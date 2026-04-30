package com.eduenroll.controller;

import com.eduenroll.beans.UserAnalyticsBean;
import com.eduenroll.beans.UserBean;
import com.eduenroll.dao.UserAnalyticsDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/UserAnalyticsServlet")
public class UserAnalyticsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UserBean user = (UserBean) session.getAttribute("user");

        UserAnalyticsDAO dao = new UserAnalyticsDAO();

        List<UserAnalyticsBean> progressData = dao.getMyProgressData(user.getId());
        UserAnalyticsBean completionSummary = dao.getMyCompletionSummary(user.getId());
        int certificateCount = dao.getCertificateCount(user.getId());
        int enrolledCount = dao.getEnrolledCourseCount(user.getId());

        request.setAttribute("progressData", progressData);
        request.setAttribute("completionSummary", completionSummary);
        request.setAttribute("certificateCount", certificateCount);
        request.setAttribute("enrolledCount", enrolledCount);

        request.getRequestDispatcher("myAnalytics.jsp").forward(request, response);
    }
}