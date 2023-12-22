package dev.hbrown.bootcoroutineapi.customer

import dev.hbrown.bootcoroutineapi.TestBootCoroutineApiApplication
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(TestBootCoroutineApiApplication::class)
class CustomerRepositoryTest {

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @Test
    fun `sanity test`() {
        assertThat(customerRepository).isNotNull()
    }

    @Test
    fun `should be able to count number of customers`() = runBlocking<Unit> {
        val count = customerRepository.count()

        assertThat(count).isEqualTo(26)
    }

    @Test
    fun `should be able to save a new customer`() = runBlocking<Unit> {
        val customer = Customer(name = "Test-1-${System.currentTimeMillis()}")
        val savedCustomer = customerRepository.save(customer)

        assertThat(savedCustomer.id).isNotNull()
    }

}
