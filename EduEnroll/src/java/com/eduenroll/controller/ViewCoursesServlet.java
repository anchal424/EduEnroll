package com.eduenroll.controller;

import com.eduenroll.beans.CourseBean;
import com.eduenroll.dao.CourseDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewCoursesServlet")
public class ViewCoursesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CourseDAO dao = new CourseDAO();
        List<CourseBean> courseList = dao.getAllCourses();

        request.setAttribute("courseList", courseList);
        request.getRequestDispatcher("adminCourses.jsp").forward(request, response);
    }
}