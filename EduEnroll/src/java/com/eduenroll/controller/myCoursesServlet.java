package com.eduenroll.controller;

import com.eduenroll.beans.CourseBean;
import com.eduenroll.beans.EnrollmentBean;
import com.eduenroll.beans.UserBean;
import com.eduenroll.dao.CourseDAO;
import com.eduenroll.dao.EnrollmentDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/MyCoursesServlet")
public class myCoursesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        UserBean user = (UserBean) session.getAttribute("user");
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        CourseDAO courseDAO = new CourseDAO();

        List<EnrollmentBean> enrollments = enrollmentDAO.getEnrollmentsByStudent(user.getId());
        List<CourseBean> myCourses = new ArrayList<CourseBean>();

        for (EnrollmentBean e : enrollments) {
            CourseBean c = courseDAO.getCourseById(e.getCourseId());
            if (c != null) {
                myCourses.add(c);
            }
        }

        request.setAttribute("myCourses", myCourses);
        request.setAttribute("enrollments", enrollments);
        request.getRequestDispatcher("myCourses.jsp").forward(request, response);
    }
}