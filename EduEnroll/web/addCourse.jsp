<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Course</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/script.js"></script>
</head>
<body>

<div class="navbar">
    <div class="nav-left">
        <span class="menu-icon" onclick="openSidebar()">☰</span>
        <h2>EduEnroll</h2>
    </div>

    <div class="nav-right">
        <span class="welcome">Welcome, Admin</span>
    </div>
</div>

<div id="sidebarOverlay" class="sidebar-overlay" onclick="closeSidebar()"></div>

<div id="sidebarMenu" class="sidebar-menu">
    <div class="sidebar-header">
        <span class="close-btn" onclick="closeSidebar()">✕</span>
    </div>

    <div class="sidebar-user">
        Hello, <strong>Admin</strong> 👋
    </div>

    <a href="adminDashboard.jsp" class="sidebar-link">🏠 Dashboard</a>
    <a href="addCourse.jsp" class="sidebar-link">➕ Add Course</a>
    <a href="ViewCoursesServlet" class="sidebar-link">📚 Manage Courses</a>
    <a href="ViewEnrollmentsServlet" class="sidebar-link">📝 Enrollments</a>
    <a href="AdminAnalyticsServlet" class="sidebar-link">📊 Analytics</a>
    <a href="index.jsp" class="sidebar-link logout-link">🚪 Logout</a>
</div>

<div class="back-container">
    <a href="javascript:history.back()" class="back-btn">← Back</a>
</div>

<div class="form-container">
    <h2>Add Course</h2>

    <%
        String success = (String) request.getAttribute("success");
        if (success != null) {
    %>
        <p id="successMsg" class="success-msg"><%= success %></p>
    <%
        }
    %>

    <form id="addCourseForm" action="AddCourseServlet" method="post">
        <input type="text" name="title" placeholder="Course Title" required>
        <input type="text" name="category" placeholder="Category" required>
        <input type="text" name="description" placeholder="Description" required>
        <input type="text" name="duration" placeholder="Duration" required>
        <input type="number" step="0.01" name="price" placeholder="Price" required>
        <input type="text" name="instructor" placeholder="Instructor Name" required>
        <input type="text" name="image" placeholder="Course Image Name e.g. java.jpg" required>

        <label><strong>Course Modules</strong></label>
        <p style="margin:8px 0 12px; font-size:14px; color:#555;">
            Write each module separated by <strong>||</strong>
        </p>

        <textarea name="material" rows="8" placeholder="Example: Module 1 content||Module 2 content||Module 3 content" required></textarea>

        <button type="submit" class="btn">Add Course</button>
    </form>
</div>

<script>
    (function () {
        const successMsg = document.getElementById("successMsg");
        const form = document.getElementById("addCourseForm");

        if (successMsg && form) {
            const fields = form.querySelectorAll("input, textarea, select");

            fields.forEach(function(field) {
                field.addEventListener("input", function() {
                    successMsg.style.display = "none";
                });

                field.addEventListener("change", function() {
                    successMsg.style.display = "none";
                });

                field.addEventListener("focus", function() {
                    successMsg.style.display = "none";
                });
            });
        }
    })();
</script>

</body>
</html>