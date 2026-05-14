# Hospital Management Servlet App

A Java Servlet-based sample hospital management interface for doctor CRUD operations.

## Features
- Create, read, update, delete doctor records
- Responsive UI with header, footer, and clean modern styling
- HTML and server-side validation for required fields, email, and phone
- In-memory data storage for demonstration purposes

## Run
1. Open the project in a Java web IDE or build with Maven.
2. Run `mvn clean package`.
3. Deploy the generated WAR to a servlet container such as Apache Tomcat.
4. Visit `http://localhost:8080/HospitalManagementServletApp/doctors`.

## Project structure
- `src/main/java/com/vnrvjiet/hospital/model/Doctor.java`
- `src/main/java/com/vnrvjiet/hospital/dao/DoctorDao.java`
- `src/main/java/com/vnrvjiet/hospital/servlets/DoctorServlet.java`
- `src/main/webapp/index.jsp`
- `src/main/webapp/css/style.css`
- `src/main/webapp/WEB-INF/web.xml`
