package com.sticonsulting.ReleaseManagerPeter.controller

import com.sticonsulting.ReleaseManagerPeter.dto.ServiceDto
import com.sticonsulting.ReleaseManagerPeter.dto.SystemVersionDto
import com.sticonsulting.ReleaseManagerPeter.service.SystemVersionService
import org.springframework.web.bind.annotation.*

@RestController
class SystemVersionController(
    private var systemVersionService: SystemVersionService) {

    @PostMapping("/deploy")
    fun deployNewService(
        @RequestBody serviceDto: ServiceDto
    ): Int {
        return systemVersionService.notifyAboutNewPossibleSystemVersion(serviceDto)
    }

    @GetMapping("/services")
    fun getServices(@RequestParam(name = "systemVersion") systemVersion: Int): SystemVersionDto {
        return systemVersionService.listService(systemVersion)
    }
}