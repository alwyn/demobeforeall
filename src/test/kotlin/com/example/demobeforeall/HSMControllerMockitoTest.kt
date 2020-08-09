package com.example.demobeforeall

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient

/**
 * A test implemented using the native SPring Boot support for Mockito
 * @author JB Nizet
 */
@WebFluxTest(HSMController::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HSMControllerMockitoTest(@Autowired private val webTestClient: WebTestClient) {

    @MockBean
    private lateinit var lunaHSMService: LunaHSMService

    @MockBean
    private lateinit var hsmRouterService: HSMRouterService

    @BeforeAll
    fun prepare() {
        `when`(hsmRouterService.route(HSMProvider.LUNA)).thenReturn(lunaHSMService)
    }

    @Test
    fun passes() {
        `when`(lunaHSMService.status(StatusRequest(HSMProvider.LUNA))).thenReturn("foo")
        webTestClient.get()
            .uri("/status?hsm=LUNA")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.message").isEqualTo("foo")

        verify(lunaHSMService).status(StatusRequest(HSMProvider.LUNA))
    }
}
