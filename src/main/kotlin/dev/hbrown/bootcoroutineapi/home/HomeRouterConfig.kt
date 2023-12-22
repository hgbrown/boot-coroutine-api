package dev.hbrown.bootcoroutineapi.home

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class HomeRouterConfig {

    @Bean
    fun homeRouter(homeHandler: HomeHandler) = coRouter {
        GET(HOME_ROUTE, homeHandler::home)
        GET(HEALTH_ROUTE, homeHandler::health)
    }

    companion object {
        const val HOME_ROUTE = "/"
        const val HEALTH_ROUTE = "/health"
    }
}
