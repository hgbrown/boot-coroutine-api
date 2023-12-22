package dev.hbrown.bootcoroutineapi.customer

data class CustomerUpdateRequest(val name: String)

fun CustomerUpdateRequest.toCustomer(id: Long): Customer {
    return Customer(id = id, name = name)
}
