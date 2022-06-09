package io.github.shirohoo.controlexcel.dummy

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Dummy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String? = null,
) {
    override fun toString() = "io.github.shirohoo.controlexcel.dummy.Dummy(id=$id, name=$name)"
}