package com.example.reactive.service

import com.example.reactive.model.Car
import io.reactivex.*
import kotlin.random.Random

class DataService {
    companion object {
        fun getDataStream(timeout: Long) : Observable<Car> {
            return Observable.create {
                for (i in 0..10) {
                    Thread.sleep(timeout)
                    it.onNext(createRandomCar())
                }
                it.onComplete()
            }
        }

        private fun createRandomCar(): Car {
            val carNames = listOf("e-tron", "TT Coupé", "Nova", "Uno", "Kuga", "Pinto", "Probe", "Vaneo", "iMIEV", "Opa", "Phaeton")
            val companyNames = listOf("Audi", "Toyota", "VW", "Ford", "Kia", "Fiat", "Chevrolet", "Mercedes")
            return Car(Random.nextInt(0, 100000), companyNames[Random.nextInt(0, companyNames.size)], carNames[Random.nextInt(0, carNames.size)])
        }
    }
}


