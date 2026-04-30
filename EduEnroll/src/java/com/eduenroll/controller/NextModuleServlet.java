package com.eduenroll.controller;

import com.eduenroll.dao.EnrollmentDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/NextModuleServlet")
public class NextModuleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String enrollmentId = request.getParameter("enrollmentId");
        String courseId = request.getParameter("courseId");

        EnrollmentDAO dao = new EnrollmentDAO();
        dao.moveToNextModule(enrollmentId);

        response.sendRedirect("LearningServlet?courseId=" + courseId);
    }
}