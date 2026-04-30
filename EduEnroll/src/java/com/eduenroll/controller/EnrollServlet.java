package com.eduenroll.controller;

import com.eduenroll.beans.CourseBean;
import com.eduenroll.beans.EnrollmentBean;
import com.eduenroll.beans.UserBean;
import com.eduenroll.dao.CourseDAO;
import com.eduenroll.dao.EnrollmentDAO;
import com.eduenroll.dao.PaymentDAO;
import com.eduenroll.beans.PaymentBean;
import com.eduenroll.util.IdGenerator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/EnrollServlet")
public class EnrollServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        UserBean user = (UserBean) session.getAttribute("user");
        String courseId = request.getParameter("courseId");

        CourseDAO courseDAO = new CourseDAO();
        CourseBean course = courseDAO.getCourseById(courseId);
        if (course == null) {
            response.sendRedirect("DashboardServlet");
            return;
        }

        request.setAttribute("course", course);
        request.getRequestDispatcher("payment.jsp").forward(request, response);
    }
}