package com.sticonsulting.ReleaseManagerPeter.repository

import com.sticonsulting.ReleaseManagerPeter.entity.SystemVersionEntity
import org.springframework.data.repository.CrudRepository

interface SystemVersionRepository: CrudRepository<SystemVersionEntity, Long> {
    fun findAllByOrderByIdDesc():List<SystemVersionEntity>
}