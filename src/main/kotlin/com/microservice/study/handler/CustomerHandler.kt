package com.microservice.study.handler

import com.microservice.study.service.CustomerService

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body

@Component
class CustomerHandler(val customerService: CustomerService) {
    fun get(serverRequest: ServerRequest) =
            ok().body(customerService.getCustomer(serverRequest.pathVariable("id").toInt()))
}