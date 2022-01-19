package com.avanade.ropccall.model

import com.google.gson.annotations.SerializedName

data class TokenResult(
    @SerializedName("access_token")
    var accessToken : String,
    @SerializedName("token_type")
    var tokeType : String,
    @SerializedName("expires_in")
    var expiresIn : Int,
    @SerializedName("id_token")
    var idToken : String
)