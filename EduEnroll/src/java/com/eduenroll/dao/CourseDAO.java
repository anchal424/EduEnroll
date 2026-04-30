package com.eduenroll.dao;

import com.eduenroll.beans.CourseBean;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

public class CourseDAO {

    public boolean addCourse(CourseBean course) {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> courses = db.getCollection("courses");

        Document doc = new Document("_id", course.getId())
                .append("title", course.getTitle())
                .append("category", course.getCategory())
                .append("description", course.getDescription())
                .append("duration", course.getDuration())
                .append("price", course.getPrice())
                .append("instructor", course.getInstructor())
                .append("image", course.getImage())
                .append("modules", String.join("||", course.getModules()));

        courses.insertOne(doc);
        return true;
    }

    public List<CourseBean> getAllCourses() {
        List<CourseBean> list = new ArrayList<CourseBean>();
        Set<String> hiddenIds = getHiddenCourseIds();
        Map<String, CourseBean> editedMap = getEditedCoursesMap();

        addIfNotHidden(list, hiddenIds, editedMap, makeCourse(
                "COURSE1001",
                "Java Programming",
                "Programming",
                "Learn Core Java with OOP concepts.",
                "8 Weeks",
                2500.0,
                "Rahul Sharma",
                "Java1.jpg",
                new String[]{
                    "Module 1: Java Basics\n- What is Java\n- Features of Java\n- JVM, JDK, JRE\n- First Java Program",
                    "Module 2: OOP Concepts\n- Class and Object\n- Inheritance\n- Polymorphism\n- Encapsulation",
                    "Module 3: Arrays and Strings\n- Array basics\n- String methods\n- StringBuffer and StringBuilder",
                    "Module 4: Exception Handling\n- try catch\n- finally\n- throw and throws\n- custom exception",
                    "Module 5: Mini Project\n- Create a simple student management console app"
                }
        ));

        addIfNotHidden(list, hiddenIds, editedMap, makeCourse(
                "COURSE1002",
                "Web Development",
                "Development",
                "HTML, CSS, JavaScript basics and small projects.",
                "6 Weeks",
                2200.0,
                "Ankit Verma",
                "web.jpg",
                new String[]{
                    "Module 1: HTML Basics\n- Structure\n- Forms\n- Tables",
                    "Module 2: CSS Basics\n- Selectors\n- Colors\n- Box Model",
                    "Module 3: Layout\n- Flexbox\n- Grid\n- Responsive design",
                    "Module 4: JavaScript Basics\n- Variables\n- Functions\n- Events",
                    "Module 5: DOM Manipulation\n- Selecting elements\n- Changing content\n- Form validation"
                }
        ));

        addIfNotHidden(list, hiddenIds, editedMap, makeCourse(
                "COURSE1003",
                "Database Management",
                "Database",
                "Learn DBMS concepts and MongoDB basics.",
                "5 Weeks",
                1800.0,
                "Priya Mehta",
                "database.jpg",
                new String[]{
                    "Module 1: DBMS Basics\n- Database concepts\n- Data models",
                    "Module 2: Keys and ER Model\n- Primary key\n- Foreign key\n- ER diagrams",
                    "Module 3: SQL Intro\n- SELECT\n- INSERT\n- UPDATE\n- DELETE",
                    "Module 4: NoSQL Intro\n- Differences from SQL\n- Use cases",
                    "Module 5: MongoDB Basics\n- Collections\n- Documents\n- CRUD"
                }
        ));

        addIfNotHidden(list, hiddenIds, editedMap, makeCourse(
                "COURSE1004",
                "Python Fundamentals",
                "Programming",
                "Python basics with practical examples.",
                "7 Weeks",
                2300.0,
                "Sneha Kapoor",
                "python.jpg",
                new String[]{
                    "Module 1: Python Intro\n- Variables\n- Data types",
                    "Module 2: Conditions and Loops\n- if else\n- for\n- while",
                    "Module 3: Functions\n- parameters\n- return values",
                    "Module 4: Collections\n- list\n- tuple\n- dictionary",
                    "Module 5: Mini Practice\n- small calculator program"
                }
        ));

        addIfNotHidden(list, hiddenIds, editedMap, makeCourse(
                "COURSE1005",
                "Software Engineering",
                "Software",
                "SDLC, models, testing, and software planning.",
                "6 Weeks",
                2100.0,
                "Vikas Jain",
                "software.jpg",
                new String[]{
                    "Module 1: SDLC\n- phases of software development",
                    "Module 2: Process Models\n- Waterfall\n- Agile\n- Spiral",
                    "Module 3: Requirements\n- SRS\n- functional vs non-functional",
                    "Module 4: Testing\n- unit testing\n- integration testing",
                    "Module 5: Case Study\n- build software lifecycle example"
                }
        ));

        addIfNotHidden(list, hiddenIds, editedMap, makeCourse(
                "COURSE1006",
                "Data Structures",
                "Programming",
                "Learn stack, queue, linked list, tree basics.",
                "8 Weeks",
                2600.0,
                "Neha Singh",
                "datastructures.jpg",
                new String[]{
                    "Module 1: Arrays\n- basics and operations",
                    "Module 2: Stack\n- push pop applications",
                    "Module 3: Queue\n- enqueue dequeue applications",
                    "Module 4: Linked List\n- singly and doubly linked list",
                    "Module 5: Trees\n- binary tree basics"
                }
        ));

        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> courses = db.getCollection("courses");
        FindIterable<Document> docs = courses.find();

        for (Document doc : docs) {
            CourseBean c = documentToCourse(doc);
            list.add(c);
        }

        return list;
    }

    public CourseBean getCourseById(String id) {
        List<CourseBean> list = getAllCourses();
        for (CourseBean c : list) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public void deleteCourse(String courseId) {
        if (isHardcodedCourse(courseId)) {
            hideHardcodedCourse(courseId);
        } else {
            MongoDatabase db = MongoDBConnection.getDatabase();
            MongoCollection<Document> courses = db.getCollection("courses");
            courses.deleteOne(eq("_id", courseId));
        }
    }

    public void updateCourse(CourseBean course) {
        if (isHardcodedCourse(course.getId())) {
            saveEditedHardcodedCourse(course);
        } else {
            updateDatabaseCourse(course);
        }
    }

    private void updateDatabaseCourse(CourseBean course) {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> courses = db.getCollection("courses");

        Document updatedDoc = new Document("_id", course.getId())
                .append("image", course.getImage())
                .append("title", course.getTitle())
                .append("category", course.getCategory())
                .append("description", course.getDescription())
                .append("duration", course.getDuration())
                .append("price", course.getPrice())
                .append("instructor", course.getInstructor())
                .append("modules", String.join("||", course.getModules()));

        courses.replaceOne(eq("_id", course.getId()), updatedDoc);
    }

    private void saveEditedHardcodedCourse(CourseBean course) {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> editedCourses = db.getCollection("edited_courses");

        Document updatedDoc = new Document("_id", course.getId())
                .append("title", course.getTitle())
                .append("category", course.getCategory())
                .append("description", course.getDescription())
                .append("duration", course.getDuration())
                .append("price", course.getPrice())
                .append("instructor", course.getInstructor())
                .append("modules", String.join("||", course.getModules()));

        Document existing = editedCourses.find(eq("_id", course.getId())).first();
        if (existing == null) {
            editedCourses.insertOne(updatedDoc);
        } else {
            editedCourses.replaceOne(eq("_id", course.getId()), updatedDoc);
        }
    }

    private boolean isHardcodedCourse(String courseId) {
        return courseId.equals("COURSE1001") ||
               courseId.equals("COURSE1002") ||
               courseId.equals("COURSE1003") ||
               courseId.equals("COURSE1004") ||
               courseId.equals("COURSE1005") ||
               courseId.equals("COURSE1006");
    }

    private void hideHardcodedCourse(String courseId) {
        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> hiddenCourses = db.getCollection("hidden_courses");

        Document existing = hiddenCourses.find(eq("_id", courseId)).first();
        if (existing == null) {
            Document doc = new Document("_id", courseId).append("hidden", true);
            hiddenCourses.insertOne(doc);
        }
    }

    private Set<String> getHiddenCourseIds() {
        Set<String> hiddenIds = new HashSet<String>();

        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> hiddenCourses = db.getCollection("hidden_courses");
        FindIterable<Document> docs = hiddenCourses.find();

        for (Document doc : docs) {
            hiddenIds.add(doc.getString("_id"));
        }

        return hiddenIds;
    }

    private Map<String, CourseBean> getEditedCoursesMap() {
        Map<String, CourseBean> map = new HashMap<String, CourseBean>();

        MongoDatabase db = MongoDBConnection.getDatabase();
        MongoCollection<Document> editedCourses = db.getCollection("edited_courses");
        FindIterable<Document> docs = editedCourses.find();

        for (Document doc : docs) {
            CourseBean c = documentToCourse(doc);
            map.put(c.getId(), c);
        }

        return map;
    }

    private void addIfNotHidden(List<CourseBean> list, Set<String> hiddenIds, Map<String, CourseBean> editedMap, CourseBean originalCourse) {
        if (!hiddenIds.contains(originalCourse.getId())) {
            if (editedMap.containsKey(originalCourse.getId())) {
                list.add(editedMap.get(originalCourse.getId()));
            } else {
                list.add(originalCourse);
            }
        }
    }

    private CourseBean documentToCourse(Document doc) {
        CourseBean c = new CourseBean();
        c.setId(doc.getString("_id"));
        c.setTitle(doc.getString("title"));
        c.setCategory(doc.getString("category"));
        c.setDescription(doc.getString("description"));
        c.setDuration(doc.getString("duration"));

        Object priceObj = doc.get("price");
        if (priceObj instanceof Integer) {
            c.setPrice(((Integer) priceObj).doubleValue());
        } else if (priceObj instanceof Double) {
            c.setPrice((Double) priceObj);
        } else if (priceObj instanceof Long) {
            c.setPrice(((Long) priceObj).doubleValue());
        }

        c.setInstructor(doc.getString("instructor"));
        
                c.setImage(doc.getString("image"));
if (c.getImage() == null || c.getImage().trim().isEmpty()) {
    c.setImage("default.jpg");
}

        String modulesStr = doc.getString("modules");
        if (modulesStr != null) {
            c.setModules(modulesStr.split("\\|\\|"));
        } else {
            c.setModules(new String[]{"No module content available."});
        }

        return c;
    }

    private CourseBean makeCourse(String id, String title, String category,
        String description, String duration, double price,
        String instructor, String image, String[] modules) {

    CourseBean c = new CourseBean();
    c.setId(id);
    c.setTitle(title);
    c.setCategory(category);
    c.setDescription(description);
    c.setDuration(duration);
    c.setPrice(price);
    c.setInstructor(instructor);
    c.setImage(image);   // 👈 IMPORTANT
    c.setModules(modules);

    return c;
}
}