package space.mittwerk.radioapp

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.Request
import space.mittwerk.radioapp.ui.theme.RadioAppTheme
import java.io.IOException


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread {
            RadioStreams.initStreams()
        }.start()
        setContent {
            RadioAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PlayStopButton()

                }
            }
        }
    }
}
object RadioStreams
{
    fun initStreams() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://$domain:$port")
            .build()
        Log.d("HTML", "\"http://$domain:$port\"")
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
        //Log.d("HTML", "HTML is now $html")
        //Log.d("HTML", html)
        //return mutableListOf("").toList()
    }
    val domain = "[203:e93b:9902:9064:5efe:575b:3284:e1d2]"
    val port = 8000
    val streams = mutableListOf<String>()

    fun buildStreamLink(stream: String): String
    {
        //val stream = s ?: defaultStream
        val fullLink = "http://${domain}:$port/$stream"
        Log.d("RadioStreams", "return $fullLink")
        return "http://${domain}:$port/$stream"
    }
}
@Composable
fun PlayStopButton(modifier: Modifier = Modifier) {
    var isPlayState by remember { mutableStateOf(false) }
    val mediaPlayer = remember { MediaPlayer() }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Button(
            onClick = {
                isPlayState = !isPlayState
                if (isPlayState) {
                    try {
                        mediaPlayer.apply {
                            setDataSource(RadioStreams.buildStreamLink(RadioStreams.streams[0]))
                            prepareAsync()
                            setOnPreparedListener {
                                start()
                            }
                            setOnErrorListener { mp, what, extra ->
                                // Handle errors here
                                false
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    mediaPlayer.stop()
                    mediaPlayer.reset()
                }
            },
            colors = ButtonDefaults.buttonColors(
            )
        ) {
            val imgResource = if (!isPlayState) painterResource(id = R.drawable.ic_play) else painterResource(id = R.drawable.ic_pause)
            //Icon(
            //    imageVector = if (isPlayState) Icons.Sharp.Close else Icons.Sharp.PlayArrow,
            //    contentDescription = if (isPlayState) "Stop" else "Play"
           // )
            Image(imgResource, contentDescription = if (isPlayState) "Stop" else "Play", modifier = Modifier.size(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RadioAppPreview() {
    RadioAppTheme {
        PlayStopButton()
    }
}