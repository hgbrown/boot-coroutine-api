package dev.hbrown.bootcoroutineapi

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(TestBootCoroutineApiApplication::class)
class BootCoroutineApiApplicationTests {

    @Test
    fun contextLoads() {
    }

}
