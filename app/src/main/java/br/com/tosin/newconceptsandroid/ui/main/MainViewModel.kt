package br.com.tosin.newconceptsandroid.ui.main

import android.arch.lifecycle.*
import android.content.Context
import android.net.ConnectivityManager
import br.com.tosin.newconceptsandroid.R
import br.com.tosin.newconceptsandroid.entity.ErrorResponse
import br.com.tosin.newconceptsandroid.entity.FakeData

typealias onList = (LiveData<List<FakeData>>) -> Unit
typealias onError = (LiveData<ErrorResponse>) -> Unit

class MainViewModel : ViewModel() {

    private val model = MainModel(this)

    private var fakeData = MutableLiveData<List<FakeData>>()
    private var messageError = MutableLiveData<ErrorResponse>()

    fun destroyAnyRequest() {
        model.destroyAnyRequest()
    }

    fun fetchFakeData(context: Context) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

        if (isConnected)
            model.getFakeDataRepository()
        else
            fetchLocalFakeData()
    }

    fun resolveFakeDataList(list: List<FakeData>?) {
        fakeData.value = list ?: listOf()
    }

    fun resolveRequestError(title: Int, msg: Int) {
        val message = ErrorResponse(title, msg)
        messageError.value = message
    }

    fun observeList(temp: onList) {
        temp(fakeData)
    }

    fun observeError(temp: onError) {
        temp(messageError)
    }

    // =============================================================================================

    private fun requestFakeData() {
        model.getFakeDataRepository()
    }

    private fun saveLocalList() {

    }

    private fun fetchLocalFakeData() {
        val error = ErrorResponse(R.string.request_default_title, R.string.request_default_connection)
        messageError.value = error
    }

}
