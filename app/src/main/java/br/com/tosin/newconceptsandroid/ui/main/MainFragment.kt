package br.com.tosin.newconceptsandroid.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Canvas
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.tosin.newconceptsandroid.R
import br.com.tosin.newconceptsandroid.ui.main.adapter.FakeViewHolder
import kotlinx.android.synthetic.main.main_fragment.view.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.onRefresh

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var mView: View
    private lateinit var viewAdapter: FakeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        mView = view
        configView(view)

        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        configVmObservers()
    }

    private fun configView(view: View) {
        viewAdapter = FakeAdapter()

        view.recyclerView_main_fakeData.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(view.context)
            adapter = viewAdapter
        }

        view.fab_main.setOnClickListener {
            showLoading(true)
            viewModel.refreshFakeData(mView.context)
        }

        view.swipereFresh_main.onRefresh {
            viewModel.refreshFakeData(context!!)
        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, holder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // remove item from adapter
                val position = viewHolder.adapterPosition
                val item = viewAdapter.getItemList(position)
                if (item != null)
                    viewModel.removeFakeDataById(item)

                val foreground = (viewHolder as FakeViewHolder).foreground
                ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(foreground)
            }

            override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val foreground = (viewHolder as FakeViewHolder).foreground
                ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive)

            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                val foreground = (viewHolder as FakeViewHolder).foreground
                ItemTouchHelper.Callback.getDefaultUIUtil().clearView(foreground)
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val foreground = (viewHolder as FakeViewHolder).foreground
                ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive)
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(view.recyclerView_main_fakeData)

    }

    private fun configVmObservers() {
        viewModel.observeListChange { liveData ->
            liveData.observe(this, Observer { list ->
                if (list == null || list.isEmpty()) {
                    mView.textView_main_emptyList.visibility = View.VISIBLE
                }
                else {
                    mView.textView_main_emptyList.visibility = View.GONE
                }
                Log.d("TEST", "Main Fragment => Receive received: ${list?.size}")
                showLoading(false)
                viewAdapter.setList(list ?: listOf())
            })
        }

        viewModel.observeErrorChange { liveData ->
            liveData.observe(this, Observer { error ->
                showLoading(false)
                error?.let {
                    val title = error.title
                    val msg = error.msg
                    Log.d("TEST", "Main Fragment => Error received: ${getString(msg)}")
                    showMessage(getString(title), getString(msg))
                }
            })
        }
    }

    private fun showLoading(show: Boolean) {
        mView.swipereFresh_main.isRefreshing = show
    }

    private fun showMessage(_title: String, _message: String) {
        alert {
            title = _title
            message = _message
            positiveButton("Ok") {

            }
        }.show()
    }

}
