package io.github.shirohoo.controlexcel

import io.github.shirohoo.controlexcel.dummy.Dummy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.File

@Suppress("UNCHECKED_CAST")
class PlatformConverterTests {
    @Test
    fun `엑셀 파일을 읽어 엔티티 리스트로 반환한다`() {
        val file = File(javaClass.classLoader.getResource("dummy.xlsx")!!.file)
        val converter: PlatformConverter<Dummy> = PlatformConverter(Dummy::class)
        val entities: List<Dummy> = converter.convertToEntity(file)

        assertEquals(30, entities.size)
    }

    @Test
    fun `엔티티 리스트를 엑셀 파일의 바이트 배열로 반환한다`() {
        val converter: PlatformConverter<Dummy> = PlatformConverter(Dummy::class)
        val dummies = listOf(
            Dummy(1, "name1"),
            Dummy(2, "name2"),
            Dummy(3, "name3"),
            Dummy(4, "name4"),
            Dummy(5, "name5"),
        )

        assertDoesNotThrow { converter.convertToExcel(dummies) }
    }
}