package br.com.tosin.newconceptsandroid.ui.main

import android.support.annotation.WorkerThread
import br.com.tosin.newconceptsandroid.R
import br.com.tosin.newconceptsandroid.database.FakeDataDao
import br.com.tosin.newconceptsandroid.entity.FakeData
import br.com.tosin.newconceptsandroid.repository.RetrofitInitializer
import br.com.tosin.newconceptsandroid.repository.interfaces.FakeDataRepository
import br.com.tosin.newconceptsandroid.utils.DefineMessageError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException

class MainModel(private val viewModel: MainViewModel, private val fakeDataDao: FakeDataDao?) {

    private var compositeDisposable = CompositeDisposable()

    fun getFakeDataRepository() {
        val service = RetrofitInitializer.createService(FakeDataRepository::class.java)

        val disposable = service.getFakeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                        viewModel.resolveFakeDataList(response)
                    }, { exception ->
                        when (exception) {
                            is HttpException -> viewModel.resolveRequestError(R.string.request_default_title, DefineMessageError.getMessage(exception))
                            is ConnectException -> viewModel.resolveRequestError(R.string.request_default_title, R.string.request_default_connection)
                            is IOException -> viewModel.resolveRequestError(R.string.request_default_title, R.string.request_default_connection)
                            is JSONException -> viewModel.resolveRequestError(R.string.request_default_title, R.string.request_default_no_parse_data)
                            else -> viewModel.resolveRequestError(R.string.request_default_title, R.string.request_default_title)
                        }
                    }
                )

        compositeDisposable.add(disposable)
    }

    fun destroyAnyRequest() {
        compositeDisposable.dispose()
    }

    // =============================================================================================

    @WorkerThread
    fun insert(fakeData: FakeData) {
        fakeDataDao?.insert(fakeData)
    }

    @WorkerThread
    fun cleanListLocal() {
        fakeDataDao?.deleteAll()
    }

    @WorkerThread
    fun removeFakeDataById(fakeData: FakeData) {
        fakeDataDao?.removeById(fakeData)
    }

    fun getFakeDataLocal(): List<FakeData>? {
        return fakeDataDao?.getAll()
    }

}