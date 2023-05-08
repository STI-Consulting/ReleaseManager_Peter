package com.sticonsulting.ReleaseManagerPeter.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table( name = "SystemVersion")
data class SystemVersionEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(name = "systemVersion", nullable = false)
    var systemVersion: Int
)