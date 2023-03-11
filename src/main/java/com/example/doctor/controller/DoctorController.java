package com.example.doctor.controller;

import com.example.doctor.model.Doctor;
import com.example.doctor.service.DoctorService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class DoctorController {
    @Autowired
    DoctorService service;

    @PostMapping(value = "/doctor")
    public ResponseEntity<String> saveDoctor(@RequestBody String requestDoctor) {
        JSONObject json = new JSONObject(requestDoctor);
        List<String> validationList = validateDoctor(json);

        if(validationList.isEmpty()) {
            Doctor doctor = setDoctor(json);
            service.saveDoctor(doctor);
            return new ResponseEntity<>("Doctor saved", HttpStatus.CREATED);
        }
        else {
            String[] answer = Arrays.copyOf(
                    validationList.toArray(), validationList.size(), String[].class
            );
            return new ResponseEntity<>("Please pass the mandatory parameters- " + Arrays.toString(answer),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/doctor")
    public List<Doctor> getDoctor(@RequestParam String doctorId) {
        return service.getDoctor(doctorId);
    }

    private List<String> validateDoctor(JSONObject json) {
        List<String> errorList = new ArrayList<>();
    if(!json.has("doctorId")) {
        errorList.add("doctorId");
        }
    if(!json.has("doctorName")) {
        errorList.add("doctorName");
        }
    if(!json.has("specializedIn")) {
        errorList.add("specializedIn");
        }
    return errorList;
    }

    public Doctor setDoctor (JSONObject json) {
        Doctor doctor = new Doctor();
        String doctorId = json.getString("doctorId");
        doctor.setDoctorId(Integer.valueOf(doctorId));

        String doctorName = json.getString("doctorName");
        doctor.setDoctorName(doctorName);

        String specializedIn = json.getString("specializedIn");
        doctor.setSpecializedIn(specializedIn);

        if(json.has("experience")) {
            String exp = json.getString("experience");
            doctor.setExperience(exp);
        }
        return doctor;
    }
}
