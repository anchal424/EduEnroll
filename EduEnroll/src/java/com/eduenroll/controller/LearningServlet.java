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

@WebServlet("/LearningServlet")
public class LearningServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UserBean user = (UserBean) session.getAttribute("user");
        String courseId = request.getParameter("courseId");

        CourseDAO courseDAO = new CourseDAO();
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

        CourseBean course = courseDAO.getCourseById(courseId);
        EnrollmentBean enrollment = enrollmentDAO.getEnrollmentByStudentAndCourse(user.getId(), courseId);

        if (course == null || enrollment == null) {
            response.sendRedirect("DashboardServlet");
            return;
        }

        int currentModuleIndex = enrollment.getCurrentModule() - 1;
        String currentModuleTitle = "";
        String currentModuleContent = "";

        if (course.getModules() != null
                && currentModuleIndex >= 0
                && currentModuleIndex < course.getModules().length) {

            currentModuleTitle = "Module " + enrollment.getCurrentModule();
            currentModuleContent = course.getModules()[currentModuleIndex];
        }

        request.setAttribute("course", course);
        request.setAttribute("enrollment", enrollment);
        request.setAttribute("currentModuleTitle", currentModuleTitle);
        request.setAttribute("currentModuleContent", currentModuleContent);
        request.getRequestDispatcher("learning.jsp").forward(request, response);
    }
}