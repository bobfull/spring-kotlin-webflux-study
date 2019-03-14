package com.microservice.study.handler

import com.microservice.study.data.Customer
import com.microservice.study.service.CustomerService
import org.springframework.http.HttpStatus

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.reactive.function.server.body

@Component
class CustomerHandler(val customerService: CustomerService) {
    fun get(serverRequest: ServerRequest) =
        customerService.getCustomer(serverRequest.pathVariable("id").toInt()).flatMap { ok().body(fromObject(it)) }
                .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())

    fun serarch(serverRequest: ServerRequest) =
            ok().body(customerService.searchCustomer(serverRequest.queryParam("nameFilter").orElse("")), Customer::class.java)
}