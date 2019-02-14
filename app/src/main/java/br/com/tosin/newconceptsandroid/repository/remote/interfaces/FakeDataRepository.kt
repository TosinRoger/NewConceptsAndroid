package br.com.tosin.newconceptsandroid.repository.remote.interfaces

import br.com.tosin.newconceptsandroid.entity.FakeData
import io.reactivex.Observable
import retrofit2.http.GET

interface FakeDataRepository {

    @GET("/TosinRoger/FakeTestData/master/FakeData.json")
    fun getFakeData(): Observable<HashSet<FakeData>>
}