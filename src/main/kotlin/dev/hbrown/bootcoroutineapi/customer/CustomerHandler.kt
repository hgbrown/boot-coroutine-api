package dev.hbrown.bootcoroutineapi.customer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.server.ServerWebInputException

@Component
class CustomerHandler(
    private val customerRepository: CustomerRepository,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)
    suspend fun findAll(request: ServerRequest): ServerResponse = ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .bodyAndAwait(customerRepository.findAll())

    suspend fun findById(request: ServerRequest): ServerResponse {
        val id = getIdFromRequest(request)

        return customerRepository.findById(id)?.let { customer ->
            respondWithOkValue(customer)
        } ?: respondWithError("No customer found with id $id.", HttpStatus.NOT_FOUND)
    }

    suspend fun create(request: ServerRequest): ServerResponse {
        val customerCreateRequest = parseBody<CustomerCreateRequest>(request)
        return saveCustomer(customerCreateRequest, request)
    }

    suspend fun update(request: ServerRequest): ServerResponse {
        val id = getIdFromRequest(request)
        val customerUpdateRequest = parseBody<CustomerUpdateRequest>(request)
        return updateCustomer(customerUpdateRequest, id)
    }

    suspend fun delete(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toLong()
        customerRepository.deleteById(id)
        return ServerResponse.noContent().buildAndAwait()
    }

    private suspend fun saveCustomer(
        customerCreateRequest: CustomerCreateRequest?,
        request: ServerRequest,
    ): ServerResponse {
        return if (customerCreateRequest != null) {
            val savedCustomer = customerRepository.save(customerCreateRequest.toCustomer())
            log.debug("savedCustomer=[{}]", savedCustomer)

            ServerResponse
                .created(request.uriBuilder().pathSegment(savedCustomer.id.toString()).build())
                .buildAndAwait()
        } else {
            respondWithError("Unable to save customer - no create customer request data found.")
        }
    }

    private suspend fun updateCustomer(customerUpdateRequest: CustomerUpdateRequest?, id: Long): ServerResponse {
        return if (customerUpdateRequest != null) {
            val foundCustomerById = customerRepository.findById(id)
            log.debug("foundCustomerById=[{}]", foundCustomerById)

            if (foundCustomerById != null) {
                val customerToUpdate = customerUpdateRequest.toCustomer(id)
                val updatedCustomer = customerRepository.save(customerToUpdate)
                log.debug("updatedCustomer=[{}]", updatedCustomer)

                respondWithOkValue(updatedCustomer)
            } else {
                respondWithError("Unable to update customer - no customer found with id $id.")
            }
        } else {
            respondWithError("Unable to update customer - no update customer update request data found.")
        }
    }

    private fun getIdFromRequest(request: ServerRequest) = request.pathVariable("id").toLong()

    private suspend inline fun <reified T : Any> parseBody(request: ServerRequest): T? {
        return try {
            request.awaitBodyOrNull<T>()
        } catch (e: ServerWebInputException) {
            log.error("Unable to parse request body as ${T::class.simpleName}.", e)
            null
        }
    }

    private suspend fun respondWithError(message: String, status: HttpStatus = HttpStatus.BAD_REQUEST) = ServerResponse
        .status(status)
        .bodyValueAndAwait(mapOf("error" to message))

    private suspend fun respondWithOkValue(value: Any) = ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValueAndAwait(value)
}

