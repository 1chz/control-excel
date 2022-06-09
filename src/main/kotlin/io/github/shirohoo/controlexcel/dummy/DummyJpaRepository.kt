package io.github.shirohoo.controlexcel.dummy

import org.springframework.data.jpa.repository.JpaRepository

interface DummyJpaRepository : JpaRepository<Dummy, Long>