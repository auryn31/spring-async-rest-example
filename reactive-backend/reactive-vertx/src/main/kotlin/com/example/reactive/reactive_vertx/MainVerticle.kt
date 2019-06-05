package com.example.reactive.reactive_vertx

import com.beust.klaxon.Klaxon
import com.example.reactive.service.DataService
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.ext.web.Router
import io.vertx.core.buffer.Buffer
import io.vertx.reactivex.RxHelper


class MainVerticle : AbstractVerticle() {

  override fun start(startFuture: Future<Void>) {

    val server = vertx.createHttpServer()

    val router = Router.router(vertx)

    val dataStream: Flowable<Buffer> = DataService.getDataStream(100).map { Buffer.buffer(Klaxon().toJsonString(it)) }.toFlowable(BackpressureStrategy.BUFFER)

    router.route("/cars")
      .handler{ rtx ->
        val response = rtx.response()
        response.setChunked(true)
        val subscriber = RxHelper.toSubscriber(response)
        dataStream.subscribe(subscriber)
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
