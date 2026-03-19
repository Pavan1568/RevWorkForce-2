package com.revworkforce.controller;

import com.revworkforce.entity.Designation;
import com.revworkforce.repository.DesignationRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/admin/designations")
@PreAuthorize("hasRole('ADMIN')")
public class DesignationController {

    private final DesignationRepository designationRepository;

    public DesignationController(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    @GetMapping
    public List<Designation> getAll() {
        return designationRepository.findAll();
    }
}