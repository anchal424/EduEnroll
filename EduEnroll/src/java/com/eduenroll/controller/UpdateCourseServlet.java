package com.eduenroll.controller;

import com.eduenroll.beans.CourseBean;
import com.eduenroll.dao.CourseDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/UpdateCourseServlet")
public class UpdateCourseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CourseBean course = new CourseBean();
        course.setId(request.getParameter("courseId"));
        course.setTitle(request.getParameter("title"));
        course.setCategory(request.getParameter("category"));
        course.setDescription(request.getParameter("description"));
        course.setDuration(request.getParameter("duration"));
        course.setPrice(Double.parseDouble(request.getParameter("price")));
        course.setInstructor(request.getParameter("instructor"));
        course.setImage(request.getParameter("image"));

        String modulesText = request.getParameter("modules");
        if (modulesText != null && !modulesText.trim().isEmpty()) {
            course.setModules(modulesText.split("\\|\\|"));
        } else {
            course.setModules(new String[]{"No module content available."});
        }

        CourseDAO dao = new CourseDAO();
        dao.updateCourse(course);

        response.sendRedirect("ViewCoursesServlet");
    }
}