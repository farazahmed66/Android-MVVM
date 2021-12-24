package com.eateasily.codewars.network

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HttpRequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        // outgoing request
        val req = chain.request()

        // new request builder.
        val requestBuilder = req.newBuilder()

        // add accept type header.
        requestBuilder.addHeader("Accept", "application/json")

        requestBuilder.addHeader("Content-Type", "application/json")

        // new url builder.
        val urlBuilder: HttpUrl.Builder = req.url.newBuilder()

        // build new url
        requestBuilder.url(urlBuilder.build())

        // build request
        val finalReq: Request = requestBuilder.build()

        chain.proceed(finalReq)
        return chain.proceed(finalReq)
    }
}