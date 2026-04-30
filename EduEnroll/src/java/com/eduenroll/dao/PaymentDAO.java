package com.eduenroll.dao;

import com.eduenroll.beans.PaymentBean;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

public class PaymentDAO {

    public boolean savePayment(PaymentBean payment) {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> payments = db.getCollection("payments");

        Document doc = new Document("_id", payment.getId())
                .append("studentId", payment.getStudentId())
                .append("courseId", payment.getCourseId())
                .append("amount", payment.getAmount())
                .append("method", payment.getMethod())
                .append("status", payment.getStatus())
                .append("receiptId", payment.getReceiptId());

        payments.insertOne(doc);
        return true;
    }

    public PaymentBean getPaymentById(String id) {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> payments = db.getCollection("payments");
        Document doc = payments.find(eq("_id", id)).first();

        if (doc != null) {
            PaymentBean p = new PaymentBean();
            p.setId(doc.getString("_id"));
            p.setStudentId(doc.getString("studentId"));
            p.setCourseId(doc.getString("courseId"));
            Object amountObj = doc.get("amount");
            if (amountObj instanceof Integer) {
                p.setAmount(((Integer) amountObj).doubleValue());
            } else if (amountObj instanceof Double) {
                p.setAmount((Double) amountObj);
            } else if (amountObj instanceof Long) {
                p.setAmount(((Long) amountObj).doubleValue());
            }
            p.setMethod(doc.getString("method"));
            p.setStatus(doc.getString("status"));
            p.setReceiptId(doc.getString("receiptId"));
            return p;
        }
        return null;
    }
}