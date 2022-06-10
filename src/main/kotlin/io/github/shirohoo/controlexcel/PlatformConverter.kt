package io.github.shirohoo.controlexcel

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType.BLANK
import org.apache.poi.ss.usermodel.CellType.BOOLEAN
import org.apache.poi.ss.usermodel.CellType.ERROR
import org.apache.poi.ss.usermodel.CellType.FORMULA
import org.apache.poi.ss.usermodel.CellType.NUMERIC
import org.apache.poi.ss.usermodel.CellType.STRING
import org.apache.poi.ss.usermodel.CellType._NONE
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class PlatformConverter<T : Any>(
    private val type: KClass<T>
) {
    private val declaredFields = type.java.declaredFields
    fun convertToEntity(excel: File): List<T> {
        require(excel.extension == "xlsx") {
            "The file must have the extension '.xlsx'. current: ${excel.extension}"
        }

        return excel.inputStream()
            .let(::XSSFWorkbook)
            .use { it.getSheetAt(0).let(::dataBind) }
    }

    private fun dataBind(sheet: XSSFSheet): List<T> {
        val excludeTitle = 1
        return sheet.drop(excludeTitle).map { row ->
            type.createInstance().apply {
                for ((idx, field) in declaredFields.withIndex()) {
                    field.isAccessible = true
                    val cell = row.getCell(idx)
                    val cellValue = valueOf(cell)
                    field.set(this, cellValue)
                }
            }
        }
    }

    private fun valueOf(cell: Cell) = when (cell.cellType) {
        STRING -> cell.stringCellValue
        _NONE -> cell.errorCellValue
        NUMERIC -> cell.numericCellValue.toLong()
        FORMULA -> cell.cellFormula
        BLANK -> cell.errorCellValue
        BOOLEAN -> cell.booleanCellValue
        ERROR -> cell.errorCellValue
        else -> error("unknown branch")
    }

    fun convertToExcel(entities: List<T>): ByteArray {
        return ByteArrayOutputStream().use {
            val wb = XSSFWorkbook()
            val sheet = wb.createSheet()

            sheet.createRow(0).apply {
                for ((idx, field) in declaredFields.withIndex()) {
                    val cell = this.createCell(idx)
                    cell.setCellValue(field.name)
                }
            }

            for ((rowIdx, entity) in entities.withIndex()) {
                val excludeTitle = rowIdx + 1
                val row = sheet.createRow(excludeTitle)
                for ((cellIdx, field) in declaredFields.withIndex()) {
                    field.isAccessible = true
                    val cell = row.createCell(cellIdx)
                    val value = field.get(entity)
                    cell.setCellValue(value.toString())
                }
            }
            wb.write(it)
            wb.close()
            it.toByteArray()
        }
    }
}
