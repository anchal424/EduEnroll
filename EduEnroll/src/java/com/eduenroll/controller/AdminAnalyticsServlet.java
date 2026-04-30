package com.eduenroll.controller;

import com.eduenroll.beans.AdminAnalyticsBean;
import com.eduenroll.dao.AdminAnalyticsDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AdminAnalyticsServlet")
public class AdminAnalyticsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AdminAnalyticsDAO dao = new AdminAnalyticsDAO();

        List<AdminAnalyticsBean> enrollmentData = dao.getEnrollmentsByCourse();
        AdminAnalyticsBean completionData = dao.getCompletionStatus();
        List<AdminAnalyticsBean> revenueData = dao.getRevenueByCourse();

        request.setAttribute("enrollmentData", enrollmentData);
        request.setAttribute("completionData", completionData);
        request.setAttribute("revenueData", revenueData);

        request.getRequestDispatcher("adminAnalytics.jsp").forward(request, response);
    }
}