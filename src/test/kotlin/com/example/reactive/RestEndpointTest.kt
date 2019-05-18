package com.example.reactive

import com.beust.klaxon.Klaxon
import com.example.reactive.model.Car
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RestEndpointTest {

    @Test
    fun `there are 11 cars as response from api`() {
        val url = URL("http://127.0.0.1:8080/cars")
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
