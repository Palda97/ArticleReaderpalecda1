package cz.cvut.palecda1

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    /*@Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }*/

    @Test
    fun notificationJson() {
        val ntf = NotificationTextFactory()
        val feedMap = mutableMapOf(Pair("Pew", 12), Pair("Dev", 10), Pair("Brah", 23))
        val json = ntf.fromMapToJson(feedMap)
        assertEquals("{\"Pew\":12,\"Dev\":10,\"Brah\":23}", json)
        assertEquals(feedMap, ntf.fromJsonToMap(json))
    }

    @Test
    fun emptyJson() {
        val ntf = NotificationTextFactory()
        ntf.fromJsonToMap("")
    }

    @Test
    fun merge() {
        val ntf = NotificationTextFactory()
        val map1 = mutableMapOf(Pair("Pew", 12), Pair("Dev", 10))
        val map2 = mutableMapOf(Pair("Pew", 3), Pair("Reddit", 18))
        val expected = mutableMapOf(Pair("Pew", 15), Pair("Dev", 10), Pair("Reddit", 18))
        assertEquals(expected, ntf.mergeMaps(map1, map2))
        assertEquals(expected, ntf.mergeMaps(map2, map1))
    }
}
