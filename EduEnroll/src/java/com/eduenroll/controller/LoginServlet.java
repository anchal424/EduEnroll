package com.eduenroll.controller;

import com.eduenroll.beans.UserBean;
import com.eduenroll.dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();
        UserBean user = dao.loginUser(email, password);

        if (user != null && "student".equals(user.getRole())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("DashboardServlet");
        } else {
            request.setAttribute("error", "Invalid credentials");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}