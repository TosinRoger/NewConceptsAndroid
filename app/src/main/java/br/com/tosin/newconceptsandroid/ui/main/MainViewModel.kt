package br.com.tosin.newconceptsandroid.ui.main

import androidx.lifecycle.*
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import br.com.tosin.newconceptsandroid.AppApplication
import br.com.tosin.newconceptsandroid.R
import br.com.tosin.newconceptsandroid.repository.database.FakeDataDatabase
import br.com.tosin.newconceptsandroid.entity.ErrorResponse
import br.com.tosin.newconceptsandroid.entity.FakeData
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main

typealias onError = (LiveData<ErrorResponse>) -> Unit

class MainViewModel : ViewModel() {

    private val repository = MainRepository(this, FakeDataDatabase.getInstance(AppApplication.context!!)?.fakeDataDao())
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var fakeData = MutableLiveData<HashSet<FakeData>>()
    private var messageError = MutableLiveData<ErrorResponse>()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        repository.destroyAnyRequest()
    }

    // =============================================================================================
    // BUSINESS LOGIC
    // =============================================================================================

    /**
     * Try get data from internet, if not possible get from database
     */
    fun refreshFakeData(context: Context) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

        if (isConnected)
            repository.getFakeDataRepository()
        else {
            errorRemoteGetFakeData(R.string.request_default_title, R.string.request_default_connection)
            fetchLocalFakeData()
        }
    }

    fun responseRemoteGetFakeData(list: HashSet<FakeData>?) {
        list?.let {
            saveLocalList(list)
        }
        fakeData.value = list
    }

    fun errorRemoteGetFakeData(title: Int, msg: Int) {
        val message = ErrorResponse(title, msg)
        messageError.value = message
    }

    // =============================================================================================
    // UI OBSERVER LIVE DATA
    // =============================================================================================

    fun getFakeList(): LiveData<HashSet<FakeData>> {
        return fakeData
    }

    fun observeErrorChange(temp: onError) {
        temp(messageError)
    }

    // =============================================================================================
    // LOGIC DATABASE
    // =============================================================================================

    private fun saveLocalList(list: HashSet<FakeData>) {
        Log.d("TEST", "Try save List with ${list.size} items")
        uiScope.launch(Dispatchers.IO) {
            for (fake in list) {
                repository.insert(fake)
            }
            fetchLocalFakeData()
        }
    }

    fun cleanListLocal() {
        uiScope.launch(Dispatchers.IO) {
            repository.cleanListLocal()
        }
        fetchLocalFakeData()
    }

    fun removeFakeDataById(fakeData: FakeData) {
        uiScope.launch(Dispatchers.IO) {
            repository.removeFakeDataById(fakeData)
        }
        fetchLocalFakeData()
    }

    private fun fetchLocalFakeData()  {
        uiScope.launch {
            val aux = withContext(Dispatchers.IO) {
                repository.getFakeDataLocal()
            }
            withContext(Dispatchers.Main) {
                aux?.let { list ->
                    fakeData.value = list
                }
            }
        }
    }
}
