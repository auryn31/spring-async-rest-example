package com.example.reactive.rest

import com.example.reactive.dataprovider.DataProvider
import io.swagger.annotations.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter
import java.lang.Exception
import java.util.concurrent.Executors


@Controller
@Api
class RestEndpoint {

    @Autowired
    lateinit var dataProvider: DataProvider

    private val executor = Executors.newCachedThreadPool()

    @GetMapping(path = ["cars"])
    @ResponseBody
    @ApiResponses(value = [ApiResponse(code = 200, message = "Cars")])
    @ApiParam("nothing")
    fun getCars(): ResponseEntity<ResponseBodyEmitter> {
        val responseEmitter = ResponseBodyEmitter()
        try {
            dataProvider.getDataStream()
                    .subscribe(
                            { emittedValue ->
                                responseEmitter.send(emittedValue, MediaType.APPLICATION_STREAM_JSON)
                                responseEmitter.send("\n")
                            },
                            { error -> println(error) },
                            { responseEmitter.complete() }
                    )
        } catch (exception: Exception) {
            println(exception)
        }

        return ResponseEntity(responseEmitter, HttpStatus.OK)
    }

}
