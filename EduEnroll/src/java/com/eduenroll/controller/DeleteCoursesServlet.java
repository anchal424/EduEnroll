package com.eduenroll.controller;

import com.eduenroll.dao.CourseDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DeleteCourseServlet")
public class DeleteCoursesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String courseId = request.getParameter("courseId");

        CourseDAO dao = new CourseDAO();
        dao.deleteCourse(courseId);

        response.sendRedirect("ViewCoursesServlet");
    }
}