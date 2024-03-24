package space.mittwerk.radioapp.model

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class RadioStream(val domain: String, val port: Int, val mountPoint: String)
{
    fun BuildFullLink(useClearHttp: Boolean = false) = (if (useClearHttp) "http://" else "https://") + "${domain}:$port/$mountPoint"
    data class Builder(var domain: String, var port:Int, var mountPoint: String)
    {
        fun domain(domain: String): Builder {
            this.domain = domain
            return this
        }
        fun port(port: Int): Builder {
            this.port = port
            return this
        }
        fun mountPoint(mountPoint: String): Builder {
            this.mountPoint = domain
            return this
        }
        fun build(): RadioStream
        {
            return RadioStream(this.domain, this.port, this.mountPoint)
        }
    }
}

object StreamsGrabber
{
    fun grab(url: String): List<String>
    {
        val streams = mutableListOf<String>()
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        val html = try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            response.body?.string() ?: throw IOException("Empty response body")
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
        val regex = Regex("<h3 class=\"mount\">Mount Point \\/\\w+<\\/h3>")
        val points = regex.findAll(html)
        for(point in points)
        {
            val txt = point.value.split("/")[1].replace("<","")
            //Log.d("POINT", txt)
            streams.add(txt)
        }
        return streams.toList()
    }
}