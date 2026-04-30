package com.eduenroll.controller;

import com.eduenroll.beans.CertificateBean;
import com.eduenroll.beans.CourseBean;
import com.eduenroll.beans.UserBean;
import com.eduenroll.dao.CertificateDAO;
import com.eduenroll.dao.CourseDAO;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CertificateServlet")
public class CertificateServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
         UserBean user = (UserBean) session.getAttribute("user");
        CertificateDAO certDAO = new CertificateDAO();
        CourseDAO courseDAO = new CourseDAO();

        List<CertificateBean> certificateList = certDAO.getCertificatesByStudent(user.getId());
        Map<String, CourseBean> courseMap = new HashMap<String, CourseBean>();

        for (CertificateBean cert : certificateList) {
            CourseBean c = courseDAO.getCourseById(cert.getCourseId());
            if (c != null) {
                courseMap.put(cert.getCourseId(), c);
            }
        }

        request.setAttribute("certificateList", certificateList);
        request.setAttribute("courseMap", courseMap);
        request.getRequestDispatcher("certificate.jsp").forward(request, response);
    }
}