package dev.hbrown.bootcoroutineapi.customer

import org.springframework.data.annotation.Id

class Customer(
    @Id
    val id: Long? = null,

    val name: String,
) {

    override fun toString(): String {
        return "Customer(id=$id, name='$name')"
    }
}
