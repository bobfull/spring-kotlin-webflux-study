package com.microservice.study.service

import com.microservice.study.data.Customer


interface CustomerService {
    fun getCustomer(id: Int) : Customer?
    fun searchCustomer(nameFilter : String) : List<Customer>
}