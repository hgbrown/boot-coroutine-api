package dev.hbrown.bootcoroutineapi.customer

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: CoroutineCrudRepository<Customer, Long>
