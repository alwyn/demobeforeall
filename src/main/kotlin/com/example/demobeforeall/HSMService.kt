package com.example.demobeforeall

interface HSMService {
    fun status(request: StatusRequest): String
}
