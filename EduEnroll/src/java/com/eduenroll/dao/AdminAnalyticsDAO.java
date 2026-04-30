package com.eduenroll.dao;

import com.eduenroll.beans.AdminAnalyticsBean;
import com.eduenroll.beans.CourseBean;
import com.eduenroll.beans.EnrollmentBean;
import com.eduenroll.beans.PaymentBean;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.Document;

public class AdminAnalyticsDAO {

    public List<AdminAnalyticsBean> getEnrollmentsByCourse() {
        List<AdminAnalyticsBean> result = new ArrayList<AdminAnalyticsBean>();

        CourseDAO courseDAO = new CourseDAO();
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

        List<CourseBean> courses = courseDAO.getAllCourses();
        List<EnrollmentBean> enrollments = enrollmentDAO.getAllEnrollments();

        for (CourseBean course : courses) {
            int count = 0;

            for (EnrollmentBean e : enrollments) {
                if (course.getId().equals(e.getCourseId())) {
                    count++;
                }
            }

            AdminAnalyticsBean bean = new AdminAnalyticsBean();
            bean.setLabel(course.getTitle());
            bean.setCount(count);
            result.add(bean);
        }

        return result;
    }

    public AdminAnalyticsBean getCompletionStatus() {
        AdminAnalyticsBean bean = new AdminAnalyticsBean();

        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        List<EnrollmentBean> enrollments = enrollmentDAO.getAllEnrollments();

        int completed = 0;
        int ongoing = 0;

        for (EnrollmentBean e : enrollments) {
            if ("Completed".equalsIgnoreCase(e.getStatus())) {
                completed++;
            } else {
                ongoing++;
            }
        }

        bean.setLabel("status");
        bean.setCount(completed);
        bean.setAmount(ongoing); // using amount to store ongoing count
        return bean;
    }

    public List<AdminAnalyticsBean> getRevenueByCourse() {
        List<AdminAnalyticsBean> result = new ArrayList<AdminAnalyticsBean>();

        CourseDAO courseDAO = new CourseDAO();
        List<CourseBean> courses = courseDAO.getAllCourses();

        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> payments = db.getCollection("payments");
        FindIterable<Document> paymentDocs = payments.find();

        Map<String, Double> revenueMap = new HashMap<String, Double>();

        for (Document doc : paymentDocs) {
            String courseId = doc.getString("courseId");

            Object amountObj = doc.get("amount");
            double amount = 0.0;

            if (amountObj instanceof Integer) {
                amount = ((Integer) amountObj).doubleValue();
            } else if (amountObj instanceof Double) {
                amount = (Double) amountObj;
            } else if (amountObj instanceof Long) {
                amount = ((Long) amountObj).doubleValue();
            }

            if (revenueMap.containsKey(courseId)) {
                revenueMap.put(courseId, revenueMap.get(courseId) + amount);
            } else {
                revenueMap.put(courseId, amount);
            }
        }

        for (CourseBean course : courses) {
            AdminAnalyticsBean bean = new AdminAnalyticsBean();
            bean.setLabel(course.getTitle());
            bean.setAmount(revenueMap.containsKey(course.getId()) ? revenueMap.get(course.getId()) : 0.0);
            result.add(bean);
        }

        return result;
    }
}