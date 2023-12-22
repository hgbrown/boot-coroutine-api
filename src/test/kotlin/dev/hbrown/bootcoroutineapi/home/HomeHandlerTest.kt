package dev.hbrown.bootcoroutineapi.home

import dev.hbrown.bootcoroutineapi.TestBootCoroutineApiApplication
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Import

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestBootCoroutineApiApplication::class)
class HomeHandlerTest {

    @LocalServerPort
    private var port: Int = -1

    @BeforeEach
    fun setPort() {
        RestAssured.port = port
    }

    @Test
    fun `should get expected response from root path`() {
        given()
            .get("/")
            .then()
                .statusCode(200)
                .body("build-version", equalTo("1.0.0"))
                .body("build-time", notNullValue())
                .body("active-profiles", equalTo("dev"))
    }

    @Test
    fun `should get expected response from health path`() {
        given()
            .get("/health")
            .then()
            .statusCode(200)
            .body("status", equalTo("UP"))
    }
}
