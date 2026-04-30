<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.eduenroll.beans.CourseBean" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<%
    CourseBean course = (CourseBean) request.getAttribute("course");
    if (course == null) {
        response.sendRedirect("DashboardServlet");
        return;
    }
%>

<div class="auth-page">
    <div class="auth-card">

        <h2>Payment Simulation</h2>

        <p><strong>Course:</strong> <%= course.getTitle() %></p>
        <p><strong>Amount:</strong> ₹<%= course.getPrice() %></p>

        <form action="PaymentServlet" method="post">
            <input type="hidden" name="courseId" value="<%= course.getId() %>">
            <input type="hidden" name="amount" value="<%= course.getPrice() %>">

            <select name="method">
                <option value="Card">Card</option>
                <option value="UPI">UPI</option>
                <option value="NetBanking">Net Banking</option>
            </select>

            <button type="submit" class="btn">Pay Now</button>
        </form>

    </div>
</div>

</body>
</html>