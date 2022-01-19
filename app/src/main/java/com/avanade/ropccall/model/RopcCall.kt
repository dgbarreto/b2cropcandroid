package com.avanade.ropccall.model

import com.google.gson.annotations.SerializedName

data class RopcCall (
    @SerializedName("username")
    var username : String,
    @SerializedName("password")
    var password : String,
    @SerializedName("grant_type")
    var grantType : String,
    @SerializedName("scope")
    var scope : String,
    @SerializedName("client_id")
    var clientId : String,
    @SerializedName("response_type")
    var responseType : String
)