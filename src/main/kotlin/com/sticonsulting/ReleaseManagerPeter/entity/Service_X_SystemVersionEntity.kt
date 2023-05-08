package com.sticonsulting.ReleaseManagerPeter.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table( name = "Service_X_SystemVersionEntity")
data class Service_X_SystemVersionEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(name = "serviceName", nullable = false)
    var serviceName: String,

    @Column(name = "serviceVersion", nullable = false)
    var serviceVersion: Int,

    @Column(name = "systemVersion", nullable = false)
    var systemVersion: Int
)