package br.com.tosin.newconceptsandroid.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import br.com.tosin.newconceptsandroid.entity.FakeData
import kotlinx.coroutines.Deferred
import retrofit2.Response

abstract class MainContract {

    interface MainView {
        fun showLoading(show: Boolean)
        fun showMessage(_title: String, _message: String)
        fun openDetails(item: FakeData, position: Int)
    }
    interface MainUserActionListener {
        fun refreshFakeData(context: Context)
        fun responseRemoteGetFakeData(list: HashSet<FakeData>?)
        fun errorRemoteGetFakeData(title: Int, msg: Int)

        fun getFakeList(): LiveData<HashSet<FakeData>>
        fun observeErrorChange(temp: onError)
        fun removeFakeDataById(fakeData: FakeData)
    }
    interface IntMainRepository {
        fun getFakeDataRepository()
    }
}
