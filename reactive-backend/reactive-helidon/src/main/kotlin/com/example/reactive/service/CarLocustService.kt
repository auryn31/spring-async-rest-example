package com.example.reactive.service

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Response

@Path("/")
class CarLocustService {


    @GET
    @Produces("application/stream+json")
    fun loadCarsAsJsonStream(): Response {
        return Response.ok().header("Access-Control-Allow-Origin", "*").entity(CarStreamResponseOutput()).build()
    }
}