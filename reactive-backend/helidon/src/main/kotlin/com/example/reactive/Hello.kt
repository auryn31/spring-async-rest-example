package com.example.reactive

import io.helidon.webserver.*
import java.util.concurrent.TimeUnit


fun main(args: Array<String>) {

    val webServer = WebServer
            .create(Routing.builder()
                    .get("/greet", object: Handler {
                        override fun accept(p0: ServerRequest?, p1: ServerResponse?) {
                            p1?.send("Hello World")
                        }
                    })
                    .build())
            .start()
            .toCompletableFuture()
            .get(10, TimeUnit.SECONDS)

    println("Server started at: http://localhost:" + webServer.port())
}

