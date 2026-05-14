<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.vnrvjiet.hospital.model.Doctor" %>
<%@ page import="java.util.List" %>
<%
    List<Doctor> doctors = (List<Doctor>) request.getAttribute("doctors");
    if (doctors == null) {
        doctors = java.util.Collections.emptyList();
    }
    Doctor editDoctor = (Doctor) request.getAttribute("doctor");
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Hospital Doctor Management</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<header class="hero-header">
    <div class="logo-panel">
        <div class="logo-mark">HV</div>
        <div>
            <h1>VNRVJIET Hospital</h1>
            <p>Doctor record management made simple</p>
        </div>
    </div>
    <nav class="top-nav">
        <a href="#doctor-form">Add Doctor</a>
        <a href="#doctor-table">Doctor List</a>
    </nav>
</header>
<main class="page-shell">
    <section id="doctor-form" class="panel">
        <div class="panel-header">
            <h3><%= editDoctor == null ? "Add New Doctor" : "Edit Doctor" %></h3>
            <span class="status-chip"><%= editDoctor == null ? "Create" : "Edit" %></span>
        </div>
        <% if (message != null) { %>
            <div class="alert success"><%= message %></div>
        <% } %>
        <% if (error != null) { %>
            <div class="alert error"><%= error %></div>
        <% } %>
        <form method="post" action="doctors" novalidate>
            <input type="hidden" name="id" value="<%= editDoctor != null ? editDoctor.getId() : "" %>" />
            <input type="hidden" name="action" value="<%= editDoctor == null ? "add" : "update" %>" />

            <label>
                Doctor Name
                <input name="name" type="text" required minlength="3" value="<%= editDoctor != null ? editDoctor.getName() : "" %>" />
            </label>
            <label>
                Specialization
                <input name="specialization" type="text" required minlength="3" value="<%= editDoctor != null ? editDoctor.getSpecialization() : "" %>" />
            </label>
            <label>
                Email Address
                <input name="email" type="email" required value="<%= editDoctor != null ? editDoctor.getEmail() : "" %>" />
            </label>
            <label>
                Phone Number
                <input name="phone" type="tel" required pattern="[0-9]{10}" value="<%= editDoctor != null ? editDoctor.getPhone() : "" %>" placeholder="10-digit mobile" />
            </label>
            <label>
                Availability
                <input name="availability" type="text" required minlength="3" value="<%= editDoctor != null ? editDoctor.getAvailability() : "" %>" placeholder="Mon - Fri, Tue - Sat, etc." />
            </label>
            <button type="submit" class="primary-btn"><%= editDoctor == null ? "Add Doctor" : "Save Changes" %></button>
            <% if (editDoctor != null) { %>
                <a class="secondary-btn" href="doctors">Cancel</a>
            <% } %>
        </form>
    </section>

    <section id="doctor-table" class="panel table-panel">
        <div class="panel-header">
            <h3>Doctor Records</h3>
            <p class="subtle">Click edit to update or delete a record.</p>
        </div>
        <div class="table-wrapper">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Specialization</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Availability</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                <% for (Doctor doctor : doctors) { %>
                    <tr>
                        <td><%= doctor.getId() %></td>
                        <td><%= doctor.getName() %></td>
                        <td><%= doctor.getSpecialization() %></td>
                        <td><%= doctor.getEmail() %></td>
                        <td><%= doctor.getPhone() %></td>
                        <td><%= doctor.getAvailability() %></td>
                        <td class="actions">
                            <a href="doctors?action=edit&id=<%= doctor.getId() %>" class="action-link">Edit</a>
                            <a href="doctors?action=delete&id=<%= doctor.getId() %>" class="action-link delete-link" onclick="return confirm('Delete this doctor record?');">Delete</a>
                        </td>
                    </tr>
                <% } %>
                <% if (doctors.isEmpty()) { %>
                    <tr><td colspan="7" class="empty-state">No doctors available. Add a record to get started.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </section>
</main>
<footer class="page-footer">
    <p>© 2026 VNRVJIET Hospital Management. Designed for clean doctor CRUD workflows.</p>
</footer>
</body>
</html>
