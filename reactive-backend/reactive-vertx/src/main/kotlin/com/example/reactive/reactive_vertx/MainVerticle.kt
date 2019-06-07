package com.example.reactive.reactive_vertx

import com.beust.klaxon.Klaxon
import com.example.reactive.service.DataService
import io.reactivex.*
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.ext.web.Router
import io.vertx.core.buffer.Buffer

class MainVerticle : AbstractVerticle() {

  override fun start(startFuture: Future<Void>) {

    val server = vertx.createHttpServer()

    val router = Router.router(vertx)

    router.route("/cars")
      .handler{ rtx ->
        val response = rtx.response()
        response.setChunked(true)
        val flow: Flowable<Buffer> = DataService.getDataFlowable(100).map { Buffer.buffer(Klaxon().toJsonString(it)) }
        flow.subscribe({
          response.write(it)
          response.write("\n")
          response.writeContinue()
        }, ::println, {response.end()})
      }.failureHandler {
        println("car error :-/\n")
        it.response().end("car error :-/\n")
      }

    router.route("/")
      .handler{ rtx ->
        val response = rtx.response()
        response.setChunked(true)
        response.write("t")
        response.end("end message")
      }
      .failureHandler {
        println("error")
        it.response().end("error")
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
