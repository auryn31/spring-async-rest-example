package com.example.reactive.reactive_vertx

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.ext.web.Router

const val TIMEOUT = 0L

class MainVerticle : AbstractVerticle() {

  override fun start(startFuture: Future<Void>) {

    val startTime = System.currentTimeMillis()

    val server = vertx.createHttpServer()

    val router = Router.router(vertx)

    router.route("/cars")
      .produces("application/stream+json")
      .handler(AsyncCarResponse())
      .failureHandler {
        println("car error asynchron response\n")
        it.response().end("car error asynchron response\n")
      }

    router.route("/cars-locust")
      .produces("application/stream+json")
      .handler(AsyncCarResponse())
      .failureHandler {
        println("car error asynchron response\n")
        it.response().end("car error asynchron response\n")
      }

    router.route("/cars")
      .produces("application/json")
      .handler(SyncCarResponse())
      .failureHandler {
        println("car error synchron response\n")
        it.response().end("car error synchron response\n")
      }

    server.requestHandler(router).listen(8080) { http ->
      if (http.succeeded()) {
        startFuture.complete()
        val startTimeDone = System.currentTimeMillis() - startTime
        println("HTTP server started on port 8080 in $startTimeDone ms on event loop thread ${Thread.currentThread()}")
      } else {
        startFuture.fail(http.cause());
      }
    }
  }
}
