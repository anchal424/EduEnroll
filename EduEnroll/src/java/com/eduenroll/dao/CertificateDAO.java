package com.eduenroll.dao;

import com.eduenroll.beans.CertificateBean;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class CertificateDAO {

    public boolean addCertificate(CertificateBean cert) {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> certificates = db.getCollection("certificates");

        Document existing = certificates.find(and(
                eq("studentId", cert.getStudentId()),
                eq("courseId", cert.getCourseId())
        )).first();

        if (existing != null) {
            return false;
        }
        
        Document doc = new Document("_id", cert.getId())
                .append("studentId", cert.getStudentId())
                .append("courseId", cert.getCourseId())
                .append("certificateNo", cert.getCertificateNo());

        certificates.insertOne(doc);
        return true;
    }

    public List<CertificateBean> getCertificatesByStudent(String studentId) {
        List<CertificateBean> list = new ArrayList<CertificateBean>();
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> certificates = db.getCollection("certificates");
        FindIterable<Document> docs = certificates.find(eq("studentId", studentId));

        for (Document doc : docs) {
            CertificateBean c = new CertificateBean();
            c.setId(doc.getString("_id"));
            c.setStudentId(doc.getString("studentId"));
            c.setCourseId(doc.getString("courseId"));
            c.setCertificateNo(doc.getString("certificateNo"));
            list.add(c);
        }
        return list;
    }
    
    public CertificateBean getCertificateByStudentAndCourse(String studentId, String courseId) {
    MongoDatabase db = MongoDBConnection.getDatabase();
    MongoCollection<Document> certificates = db.getCollection("certificates");

    Document doc = certificates.find(and(
            eq("studentId", studentId),
            eq("courseId", courseId)
    )).first();

    if (doc != null) {
        CertificateBean c = new CertificateBean();
        c.setId(doc.getString("_id"));
        c.setStudentId(doc.getString("studentId"));
        c.setCourseId(doc.getString("courseId"));
        c.setCertificateNo(doc.getString("certificateNo"));
        return c;
    }
    return null;
}
}    