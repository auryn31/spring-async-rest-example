package com.example.reactive.reactive_vertx

import com.beust.klaxon.Klaxon
import com.example.reactive.service.DataService
import io.vertx.core.Handler
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.RoutingContext

class SyncCarResponse : Handler<RoutingContext> {

  override fun handle(rtx: RoutingContext?) {
    val response = rtx?.response()
    val carResponseListBuffer: Buffer = Buffer.buffer(Klaxon().toJsonString(DataService.getDataStream(TIMEOUT).blockingIterable().toList()))
    response?.end(carResponseListBuffer)
  }

}
