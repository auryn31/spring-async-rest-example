package com.example.reactive.rest

import com.beust.klaxon.Klaxon
import com.example.reactive.dataprovider.DataProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody
import java.io.BufferedWriter
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.util.concurrent.CountDownLatch

@Component
class CarStreamResponseOutput : StreamingResponseBody {
    @Autowired
    lateinit var dataProvider: DataProvider

    override fun writeTo(os: OutputStream) {
        val writer = BufferedWriter(OutputStreamWriter(os))
        val countDownLatch = CountDownLatch(1)
        dataProvider.getDataStream().subscribe({
            writer.write(Klaxon().toJsonString(it))
            writer.write("\n")
            writer.flush()
        }, ::println, {
            os.close()
            countDownLatch.countDown()
        })
        countDownLatch.await()
        writer.flush()
    }
}