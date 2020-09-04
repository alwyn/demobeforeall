package com.example.demobeforeall

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient

/**
 * A test implemented using SpringMockK
 */
@WebFluxTest(HSMController::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HSMControllerMockkTest(@Autowired private val webTestClient: WebTestClient) {

    @MockkBean
    private lateinit var lunaHSMService: LunaHSMService

    @MockkBean
    private lateinit var hsmRouterService: HSMRouterService

    @BeforeAll
    fun prepare() {
        every { hsmRouterService.route(HSMProvider.LUNA) } returns lunaHSMService
    }

    @Test
    fun passes() {
        every { lunaHSMService.status(any()) } returns "foo"
        webTestClient.get()
            .uri("/status?hsm=LUNA")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.message").isEqualTo("foo")

        verify { lunaHSMService.status(any()) }
    }

    @Test
    fun shouldPassToo() {
        every { lunaHSMService.status(any()) } returns "bar"
        webTestClient.get()
                .uri("/status?hsm=LUNA")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.message").isEqualTo("bar")

        verify { lunaHSMService.status(any()) }
    }
}
