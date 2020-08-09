package com.example.demobeforeall

interface HSMRouterService {
    fun route(provider: HSMProvider): HSMService
}
