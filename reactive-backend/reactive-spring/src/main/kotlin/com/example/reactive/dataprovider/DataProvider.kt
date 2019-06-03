package com.example.reactive.dataprovider

import com.example.reactive.model.Car
import com.example.reactive.service.DataService
import io.reactivex.Observable
import org.springframework.stereotype.Component

@Component
class DataProvider {
    fun getDataStream() : Observable<Car> {
        return DataService.getDataStream(0)
    }
}