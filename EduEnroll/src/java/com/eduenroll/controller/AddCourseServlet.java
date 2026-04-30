package com.eduenroll.controller;

import com.eduenroll.beans.CourseBean;
import com.eduenroll.dao.CourseDAO;
import com.eduenroll.util.IdGenerator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AddCourseServlet")
public class AddCourseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CourseBean course = new CourseBean();
        course.setId(IdGenerator.generate("COURSE"));
        course.setTitle(request.getParameter("title"));
        course.setCategory(request.getParameter("category"));
        course.setDescription(request.getParameter("description"));
        course.setDuration(request.getParameter("duration"));
        course.setPrice(Double.parseDouble(request.getParameter("price")));
        course.setInstructor(request.getParameter("instructor"));
        course.setImage(request.getParameter("image"));

        String material = request.getParameter("material");
        if (material != null && !material.trim().isEmpty()) {
            String[] modules = material.split("\\|\\|");
            course.setModules(modules);
        } else {
            course.setModules(new String[]{"No module content available."});
        }

        CourseDAO dao = new CourseDAO();
        dao.addCourse(course);

        request.setAttribute("success", "Course added successfully. Add another.");
        request.getRequestDispatcher("addCourse.jsp").forward(request, response);
    }
}