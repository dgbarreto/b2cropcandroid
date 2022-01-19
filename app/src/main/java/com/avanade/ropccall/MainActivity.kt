package com.avanade.ropccall

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.pm.Signature
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Base64
import android.widget.EditText
import java.lang.IllegalStateException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.HashSet


class MainActivity : AppCompatActivity() {
    private lateinit var buttonLogin : Button
    private lateinit var buttonListBrowsers : Button
    private lateinit var tvLogin : EditText
    private lateinit var tvPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonLogin = findViewById(R.id.buttonLogin)
        tvLogin = findViewById(R.id.tvUser)
        tvPassword = findViewById(R.id.tvPassword)
        buttonListBrowsers = findViewById(R.id.buttonListBrowsers)

        buttonLogin.setOnClickListener { login() }
        buttonListBrowsers.setOnClickListener { listInstalledBrowsers() }

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    fun login(){
        val url = URL("https://nextdevpoc.b2clogin.com/nextdevpoc.onmicrosoft.com/oauth2/v2.0/token?p=b2c_1_ropc_auth")
        var params = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(tvLogin.text.toString(), "UTF-8")
        params += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(tvPassword.text.toString(), "UTF-8")
        params += "&" + URLEncoder.encode("grant_type", "UTF-8") + "=" + URLEncoder.encode("password", "UTF-8")
        params += "&" + URLEncoder.encode("scope", "UTF-8") + "=" + URLEncoder.encode("openid 68646994-3950-4f4e-a1ec-47a108d27b3a", "UTF-8")
        params += "&" + URLEncoder.encode("client_id", "UTF-8") + "=" + URLEncoder.encode("68646994-3950-4f4e-a1ec-47a108d27b3a", "UTF-8")
        params += "&" + URLEncoder.encode("response_type", "UTF-8") + "=" + URLEncoder.encode("token id_token", "UTF-8")

        with(url.openConnection() as HttpURLConnection as HttpURLConnection){
            requestMethod = "POST"
            val sw = OutputStreamWriter(outputStream)
            sw.write(params)
            sw.flush()

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()
                var inputLine = it.readLine()
                while(inputLine != null){
                    response.append(inputLine)
                    inputLine = it.readLine()
                }

                println("Response: $response")
            }
        }
    }

    fun listInstalledBrowsers(){
        val pm = packageManager
        //val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
        val intent = Intent(Intent.ACTION_VIEW, Uri.fromParts("http", "", null))
            .addCategory(Intent.CATEGORY_BROWSABLE)

        var resolveActivityList : List<ResolveInfo> = pm.queryIntentActivities(intent, 0)
        val activities = resolveActivityList.iterator()
        var info : ResolveInfo
        var packageInfo : PackageInfo

        while(activities.hasNext()){
            info = activities.next()
            packageInfo = packageManager.getPackageInfo(info.activityInfo.packageName, PackageManager.GET_SIGNATURES)
            generateSignatureHash(packageInfo.signatures)
        }
    }

    fun generateSignatureHash(signatures : Array<Signature>):  Set<String>{
        var signatureHashes : MutableSet<String> = HashSet()
        val signaturesIterator = signatures.iterator()
        var signature : Signature
        val messageDigest : MessageDigest = MessageDigest.getInstance("SHA-512")

        while(signaturesIterator.hasNext()){
            signature = signaturesIterator.next()
            var hashBytes = messageDigest.digest(signature.toByteArray())
            signatureHashes.add(Base64.encodeToString(hashBytes, 10))
            println(Base64.encodeToString(hashBytes, 10))
        }

        return signatureHashes
    }
}