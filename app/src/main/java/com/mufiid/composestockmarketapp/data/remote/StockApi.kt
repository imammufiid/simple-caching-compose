package com.mufiid.composestockmarketapp.data.remote

import com.mufiid.composestockmarketapp.BuildConfig
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apiKey") apiKey: String = BuildConfig.STOCK_API_KEY
    ): ResponseBody
}