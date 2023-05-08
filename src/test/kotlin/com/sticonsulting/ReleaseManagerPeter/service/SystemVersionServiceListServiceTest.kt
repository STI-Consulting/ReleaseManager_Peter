package com.sticonsulting.ReleaseManagerPeter.service

import com.sticonsulting.ReleaseManagerPeter.dto.ServiceDto
import com.sticonsulting.ReleaseManagerPeter.dto.SystemVersionDto
import com.sticonsulting.ReleaseManagerPeter.entity.ServiceEntity
import com.sticonsulting.ReleaseManagerPeter.entity.Service_X_SystemVersionEntity
import com.sticonsulting.ReleaseManagerPeter.entity.SystemVersionEntity
import com.sticonsulting.ReleaseManagerPeter.repository.ServiceRepository
import com.sticonsulting.ReleaseManagerPeter.repository.Service_X_SystemVersionRepository
import com.sticonsulting.ReleaseManagerPeter.repository.SystemVersionRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.mockito.Mockito
import kotlin.NoSuchElementException

internal class SystemVersionServiceListServiceTest {

    val serviceRepository = Mockito.mock(ServiceRepository::class.java)
    val systemVersionRepository = Mockito.mock(SystemVersionRepository::class.java)
    val serviceXSystemVersionRepository = Mockito.mock(Service_X_SystemVersionRepository::class.java)
    val systemVersionService = SystemVersionService(
        serviceRepository=serviceRepository,
        systemVersionRepository=systemVersionRepository,
        serviceXSystemVersionRepository=serviceXSystemVersionRepository)

    @Test
    fun when_requestedSystemVersionDoesNotExists_then_responseSystemVersionWithNoSystems() {
        Mockito.`when`(serviceXSystemVersionRepository.findBySystemVersion(1)).thenReturn(
            ArrayList<Service_X_SystemVersionEntity>())

        Mockito.`when`(systemVersionRepository.findAll()).thenThrow(NoSuchElementException())

        var systemVersionDto = systemVersionService.listService(1)

        Assertions.assertEquals(1,systemVersionDto.systemVersion)
        Assertions.assertEquals(0,systemVersionDto.systems.size)
    }

    @Test
    fun when_onlyRequestedSystemVersionExists_then_responseSystemVersionWithSystems() {
        Mockito.`when`(serviceXSystemVersionRepository.findBySystemVersion(4)).thenReturn(
            listOf(
                Service_X_SystemVersionEntity(systemVersion = 4, serviceName = "Service 1", serviceVersion = 1),
                Service_X_SystemVersionEntity(systemVersion = 4, serviceName = "Service 2", serviceVersion = 1),
                Service_X_SystemVersionEntity(systemVersion = 4, serviceName = "Service 3", serviceVersion = 2)))

        var systemVersionDto = systemVersionService.listService(4)

        Assertions.assertEquals(4,systemVersionDto.systemVersion)
        Assertions.assertEquals(3,systemVersionDto.systems.size)
    }
}