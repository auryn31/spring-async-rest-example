package com.example.reactive.reactive_vertx

import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx

fun main(args: Array<String>) {

  // For testing
  val vertx = Vertx.vertx()

  // Deploy 1 verticle that will use one JVM thread
  // increase the number of instances to increase performance
  // don't go above your CPU cores x 2
  vertx.deployVerticle({ MainVerticle() }, DeploymentOptions().setInstances(1))


}
