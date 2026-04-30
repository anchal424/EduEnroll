package com.eduenroll.dao;

import com.eduenroll.beans.EnrollmentBean;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class EnrollmentDAO {

    public boolean enrollCourse(EnrollmentBean enrollment) {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> enrollments = db.getCollection("enrollments");

        Document existing = enrollments.find(and(
                eq("studentId", enrollment.getStudentId()),
                eq("courseId", enrollment.getCourseId())
        )).first();

        if (existing != null) {
            return false;
        }

        Document doc = new Document("_id", enrollment.getId())
                .append("studentId", enrollment.getStudentId())
                .append("courseId", enrollment.getCourseId())
                .append("status", enrollment.getStatus())
                .append("progress", enrollment.getProgress())
                .append("currentModule", enrollment.getCurrentModule())
                .append("totalModules", enrollment.getTotalModules());

        enrollments.insertOne(doc);
        return true;
    }

    public EnrollmentBean getEnrollmentByStudentAndCourse(String studentId, String courseId) {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> enrollments = db.getCollection("enrollments");

        Document doc = enrollments.find(and(eq("studentId", studentId), eq("courseId", courseId))).first();
        if (doc != null) {
            EnrollmentBean e = new EnrollmentBean();
            e.setId(doc.getString("_id"));
            e.setStudentId(doc.getString("studentId"));
            e.setCourseId(doc.getString("courseId"));
            e.setStatus(doc.getString("status"));

            Integer progress = doc.getInteger("progress");
            Integer currentModule = doc.getInteger("currentModule");
            Integer totalModules = doc.getInteger("totalModules");

            e.setProgress(progress != null ? progress : 0);
            e.setCurrentModule(currentModule != null ? currentModule : 1);
            e.setTotalModules(totalModules != null ? totalModules : 1);

            return e;
        }
        return null;
    }

    public List<EnrollmentBean> getEnrollmentsByStudent(String studentId) {
        List<EnrollmentBean> list = new ArrayList<EnrollmentBean>();

        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> enrollments = db.getCollection("enrollments");
        FindIterable<Document> docs = enrollments.find(eq("studentId", studentId));

        for (Document doc : docs) {
            EnrollmentBean e = new EnrollmentBean();
            e.setId(doc.getString("_id"));
            e.setStudentId(doc.getString("studentId"));
            e.setCourseId(doc.getString("courseId"));
            e.setStatus(doc.getString("status"));

            Integer progress = doc.getInteger("progress");
            Integer currentModule = doc.getInteger("currentModule");
            Integer totalModules = doc.getInteger("totalModules");

            e.setProgress(progress != null ? progress : 0);
            e.setCurrentModule(currentModule != null ? currentModule : 1);
            e.setTotalModules(totalModules != null ? totalModules : 1);

            list.add(e);
        }
        return list;
    }

    public List<EnrollmentBean> getAllEnrollments() {
        List<EnrollmentBean> list = new ArrayList<EnrollmentBean>();

        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> enrollments = db.getCollection("enrollments");
        FindIterable<Document> docs = enrollments.find();

        for (Document doc : docs) {
            EnrollmentBean e = new EnrollmentBean();
            e.setId(doc.getString("_id"));
            e.setStudentId(doc.getString("studentId"));
            e.setCourseId(doc.getString("courseId"));
            e.setStatus(doc.getString("status"));

            Integer progress = doc.getInteger("progress");
            Integer currentModule = doc.getInteger("currentModule");
            Integer totalModules = doc.getInteger("totalModules");

            e.setProgress(progress != null ? progress : 0);
            e.setCurrentModule(currentModule != null ? currentModule : 1);
            e.setTotalModules(totalModules != null ? totalModules : 1);

            list.add(e);
        }
        return list;
    }

    public void moveToNextModule(String enrollmentId) {
    MongoDatabase db = MongoDBConnection.getDatabase();
    MongoCollection<Document> enrollments = db.getCollection("enrollments");

    Document doc = enrollments.find(eq("_id", enrollmentId)).first();
    if (doc == null) return;

    Integer currentModuleObj = doc.getInteger("currentModule");
    Integer totalModulesObj = doc.getInteger("totalModules");

    int currentModule = currentModuleObj != null ? currentModuleObj : 1;
    int totalModules = totalModulesObj != null ? totalModulesObj : 1;

    if (currentModule < totalModules) {
        currentModule = currentModule + 1;
    }

    int progress = (currentModule * 100) / totalModules;
    String status = currentModule >= totalModules ? "Completed" : "Enrolled";

    enrollments.updateOne(eq("_id", enrollmentId),
            Updates.combine(
                    Updates.set("currentModule", currentModule),
                    Updates.set("progress", progress),
                    Updates.set("status", status)
            ));
}
}