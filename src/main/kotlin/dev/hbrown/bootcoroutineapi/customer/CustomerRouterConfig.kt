package dev.hbrown.bootcoroutineapi.customer

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class CustomerRouterConfig {

    @Bean
    fun customerRouter(customerHandler: CustomerHandler) = coRouter {
        GET(CUSTOMERS_ROUTE, customerHandler::findAll)
        POST(CUSTOMERS_ROUTE, customerHandler::create)

        GET(CUSTOMERS_ROUTE_ID, customerHandler::findById)
        PUT(CUSTOMERS_ROUTE_ID, customerHandler::update)
        DELETE(CUSTOMERS_ROUTE_ID, customerHandler::delete)
    }

    companion object {
        const val CUSTOMERS_ROUTE = "/api/customers"
        const val CUSTOMERS_ROUTE_ID = "${CUSTOMERS_ROUTE}/{id}"
    }
}
