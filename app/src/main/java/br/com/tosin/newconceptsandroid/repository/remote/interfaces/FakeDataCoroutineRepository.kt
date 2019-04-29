package br.com.tosin.newconceptsandroid.repository.remote.interfaces

import br.com.tosin.newconceptsandroid.entity.FakeData
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface FakeDataCoroutineRepository {

    @GET("/TosinRoger/FakeTestData/master/FakeData.json")
    fun getFakeDataCoroutineAsync(): Deferred<Response<HashSet<FakeData>>>
}