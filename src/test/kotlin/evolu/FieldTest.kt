package evolu

import kotlin.test.Test
import kotlin.test.assertEquals

internal class FieldTest {

    @Test
    fun getSpot() {
        val field = Field(5, 5)

        assertEquals(Field.Spot.Wall, field.getSpot(-1,0))
        assertEquals(Field.Spot.Wall, field.getSpot(0,-1))
        assertEquals(Field.Spot.Empty, field.getSpot(0,0))
        assertEquals(Field.Spot.Empty, field.getSpot(4,4))
        assertEquals(Field.Spot.Wall, field.getSpot(5,4))
        assertEquals(Field.Spot.Wall, field.getSpot(4,5))
    }

    @Test
    fun setSpot() {
        val field = Field(5, 5)
        assertEquals(Field.Spot.Empty, field.getSpot(2,2))
        field.setSpot(2,2,Field.Spot.Diamond)
        assertEquals(Field.Spot.Diamond, field.getSpot(2,2))
    }
}