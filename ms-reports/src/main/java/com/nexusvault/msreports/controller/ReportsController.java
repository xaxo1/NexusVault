package com.nexusvault.msreports.controller;

import com.nexusvault.msreports.model.ModelReports;
import com.nexusvault.msreports.repository.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    @Autowired
    private ReportsRepository reportsRepository;

    @GetMapping("/history")
    public List<ModelReports> getAllReports() {
        return reportsRepository.findAll();
    }

    @GetMapping("/search")
    public List<ModelReports> getByType(@RequestParam String type) {
        return reportsRepository.findByTipoReporte(type);
    }
}