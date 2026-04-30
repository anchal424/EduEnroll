package com.eduenroll.dao;

import com.eduenroll.beans.CourseBean;
import com.eduenroll.beans.EnrollmentBean;
import com.eduenroll.beans.UserAnalyticsBean;
import java.util.ArrayList;
import java.util.List;

public class UserAnalyticsDAO {

    public List<UserAnalyticsBean> getMyProgressData(String studentId) {
        List<UserAnalyticsBean> result = new ArrayList<UserAnalyticsBean>();

        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        CourseDAO courseDAO = new CourseDAO();

        List<EnrollmentBean> enrollments = enrollmentDAO.getEnrollmentsByStudent(studentId);

        for (EnrollmentBean e : enrollments) {
            CourseBean c = courseDAO.getCourseById(e.getCourseId());
            if (c != null) {
                UserAnalyticsBean bean = new UserAnalyticsBean();
                bean.setLabel(c.getTitle());
                bean.setCount(e.getProgress());
                result.add(bean);
            }
        }

        return result;
    }

    public UserAnalyticsBean getMyCompletionSummary(String studentId) {
        UserAnalyticsBean bean = new UserAnalyticsBean();

        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        List<EnrollmentBean> enrollments = enrollmentDAO.getEnrollmentsByStudent(studentId);

        int completed = 0;
        int ongoing = 0;

        for (EnrollmentBean e : enrollments) {
            if ("Completed".equalsIgnoreCase(e.getStatus())) {
                completed++;
            } else {
                ongoing++;
            }
        }

        bean.setLabel(completed + "," + ongoing);
        bean.setCount(completed); // count stores completed
        return bean;
    }

    public int getCertificateCount(String studentId) {
        CertificateDAO certificateDAO = new CertificateDAO();
        return certificateDAO.getCertificatesByStudent(studentId).size();
    }

    public int getEnrolledCourseCount(String studentId) {
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        return enrollmentDAO.getEnrollmentsByStudent(studentId).size();
    }
}