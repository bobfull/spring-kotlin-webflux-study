package com.microservice.study.service

import com.microservice.study.data.Customer
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class CustomerServiceImpl : CustomerService {
    companion object {
        val initialCustomers = arrayOf(Customer(1, "Kotlin"),
                Customer(2, "Spring"),
                Customer(3, "Microservice"))
    }
    val customers = ConcurrentHashMap<Int, Customer>(initialCustomers.associateBy(Customer::id))


    override fun getCustomer(id: Int): Customer? = customers[id]

    override fun searchCustomer(nameFilter: String): List<Customer> = customers.filter { it.value.name.contains(nameFilter, true) }
            .map(Map.Entry<Int, Customer>::value).toList()
}