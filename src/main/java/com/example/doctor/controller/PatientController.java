package com.example.doctor.controller;

import com.example.doctor.dao.DoctorRepository;
import com.example.doctor.model.Doctor;
import com.example.doctor.model.Patient;
import com.example.doctor.service.PatientService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class PatientController {
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientService service;

    @PostMapping(value = "/patient")
    public String savePatient(@RequestBody String patientRequest) {
        JSONObject json = new JSONObject(patientRequest);
        Patient patient = setPatient(json);
        service.savePatient(patient);

        return "saved patient";
    }

    private Patient setPatient(JSONObject json) {
        Patient patient = new Patient();

        patient.setPatientId(json.getInt("patientId"));
        patient.setPatientName(json.getString("patientName"));
        patient.setAge(json.getInt("age"));
        patient.setPhoneNumber(json.getString("phoneNumber"));
        patient.setDiseaseType(json.getString("diseaseType"));
        patient.setGender(json.getString("gender"));

        Timestamp currTime = new Timestamp(System.currentTimeMillis());
        patient.setAdmitDate(currTime);

        String doctorID = json.getString("doctorId");
        Doctor doctor = doctorRepository.findById(Integer.valueOf(doctorID)).get();
        patient.setDoctorId(doctor);

        return patient;
    }
}
