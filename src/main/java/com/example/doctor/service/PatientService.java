package com.example.doctor.service;

import com.example.doctor.dao.PatientRepository;
import com.example.doctor.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    PatientRepository repository;
    public void savePatient(Patient patient) {
        repository.save(patient);
    }
}
