package com.example.reactive.service

import com.beust.klaxon.Klaxon
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.StreamingOutput


@Path("/")
class CarService {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getCarsAsList():Response {
        return Response.ok().header("Access-Control-Allow-Origin", "*").entity(Klaxon().toJsonString(DataService.getDataStream(150).toList().blockingGet())).build()
    }


    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun loadCarsAsOctetStream(): StreamingOutput {
        return CarStreamResponseOutput()
    }

    @GET
    @Produces("application/stream+json")
    fun loadCarsAsJsonStream(): Response {
        val responseStream = CarStreamResponseOutput()
        return Response.ok().header("Access-Control-Allow-Origin", "*").entity(responseStream).build()
    }
}