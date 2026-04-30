package com.eduenroll.controller;

import com.eduenroll.beans.CertificateBean;
import com.eduenroll.beans.CourseBean;
import com.eduenroll.beans.UserBean;
import com.eduenroll.dao.CertificateDAO;
import com.eduenroll.dao.CourseDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@WebServlet("/DownloadCertificateServlet")
public class DownloadCertificateServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UserBean user = (UserBean) session.getAttribute("user");
        String courseId = request.getParameter("courseId");

        CertificateDAO certDAO = new CertificateDAO();
        CourseDAO courseDAO = new CourseDAO();

        CertificateBean cert = certDAO.getCertificateByStudentAndCourse(user.getId(), courseId);
        CourseBean course = courseDAO.getCourseById(courseId);

        if (cert == null || course == null) {
            response.sendRedirect("CertificateServlet");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=certificate_" + courseId + ".pdf");

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, BaseColor.BLUE);
            Font headingFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);

            Paragraph title = new Paragraph("Certificate of Completion", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(30f);
            document.add(title);

            Paragraph p1 = new Paragraph("This is to certify that", normalFont);
            p1.setAlignment(Element.ALIGN_CENTER);
            p1.setSpacingAfter(15f);
            document.add(p1);

            Paragraph studentName = new Paragraph(user.getName(), headingFont);
            studentName.setAlignment(Element.ALIGN_CENTER);
            studentName.setSpacingAfter(15f);
            document.add(studentName);

            Paragraph p2 = new Paragraph("has successfully completed the course", normalFont);
            p2.setAlignment(Element.ALIGN_CENTER);
            p2.setSpacingAfter(15f);
            document.add(p2);

            Paragraph courseName = new Paragraph(course.getTitle(), boldFont);
            courseName.setAlignment(Element.ALIGN_CENTER);
            courseName.setSpacingAfter(30f);
            document.add(courseName);

            Paragraph certNo = new Paragraph("Certificate No: " + cert.getCertificateNo(), normalFont);
            certNo.setAlignment(Element.ALIGN_CENTER);
            certNo.setSpacingAfter(10f);
            document.add(certNo);

            String today = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            Paragraph date = new Paragraph("Date: " + today, normalFont);
            date.setAlignment(Element.ALIGN_CENTER);
            document.add(date);

            document.close();

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}