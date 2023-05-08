package com.sticonsulting.ReleaseManagerPeter.repository

import com.sticonsulting.ReleaseManagerPeter.entity.Service_X_SystemVersionEntity
import com.sticonsulting.ReleaseManagerPeter.entity.SystemVersionEntity
import org.springframework.data.repository.CrudRepository

interface Service_X_SystemVersionRepository: CrudRepository<Service_X_SystemVersionEntity, Long> {
    fun findBySystemVersion(systemVersion: Int): List<Service_X_SystemVersionEntity>
}