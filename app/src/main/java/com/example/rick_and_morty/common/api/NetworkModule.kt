package com.example.rick_and_morty.common.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp


object NetworkModule {
    val publicClient = HttpClient(OkHttp){
        install(ContentNegotation){

        }
    }
}