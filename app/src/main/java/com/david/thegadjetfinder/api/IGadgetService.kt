package com.david.thegadjetfinder.api
import com.david.thegadjetfinder.model.ApiResponse
import com.david.thegadjetfinder.model.Credentials
import com.david.thegadjetfinder.model.Gadget
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface IGadgetService{

    @POST("/user/login")
    fun login(@Body credentials: Credentials): Call<ApiResponse>

    @POST("/user/register")
    fun register(@Body credentials: Credentials): Call<ApiResponse>

    @POST("/gadget/add")
    fun addGadget(@Body gadget : Gadget): Call<ApiResponse>

    @GET("/gadget/all/{userID}")
    fun getAllGadgets(@Path ("userID") userID: Long) : Call<List<Gadget>>

    @DELETE("/gadget/{gadgetId}")
    fun deleteGadget(@Path ("gadgetId") id :Long): Call<Void>
}