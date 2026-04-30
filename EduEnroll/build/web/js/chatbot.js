function toggleChatbot() {
    var chatbot = document.getElementById("chatbotBox");
    chatbot.classList.toggle("active");
}

function sendBotMessage() {
    var input = document.getElementById("chatInput");
    var messages = document.getElementById("chatMessages");

    var userText = input.value.trim();

    if (userText === "") {
        return;
    }

    messages.innerHTML += '<div class="user-msg">' + userText + '</div>';

    var reply = getCourseSuggestion(userText.toLowerCase());

    setTimeout(function () {
        messages.innerHTML += '<div class="bot-msg">' + reply + '</div>';
        messages.scrollTop = messages.scrollHeight;
    }, 500);

    input.value = "";
}

function getCourseSuggestion(text) {
    if (text.indexOf("java") !== -1 || text.indexOf("coding") !== -1 || text.indexOf("programming") !== -1) {
        return "Based on your interest, I recommend <b>Java Programming</b> and <b>Python Fundamentals</b>. These courses are best for building coding logic and programming basics.";
    }

    if (text.indexOf("website") !== -1 || text.indexOf("web") !== -1 || text.indexOf("frontend") !== -1 || text.indexOf("backend") !== -1 || text.indexOf("full stack") !== -1) {
        return "You should go for <b>Web Development</b> first, then <b>Full Stack Web Development</b>. These are best for frontend, backend, and complete website development.";
    }

    if (text.indexOf("data") !== -1 || text.indexOf("analytics") !== -1 || text.indexOf("python") !== -1 || text.indexOf("visualization") !== -1) {
        return "I recommend <b>Data Analytics with Python</b>. It will help you learn data cleaning, analysis, and visualization.";
    }

    if (text.indexOf("machine learning") !== -1 || text.indexOf("ml") !== -1 || text.indexOf("ai") !== -1 || text.indexOf("prediction") !== -1) {
        return "You should choose <b>Machine Learning Basics</b>. It is suitable for AI, prediction models, and data-driven projects.";
    }

    if (text.indexOf("security") !== -1 || text.indexOf("hacking") !== -1 || text.indexOf("cyber") !== -1) {
        return "I recommend <b>Cyber Security Fundamentals</b>. It is useful for learning attacks, protection, and ethical hacking basics.";
    }

    if (text.indexOf("cloud") !== -1 || text.indexOf("aws") !== -1 || text.indexOf("deployment") !== -1) {
        return "You should choose <b>Cloud Computing with AWS</b>. It is best for cloud services, deployment, EC2, S3, and IAM basics.";
    }

    if (text.indexOf("design") !== -1 || text.indexOf("ui") !== -1 || text.indexOf("ux") !== -1 || text.indexOf("figma") !== -1) {
        return "I recommend <b>UI UX Design Fundamentals</b>. It is best for design principles, wireframes, prototypes, and Figma.";
    }

    if (text.indexOf("devops") !== -1 || text.indexOf("docker") !== -1 || text.indexOf("github") !== -1 || text.indexOf("ci") !== -1 || text.indexOf("cd") !== -1) {
        return "You should choose <b>DevOps Fundamentals</b>. It covers Git, GitHub, Docker, CI/CD, and deployment basics.";
    }

    if (text.indexOf("database") !== -1 || text.indexOf("dbms") !== -1 || text.indexOf("mongodb") !== -1 || text.indexOf("sql") !== -1) {
        return "I recommend <b>Database Management</b>. It is best for DBMS concepts, MongoDB, CRUD operations, and database design.";
    }

    return "Tell me your interest like coding, website development, data, AI, cyber security, cloud, design, database, or DevOps. I will suggest the best course for you.";
}

function sendQuickQuestion(type) {
    var input = document.getElementById("chatInput");

    if (type === "coding") {
        input.value = "I want to learn coding";
    } else if (type === "web") {
        input.value = "I want to build websites";
    } else if (type === "data") {
        input.value = "I want to learn data analytics";
    } else if (type === "cyber") {
        input.value = "I am interested in cyber security";
    }

    sendBotMessage();
}