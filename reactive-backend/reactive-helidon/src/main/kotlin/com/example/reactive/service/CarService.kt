package com.example.reactive.service

import com.beust.klaxon.Klaxon
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/")
class CarService {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getCarsAsList() = Klaxon().toJsonString(DataService.getDataStream(0).toList().blockingGet())


    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun loadCarsAsOctetStream(): Response {
        val responseStream = CarStreamResponseOutput()
        return Response.ok().entity(responseStream).build()
    }

    @GET
    @Produces("application/stream+json")
    fun loadCarsAsJsonStream(): Response {
        val responseStream = CarStreamResponseOutput()
        return Response.ok().entity(responseStream).build()
    }
}