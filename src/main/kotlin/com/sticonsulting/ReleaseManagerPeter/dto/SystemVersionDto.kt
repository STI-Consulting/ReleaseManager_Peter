package com.sticonsulting.ReleaseManagerPeter.dto

data class SystemVersionDto(
    var systemVersion: Int,
    var systems: MutableList<ServiceDto>
)
