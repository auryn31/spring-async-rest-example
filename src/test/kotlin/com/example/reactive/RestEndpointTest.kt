package com.example.reactive

import com.beust.klaxon.Klaxon
import com.example.reactive.model.Car
import io.reactivex.Observable
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


class RestEndpointTest {

    @Test
    fun `there are 11 cars as response from api`() {
        val url = URL("http://localhost:8080/cars")
        val con = url.openConnection()
        val stream = con.getInputStream()
        val reader = BufferedReader(InputStreamReader(stream))
        val observable = fromBufferedReader(reader)
        val responseCars = observable.map { Klaxon().parse<Car>(it) }.blockingIterable().toList()
        assert(responseCars.size == 11)
    }
}


fun fromBufferedReader(reader: BufferedReader): Observable<String> {
    return Observable.create { e ->
        var line: String? = reader.readLine()
        while (!e.isDisposed && line != null) {
            e.onNext(line)
            line = reader.readLine()
        }
        e.onComplete()
    }
}
