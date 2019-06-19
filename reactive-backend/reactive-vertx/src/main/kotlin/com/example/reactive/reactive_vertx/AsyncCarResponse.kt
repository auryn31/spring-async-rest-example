package com.example.reactive.reactive_vertx

import com.beust.klaxon.Klaxon
import com.example.reactive.service.DataService
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext


class AsyncCarResponse : Handler<RoutingContext> {

  override fun handle(rtx: RoutingContext) {
    val response = rtx.response()
    response?.setChunked(true)
    val flow: Flowable<String> = DataService.getDataStream(TIMEOUT).map { Klaxon().toJsonString(it) }.toFlowable(BackpressureStrategy.BUFFER)
    flow.subscribe({
      response.write(it)
      response.write("\n")
      response.writeContinue()
    }, ::println, {response.end()})
  }

}
