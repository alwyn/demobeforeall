package com.example.demobeforeall

import org.springframework.stereotype.Service

@Service
class DefaultHSMRouterService: HSMRouterService {
    override fun route(provider: HSMProvider): HSMService {
        TODO()
    }
}
