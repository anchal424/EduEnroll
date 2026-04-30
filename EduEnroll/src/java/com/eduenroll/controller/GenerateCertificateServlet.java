package com.eduenroll.controller;

import com.eduenroll.beans.CertificateBean;
import com.eduenroll.beans.EnrollmentBean;
import com.eduenroll.beans.UserBean;
import com.eduenroll.dao.CertificateDAO;
import com.eduenroll.dao.EnrollmentDAO;
import com.eduenroll.util.IdGenerator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/GenerateCertificateServlet")
public class GenerateCertificateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UserBean user = (UserBean) session.getAttribute("user");
        String courseId = request.getParameter("courseId");

        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        EnrollmentBean enrollment = enrollmentDAO.getEnrollmentByStudentAndCourse(user.getId(), courseId);

        if (enrollment == null || !"Completed".equals(enrollment.getStatus())) {
            response.sendRedirect("LearningServlet?courseId=" + courseId);
            return;
        }

        CertificateBean cert = new CertificateBean();
        cert.setId(IdGenerator.generate("CERTID"));
        cert.setStudentId(user.getId());
        cert.setCourseId(courseId);
        cert.setCertificateNo(IdGenerator.generate("CERT"));

        CertificateDAO certDAO = new CertificateDAO();
        certDAO.addCertificate(cert);

        response.sendRedirect("CertificateServlet");
    }
}