package space.mittwerk.radioapp

import junit.framework.TestCase.assertTrue
import org.junit.Test
import space.mittwerk.radioapp.model.RadioStream
import space.mittwerk.radioapp.model.StreamsGrabber

//@VisibleForTest
class RadioStreamTest {
    @Test
    fun checkRadioGrabber()
    {
        val sForTest: String = "psytrance"
        val domainForTest: String = "[203:e93b:9902:9064:5efe:575b:3284:e1d2]"

        val rstream = RadioStream.Builder().domain(domainForTest).port(8000).build()
        val list = StreamsGrabber.grab(rstream.toString())
        //println(list)
        val listContainsElements:Boolean = list.isNotEmpty()
        val listContainsStream= sForTest in list
        assertTrue("list contains any elements", listContainsElements)
        assertTrue("list contains stream $sForTest", listContainsStream)
    }
}