package io.github.shirohoo.controlexcel.dummy

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DummyExcelController(private val service: DummyExcelService) {
    @ResponseBody
    @GetMapping(
        value = ["/api/v1/excel"],
        produces = ["application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"]
    )
    fun download(): ByteArray {
        return service.download()
    }
}
