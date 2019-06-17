package com.example.reactive

import com.example.reactive.service.CarLocustService
import com.example.reactive.service.CarService
import io.helidon.webserver.*
import io.helidon.webserver.jersey.JerseySupport
import java.util.concurrent.TimeUnit
import io.helidon.webserver.ServerConfiguration


fun main(args: Array<String>) {

    val startTime = System.currentTimeMillis()

    val serverConfig = ServerConfiguration.builder()
            .port(8080).build()

    val webServer = WebServer
            .create(serverConfig, Routing.builder()
                    .register("/cars", JerseySupport.builder().register(CarService::class.java).build())
                    .register("/cars-locust", JerseySupport.builder().register(CarLocustService::class.java).build())
                    .build())
            .start()
            .toCompletableFuture()
            .get(10, TimeUnit.SECONDS)

    val startTimeDone = System.currentTimeMillis() - startTime
    println("Cars Server started at: http://localhost:" + webServer.port() + "/cars in $startTimeDone ms")
}

