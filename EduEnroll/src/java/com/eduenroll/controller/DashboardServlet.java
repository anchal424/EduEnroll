package com.eduenroll.controller;

import com.eduenroll.beans.CourseBean;
import com.eduenroll.beans.EnrollmentBean;
import com.eduenroll.beans.UserBean;
import com.eduenroll.dao.CourseDAO;
import com.eduenroll.dao.EnrollmentDAO;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        UserBean user = (UserBean) session.getAttribute("user");
        CourseDAO courseDAO = new CourseDAO();
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

        List<CourseBean> courseList = courseDAO.getAllCourses();
        List<EnrollmentBean> enrollmentList = enrollmentDAO.getEnrollmentsByStudent(user.getId());

        Map<String, EnrollmentBean> enrollmentMap = new HashMap<String, EnrollmentBean>();
        for (EnrollmentBean e : enrollmentList) {
            enrollmentMap.put(e.getCourseId(), e);
        }

        request.setAttribute("courseList", courseList);
        request.setAttribute("enrollmentMap", enrollmentMap);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}