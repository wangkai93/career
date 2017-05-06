<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.*" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="css/default.css"/>
</head>
<body background="img/welcome-bg2.png">

<div class="table-slipline"></div>
    <div class="welcome">
        <div class="welcom-text" >欢迎登陆就业管理中心！ </div>
    </div>
    <div>
        <table class="pure-table pure-table-bordered left">
            <tr>
                <td>
                    <button class="mybutton" type="button" onclick="location='${pageContext.request.contextPath}/unemp/findAllCount'">就业生、未就业生分布</button>
                </td>
                <td>
                    <button class="mybutton" type="button" onclick="location='${pageContext.request.contextPath}/unemp/findSumNotEmp'">未就业生情况分布</button>
                </td>
                <td>
                    <button class="mybutton" type="button" onclick="location='${pageContext.request.contextPath}/emp/findEmpCountByType'">就业生情况分布</button>
                </td>
            </tr>
        </table>
        <table  class="pure-table pure-table-bordered CompInfo1">
            <tr>
                <td rowspan="2">就业生月增量</td>
                <td>日期</td>
                <c:forEach items="${empIncrease}" var="emp">
                    <td> ${emp.thismonth}
                            <%--<fmt:formatDate value="${emp.thismonth}" pattern="yyyy-MM-dd"/> --%>
                    </td>
                </c:forEach>
            </tr>
            <tr>
                <td>增量</td>
                <c:forEach items="${empIncrease}" var="emp">
                    <td>${emp.data}</td>
                </c:forEach>
            </tr>
        </table>
        <table class="pure-table pure-table-bordered left">
            <tr>
                <td>近一个月准备就业的人数</td>
                <td>近一个月就业学生数量</td>
                <td>当前已就业学生数量</td>
                <td>当前未就业学生数量</td>
            </tr>
            <tr>
                <td>
                    <button class="mybutton" type="button" onclick="location='${pageContext.request.contextPath}/unemp/findAllUnempMonth'">${unempmonth}</button>
                </td>
                <td>
                    <button class="mybutton" type="button" onclick="location='${pageContext.request.contextPath}/unemp/findAllUnemp?page=1'">${EmpCountByMonth}</button>
                </td>
                <td>
                    <button class="mybutton" type="button" onclick="location='${pageContext.request.contextPath}/unemp/findAllUnemp?page=1'">${empCount}</button>
                </td>
                <td>
                    <button class="mybutton" type="button" onclick="location='${pageContext.request.contextPath}/unemp/findAllUnemp?page=1'">${unempCount}</button>
                </td>
            </tr>
        </table>
        <table class="pure-table pure-table-bordered left">
            <tr>
                <td>今天参加面试的学生</td>
                <td>近一周企业发布的招聘信息</td>
                <td>近一周发布招聘信息的企业</td>
            </tr>
            <tr>
                <td>
                    <button class="mybutton" type="button" onclick="location='${pageContext.request.contextPath}/inter/findByDay'">${dayInter}</button>
                </td>
                <td>
                    <button class="mybutton" type="button" onclick="location='${pageContext.request.contextPath}/recruit/findByWeek'">${weekRecruit}</button>
                </td>
                <td>
                    <button class="mybutton" type="button" onclick="location='${pageContext.request.contextPath}/company/findComByWeek'">${weekRecruitCom}</button>
                </td>
            </tr>
        </table>
        <div class="table-slipline"></div>
    </div>
</body>
</html>
