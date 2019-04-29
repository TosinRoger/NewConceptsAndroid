package br.com.tosin.newconceptsandroid.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.tosin.newconceptsandroid.R
import br.com.tosin.newconceptsandroid.entity.ErrorResponse
import br.com.tosin.newconceptsandroid.entity.FakeData
import br.com.tosin.newconceptsandroid.repository.remote.RetrofitInitializer
import br.com.tosin.newconceptsandroid.repository.remote.interfaces.FakeDataCoroutineRepository
import kotlinx.coroutines.*
import java.lang.Exception

typealias onError = (LiveData<ErrorResponse>) -> Unit

class MainViewModel : MainContract.MainUserActionListener, ViewModel() {

//    private val repository = MainRepository(this, FakeDataDatabase.getInstance(AppApplication.context!!)?.fakeDataDao())
    private val repository = MainRepository(this)
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
    override fun refreshFakeData(context: Context) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

        if (isConnected){

            uiScope.launch {
                val response = withContext(Dispatchers.IO) {
                    val retrofit = RetrofitInitializer.createService(FakeDataCoroutineRepository::class.java)
                    val call = retrofit.getFakeDataCoroutineAsync()

                    val temp = try {
                        call.await().body()
                    } catch (e: Exception) {
                        e
                    }

                    temp
                }
                withContext(Dispatchers.Main) {

                    if (response is Exception) {
                        errorRemoteGetFakeData(R.string.request_default_title, R.string.request_default_connection)
                    } else {
                        responseRemoteGetFakeData(response as HashSet<FakeData>?)
                        fetchLocalFakeData()
                    }

                    Log.d("Batata", "Error: $response")

                }
            }
        }
    }

    override fun responseRemoteGetFakeData(list: HashSet<FakeData>?) {
        list?.let {
            saveLocalList(list)
        }
        fakeData.value = list
    }

    override fun errorRemoteGetFakeData(title: Int, msg: Int) {
        val message = ErrorResponse(title, msg)
        messageError.value = message
    }

    // =============================================================================================
    // UI OBSERVER LIVE DATA
    // =============================================================================================

    override fun getFakeList(): LiveData<HashSet<FakeData>> {
        return fakeData
    }

    override fun observeErrorChange(temp: onError) {
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

    override fun removeFakeDataById(fakeData: FakeData) {
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
