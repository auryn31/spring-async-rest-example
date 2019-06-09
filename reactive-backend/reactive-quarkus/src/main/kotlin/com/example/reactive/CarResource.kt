package com.example.reactive

import com.example.reactive.service.DataService
import javax.inject.Inject
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.*


@Path("/cars")
class CarResource {

    @Inject
    lateinit var responseStream: CarStreamResponseOutput

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getCarsAsList() = DataService.getDataStream(0).toList().blockingGet()


    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun loadCarsAsOctetStream(): Response {
        return Response.ok().entity(responseStream).build()
    }

    @GET
    @Produces("application/stream+json")
    fun loadCarsAsJsonStream(): Response {
        return Response.ok().entity(responseStream).build()
    }
}
