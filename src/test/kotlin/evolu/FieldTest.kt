package evolu

//import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test
import kotlin.test.assertEquals

internal class FieldTest {

    @Test
    fun getSpot() {
        val field = Field(5, 5)

        assertEquals(Field.Spot.Empty, field.getSpot(0,0))
    }

    @Test
    fun setSpot() {
    }
}