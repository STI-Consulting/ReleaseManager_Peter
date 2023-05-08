package com.sticonsulting.ReleaseManagerPeter.repository

import com.sticonsulting.ReleaseManagerPeter.entity.ServiceEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ServiceRepository: CrudRepository<ServiceEntity, Long> {
    fun findByName (name: String): ServiceEntity?

}
