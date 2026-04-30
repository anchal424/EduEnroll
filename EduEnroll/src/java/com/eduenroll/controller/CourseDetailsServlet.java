package com.eduenroll.controller;

import com.eduenroll.beans.CourseBean;
import com.eduenroll.beans.EnrollmentBean;
import com.eduenroll.beans.UserBean;
import com.eduenroll.dao.CourseDAO;
import com.eduenroll.dao.EnrollmentDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CourseDetailsServlet")
public class CourseDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String courseId = request.getParameter("courseId");

        CourseDAO courseDAO = new CourseDAO();
        CourseBean course = courseDAO.getCourseById(courseId);

        if (course == null) {
            response.sendRedirect("DashboardServlet");
            return;
        }

        HttpSession session = request.getSession(false);
        EnrollmentBean enrollment = null;

        if (session != null && session.getAttribute("user") != null) {
            UserBean user = (UserBean) session.getAttribute("user");
            EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
            enrollment = enrollmentDAO.getEnrollmentByStudentAndCourse(user.getId(), courseId);
        }

        request.setAttribute("course", course);
        request.setAttribute("enrollment", enrollment);
        request.getRequestDispatcher("courseDetails.jsp").forward(request, response);
    }
}