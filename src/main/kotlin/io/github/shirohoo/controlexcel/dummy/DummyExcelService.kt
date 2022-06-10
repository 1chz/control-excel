package io.github.shirohoo.controlexcel.dummy

import io.github.shirohoo.controlexcel.ExcelService
import io.github.shirohoo.controlexcel.PlatformConverter
import org.springframework.stereotype.Service
import java.io.File

@Service
class DummyExcelService(private val repository: DummyJpaRepository) : ExcelService<Dummy> {
    private val converter: PlatformConverter<Dummy> = PlatformConverter(Dummy::class)

    override fun upload(excel: File): List<Dummy> {
        require(excel.extension == "xlsx") {
            "The file must have the extension '.xlsx'. current: ${excel.extension}"
        }

        val entities: List<Dummy> = converter.convertToEntity(excel)
        return repository.saveAll(entities)
    }

    override fun download(): ByteArray {
        val entities: List<Dummy> = repository.findAll()
        return converter.convertToExcel(entities)
    }
}
