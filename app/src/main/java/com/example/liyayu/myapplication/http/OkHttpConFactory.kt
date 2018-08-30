package com.example.liyayu.myapplication.http

import android.os.Build
import com.yanzhenjie.kalle.Headers
import com.yanzhenjie.kalle.Request
import com.yanzhenjie.kalle.connect.ConnectFactory
import com.yanzhenjie.kalle.connect.Connection
import com.yanzhenjie.kalle.urlconnect.URLConnection
import okhttp3.OkHttpClient
import okhttp3.internal.huc.OkHttpURLConnection
import okhttp3.internal.huc.OkHttpsURLConnection
import java.net.HttpURLConnection
import java.net.Proxy
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * Created by liyayu on 2018/8/21.
 * OkHttp 封装作为底层链接库
 */
class OkHttpConFactory(builder: Builder) : ConnectFactory {

    private val mClient: OkHttpClient? = if (builder.mClient == null) OkHttpClient.Builder().build() else builder.mClient

    private fun open(url: URL, proxy: Proxy): HttpURLConnection {
        mClient!!.run {
            val newClient: OkHttpClient? = mClient.newBuilder().proxy(proxy).build()
            return when (url.protocol) {
                "http" -> OkHttpURLConnection(url, newClient)
                "https" -> OkHttpsURLConnection(url, newClient)
                else -> throw IllegalArgumentException("Unexpected protocol: " + url.protocol)
            }
        }

    }

    override fun connect(request: Request?): Connection {
        val connection: HttpURLConnection? = open(URL(request?.url().toString()), request!!.proxy())
        connection?.run {
            connection.connectTimeout = request.connectTimeout()
            connection.readTimeout = request.readTimeout()
            connection.instanceFollowRedirects = false
            if (connection is HttpsURLConnection) {
                if (request.sslSocketFactory() != null)
                    connection.sslSocketFactory = request.sslSocketFactory()
                if (request.hostnameVerifier() != null)
                    connection.hostnameVerifier = request.hostnameVerifier()
            }
            val method = request.method()
            connection.requestMethod = method.toString()
            connection.doInput = true
            val isAllowBody = method.allowBody()
            connection.doOutput = isAllowBody

            val headers = request.headers()

            if (isAllowBody) {
                val contentLength = headers.contentLength
                when {
                    contentLength <= Integer.MAX_VALUE -> connection.setFixedLengthStreamingMode(contentLength.toInt())
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> connection.setFixedLengthStreamingMode(contentLength)
                    else -> connection.setChunkedStreamingMode(256 * 1024)
                }
            }

            val requestHeaders = Headers.getRequestHeaders(headers)
            for ((headKey, headValue) in requestHeaders) {
                connection.setRequestProperty(headKey, headValue)
            }
            connection.connect()
        }
        return URLConnection(connection)
    }

    class Builder {

        var mClient: OkHttpClient? = null

        fun client(client: OkHttpClient): Builder {
            this.mClient = client
            return this
        }

        fun build(): OkHttpConFactory {
            return OkHttpConFactory(this)
        }
    }
}
