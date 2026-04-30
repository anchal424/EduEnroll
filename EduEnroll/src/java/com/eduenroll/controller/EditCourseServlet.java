package com.eduenroll.controller;

import com.eduenroll.beans.CourseBean;
import com.eduenroll.dao.CourseDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/EditCourseServlet")
public class EditCourseServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String courseId = request.getParameter("courseId");

        CourseDAO dao = new CourseDAO();
        CourseBean course = dao.getCourseById(courseId);

        if (course == null) {
            response.sendRedirect("ViewCoursesServlet");
            return;
        }

        request.setAttribute("course", course);
        request.getRequestDispatcher("editCourse.jsp").forward(request, response);
    }
}