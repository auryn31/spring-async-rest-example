package com.example.reactive

import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Response


@Path("/cars-locust")
class CarLocustResource {

    @Inject
    lateinit var responseStream: CarStreamResponseOutput

    @GET
    @Produces("application/stream+json")
    fun loadCarsAsJsonStreamForLocust(): Response {
        return Response.ok().entity(responseStream).build()
    }
}
