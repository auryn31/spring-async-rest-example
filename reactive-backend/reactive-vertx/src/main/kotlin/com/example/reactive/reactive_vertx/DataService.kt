package com.example.reactive.reactive_vertx

import com.example.reactive.model.Car
import io.reactivex.*
import io.vertx.core.Vertx
import io.vertx.reactivex.RxHelper
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class DataService {
    companion object {
        fun getDataStream(vertx: Vertx, timeout: Long) : Observable<Car> {
            if (timeout > 0) {
              return Observable
                .interval(timeout, TimeUnit.MILLISECONDS, RxHelper.scheduler(vertx))
                .take(10)
                .map { createRandomCar() }
            } else {
              return Observable.just(1).repeat(10).map { createRandomCar() }
            }
        }

        private fun createRandomCar(): Car {
            val carNames = listOf("e-tron", "TT Coupé", "Nova", "Uno", "Kuga", "Pinto", "Probe", "Vaneo", "iMIEV", "Opa", "Phaeton")
            val companyNames = listOf("Audi", "Toyota", "VW", "Ford", "Kia", "Fiat", "Chevrolet", "Mercedes")
            return Car(Random.nextInt(0, 100000), companyNames[Random.nextInt(0, companyNames.size)], carNames[Random.nextInt(0, carNames.size)])
        }
    }
}


