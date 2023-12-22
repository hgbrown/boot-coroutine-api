package dev.hbrown.bootcoroutineapi.customer

data class CustomerCreateRequest(val name: String)

fun CustomerCreateRequest.toCustomer(): Customer {
    return Customer(name = name)
}
