package evolu

import kotlin.test.Test
import kotlin.test.assertEquals

internal class PointTest {

    @Test
    fun testPlus() {
        val p1 = Point(0,0)
        val p2 = Point(-1,12)
        val p3 = Point(3,-5)
        assertEquals(p1, p1+p1)
        assertEquals(p2, p1+p2)
        assertEquals(Point(2, 7), p2+p3)
        assertEquals(Point(6, -10), p3+p3)
    }
}
