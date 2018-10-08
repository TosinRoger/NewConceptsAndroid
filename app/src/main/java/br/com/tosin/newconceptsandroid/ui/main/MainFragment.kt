package br.com.tosin.newconceptsandroid.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.RotateAnimation
import android.widget.Toast
import br.com.tosin.newconceptsandroid.R
import kotlinx.android.synthetic.main.main_fragment.view.*

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
                viewAdapter.setList(list ?: listOf())
            })
        }

        viewModel.observeErrorChange { liveData ->
            liveData.observe(this, Observer { error ->
                error?.let {
                    val title = error.title
                    val msg = error.msg
                    Log.d("TEST", "Main Fragment => Error received: ${getString(msg)}")
                }
            })
        }
    }

    private fun showLoading(show: Boolean) {
        Toast.makeText(activity, "Atualizando lista", Toast.LENGTH_SHORT).show()
    }
}
