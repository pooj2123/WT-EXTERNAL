package com.vnrvjiet.hospital.dao;

import com.vnrvjiet.hospital.model.Doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class DoctorDao {
    private static final List<Doctor> doctors = new ArrayList<>();
    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    static {
        doctors.add(new Doctor(idGenerator.incrementAndGet(), "Anita Sharma", "Cardiologist", "anita.sharma@hospital.com", "9876543210", "Mon - Fri"));
        doctors.add(new Doctor(idGenerator.incrementAndGet(), "Rahul Verma", "Pediatrician", "rahul.verma@hospital.com", "9123456780", "Tue - Sat"));
    }

    public List<Doctor> listAll() {
        return new ArrayList<>(doctors);
    }

    public Optional<Doctor> findById(int id) {
        return doctors.stream().filter(d -> d.getId() == id).findFirst();
    }

    public void add(Doctor doctor) {
        doctor.setId(idGenerator.incrementAndGet());
        doctors.add(doctor);
    }

    public boolean update(Doctor updatedDoctor) {
        return findById(updatedDoctor.getId()).map(existing -> {
            existing.setName(updatedDoctor.getName());
            existing.setSpecialization(updatedDoctor.getSpecialization());
            existing.setEmail(updatedDoctor.getEmail());
            existing.setPhone(updatedDoctor.getPhone());
            existing.setAvailability(updatedDoctor.getAvailability());
            return true;
        }).orElse(false);
    }

    public boolean delete(int id) {
        return doctors.removeIf(d -> d.getId() == id);
    }
}
