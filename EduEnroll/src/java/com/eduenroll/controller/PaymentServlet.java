package com.eduenroll.controller;

import com.eduenroll.beans.CourseBean;
import com.eduenroll.beans.EnrollmentBean;
import com.eduenroll.beans.PaymentBean;
import com.eduenroll.beans.UserBean;
import com.eduenroll.dao.CourseDAO;
import com.eduenroll.dao.EnrollmentDAO;
import com.eduenroll.dao.PaymentDAO;
import com.eduenroll.util.IdGenerator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            UserBean user = (UserBean) session.getAttribute("user");
            String courseId = request.getParameter("courseId");
            String amountStr = request.getParameter("amount");
            String method = request.getParameter("method");

            if (courseId == null || amountStr == null || method == null ||
                courseId.trim().isEmpty() || amountStr.trim().isEmpty() || method.trim().isEmpty()) {
                response.sendRedirect("DashboardServlet");
                return;
            }

            double amount = Double.parseDouble(amountStr);

            CourseDAO courseDAO = new CourseDAO();
            CourseBean course = courseDAO.getCourseById(courseId);

            if (course == null) {
                response.sendRedirect("DashboardServlet");
                return;
            }

            PaymentBean payment = new PaymentBean();
            payment.setId(IdGenerator.generate("PAY"));
            payment.setStudentId(user.getId());
            payment.setCourseId(courseId);
            payment.setAmount(amount);
            payment.setMethod(method);
            payment.setStatus("Paid");
            payment.setReceiptId(IdGenerator.generate("REC"));

            PaymentDAO paymentDAO = new PaymentDAO();
            paymentDAO.savePayment(payment);

            EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

            EnrollmentBean existingEnrollment = enrollmentDAO.getEnrollmentByStudentAndCourse(user.getId(), courseId);

            if (existingEnrollment == null) {
                EnrollmentBean enrollment = new EnrollmentBean();
                enrollment.setId(IdGenerator.generate("ENR"));
                enrollment.setStudentId(user.getId());
                enrollment.setCourseId(courseId);
                enrollment.setStatus("Enrolled");
                enrollment.setProgress(0);
                enrollment.setCurrentModule(1);

                if (course.getModules() != null && course.getModules().length > 0) {
                    enrollment.setTotalModules(course.getModules().length);
                } else {
                    enrollment.setTotalModules(1);
                }

                enrollmentDAO.enrollCourse(enrollment);
            }

            request.setAttribute("payment", payment);
            request.getRequestDispatcher("receipt.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("DashboardServlet");
        }
    }
}