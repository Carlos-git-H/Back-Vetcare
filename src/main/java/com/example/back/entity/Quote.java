package com.example.back.entity;

import java.time.LocalDate;
import java.time.LocalTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="VC_TBQU")
public class Quote {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idQuote;

    @ManyToOne(optional=false)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne(optional=false)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @ManyToOne(optional=false)
    @JoinColumn(name = "metPage_id")
    private MetPag metPag;

    @Column(columnDefinition= "CHAR(1) DEFAULT '1'", nullable=false)
    private String statusPag;

    @Column(nullable=false)
    private LocalDate date;

    @Column(nullable=false)
    private LocalTime hour;

    @Column(nullable=false)
    private String comments;

    @Column(columnDefinition= "CHAR(1) DEFAULT '1'", nullable=false)
    private Character status;

    public Pet getPet() {
        return pet;
    }
    
    public void setPet(Pet pet) {
        this.pet = pet;
    }
    
    public ServiceEntity getService() {
        return service;
    }
    
    public void setService(ServiceEntity service) {
        this.service = service;
    }
    
    public MetPag getMetPag() {
        return metPag;
    }
    
    public void setMetPag(MetPag metPag) {
        this.metPag = metPag;
    }
    
    public String getStatusPag() {
        return statusPag;
    }
    
    public void setStatusPag(String statusPag) {
        this.statusPag = statusPag;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public LocalTime getHour() {
        return hour;
    }
    
    public void setHour(LocalTime hour) {
        this.hour = hour;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public Character getStatus() {
        return status;
    }
    
    public void setStatus(Character status) {
        this.status = status;
    }
    
}
