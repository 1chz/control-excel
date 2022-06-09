package io.github.shirohoo.controlexcel

import java.io.File

interface ExcelService<T> {
    fun upload(excel: File): List<T>
    fun download(): ByteArray
}