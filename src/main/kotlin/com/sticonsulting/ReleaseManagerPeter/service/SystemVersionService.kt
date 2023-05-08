package com.sticonsulting.ReleaseManagerPeter.service

import com.sticonsulting.ReleaseManagerPeter.dto.ServiceDto
import com.sticonsulting.ReleaseManagerPeter.dto.SystemVersionDto
import com.sticonsulting.ReleaseManagerPeter.entity.ServiceEntity
import com.sticonsulting.ReleaseManagerPeter.entity.Service_X_SystemVersionEntity
import com.sticonsulting.ReleaseManagerPeter.entity.SystemVersionEntity
import com.sticonsulting.ReleaseManagerPeter.repository.ServiceRepository
import com.sticonsulting.ReleaseManagerPeter.repository.Service_X_SystemVersionRepository
import com.sticonsulting.ReleaseManagerPeter.repository.SystemVersionRepository
import org.springframework.stereotype.Service

@Service
class SystemVersionService(
        private val serviceRepository: ServiceRepository,
        private val systemVersionRepository: SystemVersionRepository,
        private val serviceXSystemVersionRepository: Service_X_SystemVersionRepository
) {
    fun notifyAboutNewPossibleSystemVersion(serviceDto: ServiceDto) : Int {
        val serviceEntity = serviceRepository.findByName(serviceDto.name)
        if(isANewSystemVersionRequired(serviceEntity, serviceDto)){
            var newSystemVersionEntity = SystemVersionEntity(systemVersion = createNewSystemVersion())
            systemVersionRepository.save(newSystemVersionEntity)
            serviceRepository.save(ServiceEntity(name = serviceDto.name, version=serviceDto.version))
            createNewSystemVersionSnapShot(newSystemVersionEntity)
            return newSystemVersionEntity.systemVersion
        }

        return systemVersionRepository.findAllByOrderByIdDesc().first().systemVersion;
    }

    fun listService(systemVersion: Int) : SystemVersionDto{
        val servicesForSystemVersion = serviceXSystemVersionRepository.findBySystemVersion(systemVersion=systemVersion)
        if (servicesForSystemVersion != null && servicesForSystemVersion.isNotEmpty()){
            val systemVersionDto = SystemVersionDto(systemVersion=systemVersion, mutableListOf<ServiceDto>())
            for(service in servicesForSystemVersion){
                systemVersionDto.systems.add(ServiceDto(name = service.serviceName, version = service.serviceVersion))
            }
            return systemVersionDto
        }
        return SystemVersionDto(systemVersion=systemVersion, systems = ArrayList<ServiceDto>())
    }

    private fun isANewSystemVersionRequired(
            serviceEntity: ServiceEntity?,
            serviceDto: ServiceDto) =
        (serviceEntity == null || serviceEntity.version != serviceDto.version)

    private fun createNewSystemVersionSnapShot(
        newSystemVersionEntity: SystemVersionEntity
    ) {
        val currentServiceEntities = getServiceEntitiesWithHighestVersion()
        for (currentServiceEntity in currentServiceEntities) {
            val newSystemVersionAssignment = Service_X_SystemVersionEntity(
                serviceName = currentServiceEntity.name,
                serviceVersion = currentServiceEntity.version,
                systemVersion = newSystemVersionEntity.systemVersion
            )
            serviceXSystemVersionRepository.save(newSystemVersionAssignment)
        }
    }

    private fun getServiceEntitiesWithHighestVersion() =
        serviceRepository.findAll().groupBy { it.name }.mapValues { it.value.last() }.values.toList()

    private fun createNewSystemVersion(): Int {
        var newSystemVersion = 1
        try {
            val systemVersionEntity = systemVersionRepository.findAllByOrderByIdDesc().first()
            return systemVersionEntity.systemVersion +1
        } catch (e: NoSuchElementException) {
            //Keep new System Version
        }
        return newSystemVersion
    }

}