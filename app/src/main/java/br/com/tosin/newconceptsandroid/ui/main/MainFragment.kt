package br.com.tosin.newconceptsandroid.ui.main

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import br.com.tosin.newconceptsandroid.R
import br.com.tosin.newconceptsandroid.entity.FakeData
import br.com.tosin.newconceptsandroid.ui.common.MyDialogFragment
import br.com.tosin.newconceptsandroid.ui.detail.FakeDetailActivity
import br.com.tosin.newconceptsandroid.ui.main.adapter.FakeAdapter
import br.com.tosin.newconceptsandroid.ui.main.adapter.FakeViewHolder
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainFragment : MainContract.MainView, androidx.fragment.app.Fragment() {

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
        viewAdapter = FakeAdapter { item, position ->
            openDetails(item, position)
        }

        view.recyclerView_main_fakeData.apply {
            setHasFixedSize(true)
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(view.context)
            adapter = viewAdapter
        }

        view.fab_main.setOnClickListener {
            showLoading(true)
            viewModel.refreshFakeData(mView.context)
        }

        view.swipereFresh_main.setOnRefreshListener {
            viewModel.refreshFakeData(context!!)
        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: androidx.recyclerview.widget.RecyclerView, holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, target: androidx.recyclerview.widget.RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int) {
                // remove item from adapter
                val position = viewHolder.adapterPosition
                val item = viewAdapter.getItemList(position)
                if (item != null)
                    viewModel.removeFakeDataById(item)

                val foreground = (viewHolder as FakeViewHolder).foreground
                ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(foreground)
            }

            override fun onChildDrawOver(c: Canvas, recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val foreground = (viewHolder as FakeViewHolder).foreground
                ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive)
            }

            override fun clearView(recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder) {
                val foreground = (viewHolder as FakeViewHolder).foreground
                ItemTouchHelper.Callback.getDefaultUIUtil().clearView(foreground)
            }

            override fun onChildDraw(c: Canvas, recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val foreground = (viewHolder as FakeViewHolder).foreground
                ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive)
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(view.recyclerView_main_fakeData)

    }

    private fun configVmObservers() {
        viewModel.getFakeList().observe(this, Observer { fakeList ->
            if (fakeList.isNullOrEmpty()) {
                mView.textView_main_emptyList.visibility = View.VISIBLE
            }
            else {
                mView.textView_main_emptyList.visibility = View.GONE
            }
            showLoading(false)
            viewAdapter.setList(fakeList.toList())
        })

        viewModel.observeErrorChange { liveData ->
            liveData.observe(this, Observer { error ->
                showLoading(false)
                error?.let {
                    val title = error.title
                    val msg = error.msg
                    showMessage(getString(title), getString(msg))
                }
            })
        }
    }

    override fun showLoading(show: Boolean) {
        mView.swipereFresh_main.isRefreshing = show
    }

    override fun showMessage(_title: String, _message: String) {
        val dialog = MyDialogFragment.newInstance(_title, _message)
        dialog.show(childFragmentManager, "dialog")
    }

    override fun openDetails(item: FakeData, position: Int) {
        val extras = Bundle()
        extras.putString("FAKE_ID", item.Id)

        val intent = Intent(activity, FakeDetailActivity::class.java)
        intent.putExtras(extras)
        activity?.startActivity(intent)
    }
}
