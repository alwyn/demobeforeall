package com.example.demobeforeall

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/status")
class HSMController(private val hsmRouterService: HSMRouterService) {

    @GetMapping
    fun status(@RequestParam("hsm") provider: HSMProvider): Message {
        return Message(hsmRouterService.route(provider).status(StatusRequest(provider)))
    }
}

data class Message(val message: String)
