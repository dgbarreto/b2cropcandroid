package com.avanade.ropccall.api

import com.avanade.ropccall.model.RopcCall
import com.avanade.ropccall.model.TokenResult
import retrofit2.Call
import retrofit2.http.*

interface Endpoint {
    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded" )
    @POST("/nextdevpoc.onmicrosoft.com/oauth2/v2.0/token?p=b2c_1_ropc_auth")
    fun authenticate(@Field("username") username : String,
        @Field("password") password : String,
        @Field("grant_type") grantType : String,
        @Field("scope") scope : String,
        @Field("client_id") clientId : String,
        @Field("response_type") responseType : String) : Call<TokenResult>
}