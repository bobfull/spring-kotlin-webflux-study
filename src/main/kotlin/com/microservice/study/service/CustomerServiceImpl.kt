package com.microservice.study.service

import com.microservice.study.data.Customer
import com.microservice.study.exception.CustomerExistException
import com.microservice.study.exception.CustomerNoExistException
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono
import java.util.concurrent.ConcurrentHashMap

@Component
class CustomerServiceImpl : CustomerService {
    companion object {
        val initialCustomers = arrayOf(Customer(1, "Kotlin"),
                Customer(2, "Spring"),
                Customer(3, "Microservice", Customer.Telephone("12345","12345")))
    }
    val customers = ConcurrentHashMap<Int, Customer>(initialCustomers.associateBy(Customer::id))


    override fun getCustomer(id: Int) = customers[id]?.toMono() ?: Mono.empty()

    override fun searchCustomer(nameFilter: String) = customers.filter { it.value.name.contains(nameFilter, true) }
            .map(Map.Entry<Int, Customer>::value).toFlux()

    override fun createCustomer(customerMono: Mono<Customer>) = customerMono.flatMap {
        if(customers[it.id] == null) {
            customers[it.id] = it
            it.toMono()
        } else{
            Mono.error(CustomerExistException("Customer ${it.id} already Exist"))
        }
    }

    override fun deleteCustomer(id: Int): Mono<Customer> = when(customers[id]){
            null -> Mono.error(CustomerNoExistException("Customer $id not Exist"))
            else -> {
                val result = customers[id]?.toMono() ?: Mono.empty()
                customers.remove(id)
                result
            }
        }
    }