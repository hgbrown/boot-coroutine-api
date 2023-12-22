package dev.hbrown.bootcoroutineapi.customer

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.hbrown.bootcoroutineapi.TestBootCoroutineApiApplication
import dev.hbrown.bootcoroutineapi.customer.CustomerRouterConfig.Companion.CUSTOMERS_ROUTE
import dev.hbrown.bootcoroutineapi.customer.CustomerRouterConfig.Companion.CUSTOMERS_ROUTE_ID
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.endsWith
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Import

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestBootCoroutineApiApplication::class)
class CustomerHandlerTest {
    @LocalServerPort
    private var port: Int = -1

    @BeforeEach
    fun setPort() {
        RestAssured.port = port
    }

    @Test
    fun `should be able to find all customers`() {
        given()
            .get(CUSTOMERS_ROUTE)
            .then()
            .statusCode(200)
            .body("size()", equalTo(26))
    }

    @Nested
    inner class FindCustomerByIdShould {
        @Test
        fun `get expected customer if it exists`() {
            val id = -1
            given()
                .get(CUSTOMERS_ROUTE_ID, id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("name", equalTo("Amanda"))
        }

        @Test
        fun `fail when no customer with id exists`() {
            val id = -100
            given()
                .get(CUSTOMERS_ROUTE_ID, id)
                .then()
                .statusCode(404)
                .body("error", equalTo("No customer found with id $id."))
        }
    }

    @Nested
    inner class CreateCustomerShould {
        @Test
        fun `succeed when correct json sent in body`() {
            val name = "Test-1-${System.currentTimeMillis()}"
            val createCustomerRequest = CustomerCreateRequest(name = name)

            given()
                .contentType(ContentType.JSON)
                .body(createCustomerRequest.toJson())
                .post(CUSTOMERS_ROUTE)
                .prettyPeek()
                .then()
                .statusCode(201)
                .header("Location", endsWith("$CUSTOMERS_ROUTE/1"))
                .body(equalTo(""))
        }

        @Test
        fun `fail when empty body sent`() {
            given()
                .contentType(ContentType.JSON)
                .body("")
                .post(CUSTOMERS_ROUTE)
                .prettyPeek()
                .then()
                .statusCode(400)
                .body("error", equalTo("Unable to save customer - no create customer request data found."))
        }

        @Test
        fun `fail when invalid json body sent`() {
            given()
                .contentType(ContentType.JSON)
                .body("""{ "invalid": "body" }""")
                .post(CUSTOMERS_ROUTE)
                .prettyPeek()
                .then()
                .statusCode(400)
                .body("error", equalTo("Unable to save customer - no create customer request data found."))
        }

        private fun CustomerCreateRequest.toJson(): String = jacksonObjectMapper().writeValueAsString(this)
    }

    @Nested
    inner class UpdatingCustomerShould {
        @Test
        fun `succeed if customer with id exists`() {
            val id = -26
            val name = "Test-2-${System.currentTimeMillis()}"
            val updateCustomerRequest = CustomerUpdateRequest(name = name)

            given()
                .contentType(ContentType.JSON)
                .body(updateCustomerRequest.toJson())
                .put(CUSTOMERS_ROUTE_ID, id)
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("name", equalTo(name))
        }

        @Test
        fun `fail when customer with id does not exist`() {
            val id = -200
            val name = "Test-20-${System.currentTimeMillis()}"
            val updateCustomerRequest = CustomerUpdateRequest(name = name)

            given()
                .contentType(ContentType.JSON)
                .body(updateCustomerRequest.toJson())
                .put(CUSTOMERS_ROUTE_ID, id)
                .prettyPeek()
                .then()
                .statusCode(400)
                .body("error", equalTo("Unable to update customer - no customer found with id $id."))
        }

        @Test
        fun `fail when empty body is sent`() {
            val id = -26

            given()
                .contentType(ContentType.JSON)
                .body("")
                .put(CUSTOMERS_ROUTE_ID, id)
                .prettyPeek()
                .then()
                .statusCode(400)
                .body("error", equalTo("Unable to update customer - no update customer update request data found."))
        }

        @Test
        fun `fail when incorrect json sent in body`() {
            val id = -26

            given()
                .contentType(ContentType.JSON)
                .body("""{ "invalid": "body" }""")
                .put(CUSTOMERS_ROUTE_ID, id)
                .prettyPeek()
                .then()
                .statusCode(400)
                .body("error", equalTo("Unable to update customer - no update customer update request data found."))
        }

        private fun CustomerUpdateRequest.toJson(): String = jacksonObjectMapper().writeValueAsString(this)
    }

    @Nested
    inner class DeleteCustomerShould {
        @Test
        fun `succeed if customer with id exists`() {
            val id = 1

            given()
                .delete(CUSTOMERS_ROUTE_ID, id)
                .prettyPeek()
                .then()
                .statusCode(204)
                .body(equalTo(""))
        }

        @Test
        fun `succeed when customer with id does not exist`() {
            val id = -200

            given()
                .delete(CUSTOMERS_ROUTE_ID, id)
                .prettyPeek()
                .then()
                .statusCode(204)
                .body(equalTo(""))
        }
    }

}
