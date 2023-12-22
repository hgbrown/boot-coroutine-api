package dev.hbrown.bootcoroutineapi.home

import org.springframework.boot.info.BuildProperties
import org.springframework.core.env.Environment
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class HomeHandler(
    private val env: Environment,
    private val buildProperties: BuildProperties,
) {

    suspend fun home(request: ServerRequest): ServerResponse = ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValueAndAwait(
            """
        {
            "build-version": "${buildProperties.version}",
            "build-time": "${buildProperties.time}",
            "active-profiles": "${env.activeProfiles.joinToString()}",
        }
    """.trimIndent()
        )

    suspend fun health(request: ServerRequest): ServerResponse = ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValueAndAwait(
            """
        {
            "status": "UP"
        }
    """.trimIndent()
        )
}
