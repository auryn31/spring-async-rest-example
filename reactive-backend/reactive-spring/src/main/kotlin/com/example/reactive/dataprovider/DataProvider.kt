package com.example.reactive.dataprovider

import com.example.reactive.model.Car
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class DataProvider {

    fun getDataStream() : Observable<Car> {
        val publishSubject = PublishSubject.create<Car>()
        GlobalScope.async {
            Thread.sleep(100)
            emitRandomCarsWithTimeout(publishSubject)
        }
        return publishSubject
    }

    fun emitRandomCarsWithTimeout(publishSubject: PublishSubject<Car>) {
        for (i in 0..10) {
            Thread.sleep(150)
            publishSubject.onNext(createRandomCar())
        }
        publishSubject.onComplete()
    }

    fun createRandomCar(): Car {
        val carNames = listOf("e-tron", "TT Coupé", "Nova", "Uno", "Kuga", "Pinto", "Probe", "Vaneo", "iMIEV", "Opa", "Phaeton")
        val companyNames = listOf("Audi", "Toyota", "VW", "Ford", "Kia", "Fiat", "Chevrolet", "Mercedes")
        return Car(Random.nextInt(0, 100000), companyNames[Random.nextInt(0, companyNames.size)], carNames[Random.nextInt(0, carNames.size)])
    }
}