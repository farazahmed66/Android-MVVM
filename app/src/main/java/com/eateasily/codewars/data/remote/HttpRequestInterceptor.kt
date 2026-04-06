package com.eateasily.codewars.data.remote

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HttpRequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val requestBuilder = req.newBuilder()
        requestBuilder.addHeader("Accept", "application/json")
        requestBuilder.addHeader("Content-Type", "application/json")
        val urlBuilder: HttpUrl.Builder = req.url.newBuilder()
        requestBuilder.url(urlBuilder.build())
        val finalReq: Request = requestBuilder.build()
        return chain.proceed(finalReq)
    }
}