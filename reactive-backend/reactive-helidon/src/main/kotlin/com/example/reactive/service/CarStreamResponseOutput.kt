package com.example.reactive.service

import com.beust.klaxon.Klaxon
import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.util.concurrent.CountDownLatch
import javax.ws.rs.core.StreamingOutput

class CarStreamResponseOutput : StreamingOutput {

    override fun write(os: OutputStream?) {
        val writer = BufferedWriter(OutputStreamWriter(os))
        val countDownLatch = CountDownLatch(1)
        DataService.getDataStream(0).subscribe({
            writer.write(Klaxon().toJsonString(it))
            writer.write("\n")
            writer.flush()
        }, ::println, {
            os?.close()
            countDownLatch.countDown()
        })
        countDownLatch.await()
        writer.flush()
    }
}