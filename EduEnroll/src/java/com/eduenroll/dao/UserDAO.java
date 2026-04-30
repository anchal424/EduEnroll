package com.eduenroll.dao;

import com.eduenroll.beans.UserBean;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.eq;

public class UserDAO {

    public boolean registerUser(UserBean user) {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> users = db.getCollection("users");

        Document existing = users.find(eq("email", user.getEmail())).first();
        if (existing != null) {
            return false;
        }

        Document doc = new Document("_id", user.getId())
                .append("name", user.getName())
                .append("email", user.getEmail())
                .append("password", user.getPassword())
                .append("role", user.getRole());

        users.insertOne(doc);
        return true;
    }
    
    public UserBean loginUser(String email, String password) {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> users = db.getCollection("users");
        Document doc = users.find(eq("email", email)).first();

        if (doc != null && doc.getString("password").equals(password)) {
            UserBean user = new UserBean();
            user.setId(doc.getString("_id"));
            user.setName(doc.getString("name"));
            user.setEmail(doc.getString("email"));
            user.setPassword(doc.getString("password"));
            user.setRole(doc.getString("role"));
            return user;
        }
        return null;
    }
}