package com.microservice.study.repository

import com.microservice.study.data.Customer
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.findById
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import javax.annotation.PostConstruct

@Repository
class CustomerRepository(private val template: ReactiveMongoTemplate) {
    companion object {
        val initialCustomer = listOf(Customer(1, "Kotiln"), Customer(2, "Spring"),
                Customer(3, "microservices", Customer.Telephone("+44", "12345")))
    }

    @PostConstruct
    fun initializeRepository() = initialCustomer.map(Customer::toMono).map(this::create).map(Mono<Customer>::subscribe)

    fun create(customer: Mono<Customer>) = template.save(customer)

    fun findById(id : Int) = template.findById<Customer>(id)
}