<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.eduenroll.beans.PaymentBean" %>
<%@ page import="com.eduenroll.beans.UserBean" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Receipt</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<%
    PaymentBean payment = (PaymentBean) request.getAttribute("payment");
    UserBean user = (UserBean) session.getAttribute("user");
%>

<div class="receipt-page">

    <div class="receipt-card">

        <h2>Payment Receipt</h2>

        <div class="receipt-line"></div>

        <div class="receipt-details">

            <p><span>Student Name:</span> <%= user != null ? user.getName() : "N/A" %></p>
            <p><span>Course ID:</span> <%= payment.getCourseId() %></p>
            <p><span>Receipt ID:</span> <%= payment.getReceiptId() %></p>
            <p><span>Method:</span> <%= payment.getMethod() %></p>

        </div>

        <div class="receipt-line"></div>

        <div class="receipt-amount">
            ₹<%= payment.getAmount() %>
        </div>

        <p class="receipt-status">Payment Successful ✅</p>

        <div class="receipt-actions">
    <a href="CourseDetailsServlet?courseId=<%= payment.getCourseId() %>" class="btn">Go to Course Overview</a>
</div>
    </div>

</div>

</body>
</html>