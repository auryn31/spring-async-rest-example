package com.example.reactive

import com.example.reactive.service.DataService
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.*


@Path("/cars")
class CarResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getCarsAsList() = DataService.getDataStream(0).toList().blockingGet()


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
