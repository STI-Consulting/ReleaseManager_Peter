package com.sticonsulting.ReleaseManagerPeter.service

import com.sticonsulting.ReleaseManagerPeter.dto.ServiceDto
import com.sticonsulting.ReleaseManagerPeter.entity.ServiceEntity
import com.sticonsulting.ReleaseManagerPeter.entity.SystemVersionEntity
import com.sticonsulting.ReleaseManagerPeter.repository.ServiceRepository
import com.sticonsulting.ReleaseManagerPeter.repository.Service_X_SystemVersionRepository
import com.sticonsulting.ReleaseManagerPeter.repository.SystemVersionRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.mockito.Mockito
import kotlin.NoSuchElementException

internal class SystemVersionServiceNotifyAboutNewPossibleSystemVersionTest {

    val serviceRepository = Mockito.mock(ServiceRepository::class.java)
    val systemVersionRepository = Mockito.mock(SystemVersionRepository::class.java)
    val serviceXSystemVersionRepository = Mockito.mock(Service_X_SystemVersionRepository::class.java)
    val systemVersionService = SystemVersionService(
        serviceRepository=serviceRepository,
        systemVersionRepository=systemVersionRepository,
        serviceXSystemVersionRepository=serviceXSystemVersionRepository)

    @Test
    fun when_firstServiceIsProvided_then_responseSystemVersion1() {
        var serviceDto = ServiceDto(name = "Service 1", version = 1)
        Mockito.`when`(systemVersionRepository.findAll()).thenThrow(NoSuchElementException())

        var systemVersion = systemVersionService.notifyAboutNewPossibleSystemVersion(serviceDto)

        Assertions.assertEquals(1,systemVersion)
    }

    @Test
    fun when_existingServiceWithSameVersionIsProvided_then_responseNotANewSystemVersion() {
        var serviceDto = ServiceDto(name = "Service 1", version = 1)
        Mockito.`when`(serviceRepository.findByName(serviceDto.name)).thenReturn(ServiceEntity(name = serviceDto.name, version=serviceDto.version))
        Mockito.`when`(serviceRepository.findAll()).thenReturn(listOf(ServiceEntity(name = serviceDto.name, version=serviceDto.version)))
        Mockito.`when`(systemVersionRepository.findAll()).thenReturn(listOf(SystemVersionEntity(systemVersion = 1)))
        Mockito.`when`(systemVersionRepository.findAllByOrderByIdDesc()).thenReturn(listOf(SystemVersionEntity(systemVersion = 1)))

        var systemVersion = systemVersionService.notifyAboutNewPossibleSystemVersion(serviceDto)

        Assertions.assertEquals(1,systemVersion)
    }

    @Test
    fun when_existingServiceWithNewVersionIsProvided_then_responseANewSystemVersion() {
        var serviceDto = ServiceDto(name = "Service 1", version = 1)
        Mockito.`when`(serviceRepository.findByName(serviceDto.name)).thenReturn(ServiceEntity(name = serviceDto.name, version=serviceDto.version))
        Mockito.`when`(serviceRepository.findAll()).thenReturn(listOf(ServiceEntity(name = serviceDto.name, version=serviceDto.version++)))
        Mockito.`when`(systemVersionRepository.findAllByOrderByIdDesc()).thenReturn(listOf(SystemVersionEntity(systemVersion = 1)))

        var systemVersion = systemVersionService.notifyAboutNewPossibleSystemVersion(serviceDto)

        Assertions.assertEquals(2,systemVersion)
    }

    @Test
    fun when_secondServiceIsNewlyProvided_then_responseANewSystemVersion() {
        var service1Dto = ServiceDto(name = "Service 1", version = 1)
        var service2Dto = ServiceDto(name = "Service 2", version = 1)
        Mockito.`when`(serviceRepository.findByName(service1Dto.name)).thenReturn(ServiceEntity(name = service1Dto.name, version=service1Dto.version))
        Mockito.`when`(serviceRepository.findByName(service2Dto.name)).thenReturn(null)
        Mockito.`when`(serviceRepository.findAll()).thenReturn(
            listOf(
                ServiceEntity(name = service1Dto.name, version=service1Dto.version),
                ServiceEntity(name = service2Dto.name, version=service2Dto.version++)))
        Mockito.`when`(systemVersionRepository.findAllByOrderByIdDesc()).thenReturn(listOf(SystemVersionEntity(systemVersion = 1)))

        var systemVersion = systemVersionService.notifyAboutNewPossibleSystemVersion(service2Dto)

        Assertions.assertEquals(2,systemVersion)
    }
}