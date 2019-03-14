package com.microservice.study.controller

import com.microservice.study.data.Customer
import com.microservice.study.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class CustomerController {
    @Autowired
    private lateinit var customerService: CustomerService

    @GetMapping("/customer/{id}")
    fun getCustomer(@PathVariable id: Int) : ResponseEntity<Mono<Customer>> {
        val customer = customerService.getCustomer(id)
        return ResponseEntity(customer, HttpStatus.OK)
    }

    @PostMapping("/customer")
    fun createCustomer(@RequestBody customerMono: Mono<Customer>) = ResponseEntity(customerService.createCustomer(customerMono), HttpStatus.CREATED)


    @GetMapping("/customers")
    fun searchCustomer(@RequestParam(required = false, defaultValue = "")nameFilter: String) = customerService.searchCustomer(nameFilter)


}