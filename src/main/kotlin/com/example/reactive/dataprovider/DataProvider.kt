package com.example.reactive.dataprovider

import com.example.reactive.model.Car
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.springframework.stereotype.Component

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
            Thread.sleep(200)
            publishSubject.onNext(Car(name = "Car", company = "Company", model = "S"))
        }
        publishSubject.onComplete()
    }
}