package br.com.tosin.newconceptsandroid.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.tosin.newconceptsandroid.AppApplication
import br.com.tosin.newconceptsandroid.R
import br.com.tosin.newconceptsandroid.entity.ErrorResponse
import br.com.tosin.newconceptsandroid.entity.FakeData
import br.com.tosin.newconceptsandroid.repository.database.FakeDataDatabase
import kotlinx.coroutines.*

typealias onError = (LiveData<ErrorResponse>) -> Unit
typealias onFake = (LiveData<FakeData>) -> Unit

class FakeDetailModelView: ViewModel() {
    private val repository = DetailRepository(this, FakeDataDatabase.getInstance(AppApplication.context!!)?.fakeDataDao())

    private var fakeData = MutableLiveData<FakeData>()
    private var messageError = MutableLiveData<ErrorResponse>()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun fetchFakeDataById(fakeDataId: String) {
        uiScope.launch {
            val fake = withContext(Dispatchers.IO) {
                repository.fetchFakeDataById(fakeDataId)
            }
            withContext(Dispatchers.Main) {
                if (fake == null) {
                    val message = ErrorResponse(R.string.request_default_title, R.string.error_fetch_fake_in_db)
                    messageError.value = message
                }
                else {
                    fakeData.value = fake
                }
            }
        }
    }

    // =============================================================================================
    // UI OBSERVER LIVE DATA
    // =============================================================================================

    fun observeListChange(temp: onFake) {
        temp(fakeData)
    }

    fun observeErrorChange(temp: onError) {
        temp(messageError)
    }
}