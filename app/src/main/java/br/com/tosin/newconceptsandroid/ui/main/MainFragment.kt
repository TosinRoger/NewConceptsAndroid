package br.com.tosin.newconceptsandroid.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.tosin.newconceptsandroid.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.observeList { liveData ->
            liveData.observe(this, Observer { list ->
                Log.d("TEST", "Main Fragment => List size: ${list?.size}")
            })
        }

        viewModel.observeError { liveData ->
            liveData.observe(this, Observer { error ->
                error?.let {
                    val title = error.title
                    val msg = error.msg
                    Log.d("TEST", "Main Fragment => Error received: ${getString(msg)}")
                }

            })
        }
        viewModel.fetchFakeData(context!!)
    }
}
