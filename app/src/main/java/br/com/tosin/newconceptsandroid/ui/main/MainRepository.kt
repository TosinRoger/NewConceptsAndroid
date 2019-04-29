package br.com.tosin.newconceptsandroid.ui.main

import androidx.annotation.WorkerThread
import br.com.tosin.newconceptsandroid.R
import br.com.tosin.newconceptsandroid.entity.FakeData
import br.com.tosin.newconceptsandroid.repository.remote.RetrofitInitializer
import br.com.tosin.newconceptsandroid.repository.remote.interfaces.FakeDataRepository
import br.com.tosin.newconceptsandroid.utils.DefineMessageError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException

class MainRepository(private val viewModel: MainViewModel): MainContract.IntMainRepository {

    private var compositeDisposable = CompositeDisposable()
    private val service = RetrofitInitializer.createService(FakeDataRepository::class.java)

    private var arrayProvisional = hashSetOf<FakeData>()

    // =============================================================================================
    // WEB REQUESTS
    // =============================================================================================

    override fun getFakeDataRepository() {
        val disposable = service.getFakeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    viewModel.responseRemoteGetFakeData(response)
                }, { exception ->
                    when (exception) {
                        is HttpException -> viewModel.errorRemoteGetFakeData(R.string.request_default_title, DefineMessageError.getMessage(exception))
                        is ConnectException -> viewModel.errorRemoteGetFakeData(R.string.request_default_title, R.string.request_default_connection)
                        is IOException -> viewModel.errorRemoteGetFakeData(R.string.request_default_title, R.string.request_default_connection)
                        is JSONException -> viewModel.errorRemoteGetFakeData(R.string.request_default_title, R.string.request_default_no_parse_data)
                        else -> viewModel.errorRemoteGetFakeData(R.string.request_default_title, R.string.request_default_title)
                    }
                }
                )

        compositeDisposable.add(disposable)
    }

    fun destroyAnyRequest() {
        compositeDisposable.dispose()
    }

    // =============================================================================================
    // LOCAL REQUESTS
    // =============================================================================================

    @WorkerThread
    fun insert(fakeData: FakeData) {
        arrayProvisional.add(fakeData)
//        fakeDataDao?.insert(fakeData)
    }

    @WorkerThread
    fun cleanListLocal() {
        arrayProvisional.clear()
//        fakeDataDao?.deleteAll()
    }

    @WorkerThread
    fun removeFakeDataById(fakeData: FakeData) {
//        fakeDataDao?.removeById(fakeData)
    }

    fun getFakeDataLocal(): HashSet<FakeData>? {
//        return fakeDataDao?.getAll()?.toHashSet()
        return arrayProvisional
    }

}