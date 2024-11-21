package com.example.back.controller;

import java.time.LocalDate;



import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.back.entity.Quote;
import com.example.back.service.ReportService;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/report")
public class ReportController {
    
    @Autowired
    private ReportService reportService;

    @GetMapping("/appointments")
    public ResponseEntity<Page<Quote>> getAppointmentReport(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "status", required = false) Character status,
            @RequestParam(value = "statusPag", required = false) String statusPag,
            @RequestParam(value = "metPag", required = false) String metPag,
            @RequestParam(value = "species", required = false) String species,
            @RequestParam(value = "serviceName", required = false) String serviceName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        LocalDate startDate = (startDateStr != null) ? LocalDate.parse(startDateStr) : null;
        LocalDate endDate = (endDateStr != null) ? LocalDate.parse(endDateStr) : null;

        Pageable pageable = PageRequest.of(page, size);

        Page<Quote> report = reportService.getAppointmentReport(
                startDate, endDate, status, statusPag, metPag, species, serviceName, pageable);

        return ResponseEntity.ok(report);
    }

}
