package com.eduenroll.controller;

import com.eduenroll.beans.EnrollmentBean;
import com.eduenroll.dao.EnrollmentDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewEnrollmentsServlet")
public class ViewEnrollmentsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EnrollmentDAO dao = new EnrollmentDAO();
        List<EnrollmentBean> enrollmentList = dao.getAllEnrollments();
        request.setAttribute("enrollmentList", enrollmentList);
        request.getRequestDispatcher("enrollments.jsp").forward(request, response);
    }
}