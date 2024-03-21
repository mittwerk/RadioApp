package space.mittwerk.radioapp

import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import space.mittwerk.radioapp.ui.theme.RadioAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    val domain = "[203:e93b:9902:9064:5efe:575b:3284:e1d2]"
    val port = 8000
    val streams = listOf("synthwave", "psytrance", "stream.ogg")
    val defaultStream = streams[0]
    fun getStream(s: String? = null): String
    {
        val stream = if(s == null) defaultStream else s
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
                            setDataSource(RadioStreams.getStream())
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