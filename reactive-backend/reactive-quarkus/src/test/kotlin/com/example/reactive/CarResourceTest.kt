package com.example.reactive

import com.beust.klaxon.Klaxon
import com.example.reactive.model.Car
import io.quarkus.test.junit.QuarkusTest
import io.reactivex.Observable
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

@QuarkusTest
open class CarResourceTest {

    @Test
    fun `there are 11 cars as response from api as stream`() {
        val url = URL("http://127.0.0.1:8081/cars")
        val con = url.openConnection()
        con.setRequestProperty("Accept", "application/octet-stream")
        val stream = con.getInputStream()
        val reader = BufferedReader(InputStreamReader(stream))
        val observable = fromBufferedReader(reader)
        val responseCars = observable.map { Klaxon().parse<Car>(it) }.blockingIterable().toList()
        assert(responseCars.size == 10)
    }

    @Test
    fun `there are 11 cars as response from api as list`() {
        val url = URL("http://127.0.0.1:8081/cars")
        val con = url.openConnection()
        con.setRequestProperty("Accept", "application/json")
        val stream = con.getInputStream()
        val reader = BufferedReader(InputStreamReader(stream))
        var response = ""
        reader.lines().forEach{ response += it}
        val responseCars = Klaxon().parseArray<Car>(response);
        assert(responseCars?.size == 10)
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
