package com.microservice.study.router

import com.microservice.study.handler.CustomerHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class CustomerRouter {
    @Autowired
    lateinit var customerHandler: CustomerHandler

    @Bean
    fun customerRoutes() = router {
        "/functional".nest {
            "/customer".nest{
                GET("/{id}",  customerHandler::get)
            }
        }
    }
}