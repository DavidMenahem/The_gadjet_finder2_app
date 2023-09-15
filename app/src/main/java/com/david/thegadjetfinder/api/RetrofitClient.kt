package com.david.thegadjetfinder.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {
    private val iGadgetFinderService: IGadgetService

    init {
        val BASE_URL = "https://the-gadget-finder.ew.r.appspot.com/"
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        iGadgetFinderService = retrofit.create(IGadgetService::class.java)
    }

    public fun getIGadgetFinderService(): IGadgetService {
        return iGadgetFinderService
    }



    companion object {
       private var instance : RetrofitClient? = null;
        fun getInstance(): RetrofitClient {

            if(instance == null){
                instance = RetrofitClient()
            }
            return instance!!
        }
    }
}