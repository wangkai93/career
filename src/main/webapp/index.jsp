<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.*" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/default.css"/>
    <title>首页</title>
</head>

<frameset  frameborder="no" border="2" framespacing="0" cols=" 15%,85%">
    <frame src="${pageContext.request.contextPath }/left.jsp" scrolling="no" />
    <frameset rows="69px,*" >
        <frame src="${pageContext.request.contextPath }/top.jsp" scrolling="no">
        <frameset rows="90%,10%">
            <frame src="${pageContext.request.contextPath }/main.jsp" name="main" />
            <frame src="${pageContext.request.contextPath }/footer.jsp" />
        </frameset>
    </frameset>
</frameset>

</html>
