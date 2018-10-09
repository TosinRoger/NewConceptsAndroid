package br.com.tosin.newconceptsandroid.ui.detail

import android.support.annotation.WorkerThread
import br.com.tosin.newconceptsandroid.entity.FakeData
import br.com.tosin.newconceptsandroid.repository.database.FakeDataDao

class DetailRepository(val detailModelView: FakeDetailModelView, private val fakeDataDao: FakeDataDao?) {


    // =============================================================================================
    // LOCAL REQUESTS
    // =============================================================================================

    @WorkerThread
    fun fetchFakeDataById(fakeDataId: String): FakeData? {
        return fakeDataDao?.fetchById(fakeDataId)
    }
}