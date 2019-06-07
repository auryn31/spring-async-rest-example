package com.example.reactive.reactive_vertx

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.ext.web.Router

const val TIMEOUT = 100L

class MainVerticle : AbstractVerticle() {

  override fun start(startFuture: Future<Void>) {

    val server = vertx.createHttpServer()

    val router = Router.router(vertx)

    router.route("/cars")
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
        println("HTTP server started on port 8080")
      } else {
        startFuture.fail(http.cause());
      }
    }
  }
}
