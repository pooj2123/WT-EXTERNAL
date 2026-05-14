package com.vnrvjiet.hospital.servlets;

import com.vnrvjiet.hospital.dao.DoctorDao;
import com.vnrvjiet.hospital.model.Doctor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "DoctorServlet", urlPatterns = {"/doctors"})
public class DoctorServlet extends HttpServlet {
    private final DoctorDao doctorDao = new DoctorDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "list" : request.getParameter("action");
        switch (action) {
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteDoctor(request, response);
                break;
            default:
                listDoctors(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action") == null ? "add" : request.getParameter("action");
        if ("update".equals(action)) {
            updateDoctor(request, response);
        } else {
            addDoctor(request, response);
        }
    }

    private void listDoctors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("doctors", doctorDao.listAll());
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = parseId(request.getParameter("id"));
        Optional<Doctor> doctorOpt = doctorDao.findById(id);
        if (doctorOpt.isPresent()) {
            request.setAttribute("doctor", doctorOpt.get());
        } else {
            request.setAttribute("message", "Doctor not found for editing.");
        }
        listDoctors(request, response);
    }

    private void addDoctor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Doctor doctor = buildDoctorFromRequest(request);
        String validationError = validateDoctor(doctor, false);
        if (validationError != null) {
            request.setAttribute("error", validationError);
            request.setAttribute("doctor", doctor);
            listDoctors(request, response);
            return;
        }
        doctorDao.add(doctor);
        response.sendRedirect("doctors");
    }

    private void updateDoctor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Doctor doctor = buildDoctorFromRequest(request);
        doctor.setId(parseId(request.getParameter("id")));
        String validationError = validateDoctor(doctor, true);
        if (validationError != null) {
            request.setAttribute("error", validationError);
            request.setAttribute("doctor", doctor);
            listDoctors(request, response);
            return;
        }
        boolean updated = doctorDao.update(doctor);
        if (!updated) {
            request.setAttribute("error", "Unable to update doctor record.");
            request.setAttribute("doctor", doctor);
            listDoctors(request, response);
            return;
        }
        response.sendRedirect("doctors");
    }

    private void deleteDoctor(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = parseId(request.getParameter("id"));
        doctorDao.delete(id);
        response.sendRedirect("doctors");
    }

    private Doctor buildDoctorFromRequest(HttpServletRequest request) {
        Doctor doctor = new Doctor();
        doctor.setName(trimParam(request.getParameter("name")));
        doctor.setSpecialization(trimParam(request.getParameter("specialization")));
        doctor.setEmail(trimParam(request.getParameter("email")));
        doctor.setPhone(trimParam(request.getParameter("phone")));
        doctor.setAvailability(trimParam(request.getParameter("availability")));
        return doctor;
    }

    private String validateDoctor(Doctor doctor, boolean isUpdate) {
        if (doctor.getName().isEmpty()) {
            return "Doctor name is required.";
        }
        if (doctor.getSpecialization().isEmpty()) {
            return "Specialization is required.";
        }
        if (doctor.getEmail().isEmpty() || !doctor.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return "A valid email address is required.";
        }
        if (doctor.getPhone().isEmpty() || !doctor.getPhone().matches("^[0-9]{10}$")) {
            return "Phone must be 10 digits.";
        }
        if (doctor.getAvailability().isEmpty()) {
            return "Availability details are required.";
        }
        if (isUpdate && doctor.getId() <= 0) {
            return "Invalid record for update.";
        }
        return null;
    }

    private String trimParam(String value) {
        return value == null ? "" : value.trim();
    }

    private int parseId(String param) {
        try {
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
