package com.example.office.interfaces
import com.example.office.model.Client
import io.reactivex.Observable
import retrofit2.http.GET

interface ClientRequest {
    @GET("/users")
    fun getData(): Observable<ArrayList<Client>>
}