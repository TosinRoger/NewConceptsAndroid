package br.com.tosin.newconceptsandroid.ui.main

import android.arch.lifecycle.*
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import br.com.tosin.newconceptsandroid.AppApplication
import br.com.tosin.newconceptsandroid.database.FakeDataDatabase
import br.com.tosin.newconceptsandroid.entity.ErrorResponse
import br.com.tosin.newconceptsandroid.entity.FakeData
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main

typealias onList = (LiveData<List<FakeData>>) -> Unit
typealias onError = (LiveData<ErrorResponse>) -> Unit

class MainViewModel : ViewModel() {

    private val model = MainModel(this, FakeDataDatabase.getInstance(AppApplication.context!!)?.fakeDataDao())

    private var fakeData = MutableLiveData<List<FakeData>>()
    private var messageError = MutableLiveData<ErrorResponse>()

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

        if (list != null)
            saveLocalList(list)
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

    fun resolveDbList(list: List<FakeData>?) {
        Log.d("TEST", "Received from DB list with ${list?.size} items")
        fakeData.value = list ?: listOf()
    }

    // =============================================================================================

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        model.destroyAnyRequest()
    }

    private fun saveLocalList(list: List<FakeData>) = uiScope.launch(Dispatchers.IO) {
        Log.d("TEST", "Try save List with ${list.size} items")
        for (fake in list) {
            model.insert(fake)
        }
        fetchLocalFakeData()
    }

    private fun fetchLocalFakeData()  {
        uiScope.launch {
            val aux = withContext(Dispatchers.IO) {
                model.getFakeDataLocal()
            }
            withContext(Dispatchers.Main) {
                aux?.let { list ->
                    fakeData.value = list
                }
            }
        }
    }

    fun cleanListLocal() {
        uiScope.launch(Dispatchers.IO) {
            model.cleanListLocal()
        }
        fetchLocalFakeData()
    }

    fun removeFakeDataById(fakeData: FakeData) {
        uiScope.launch(Dispatchers.IO) {
            model.removeFakeDataById(fakeData)
        }
        fetchLocalFakeData()
    }
}
