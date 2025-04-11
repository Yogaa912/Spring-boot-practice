<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head><title>登录成功</title></head>
<body>
<h2>欢迎你，<%= request.getAttribute("username") %>！</h2>
<p>这里可以是“我和李老师.avi”或“我和马小六.png”等内容页面。</p>
</body>
</html>