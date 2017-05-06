<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2016/11/3
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.*" isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <script src="${pageContext.request.contextPath}/js/showele.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/showele.js" ></script>

</head>
<body onload="Activeli()">
<div class="table-box">
    <div class="table-content">
        <div class="table-head">
            <div class="table-address">
                <div style="float: left;">
                    <span>信息管理</span><div class="left-arrow"></div>
                    <span>未就业生信息</span><div class="left-arrow"></div>
                </div><br />
                <div class="Big-title">
                    <div class="littil-title">
                        未就业生信息
                    </div>
                    <div class="search-box">
                        <form action="${pageContext.request.contextPath}/unemp/findByUnEmp">
                            <select id="searchType" name="searchType" style="width:120px;height: 30px;">
                                <option value="sgrade">按年级</option>
                                <option value="sname">按姓名</option>
                                <option value="dname">按学生动向</option>
                            </select>
                            <input type="text" name="searchtext" style="width:120px;height: 30px;" onfocus="javascript:if(this.value=='请输入字符...')this.value='';" required="required" placeholder="请输入字符..."/>
                            <button class="mybutton" type="button" onclick="this.form.submit()"> <span>搜索</span> </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <hr class="fengexian">
        <div class="table-bar">
            <ul>
                <li class="active-li" onclick="ShowAllUnEmp()"> 全部动向 </li>
                <li onclick="ShowZhunBei()">准备就业</li>
                <li onclick="ShowKaoYan()">其他</li>
            </ul>
        </div>

        <div>
            <c:forEach varStatus="i" var="list" items="${UnempList}">
                <!--准备就业的表这是一条记录开始-->
                <table  class="pure-table pure-table-bordered left">
                    <tr>
                        <td rowspan="2" width="70px">
                            <button class="mybutton" type="button" onclick="location='${pageContext.request.contextPath}/student/findBySid?sid=${list.sid}'">${list.sname}</button>
                        </td>
                        <td width="100px">班级：</td>
                        <td width="100px">
                            <a onclick="ShowUnempStuBySclass(${list.sgrade},${list.sclass})">${list.spro}${list.sclass}班</a>
                        </td>
                        <td width="50px">年级：</td>
                        <td>${list.sgrade}级
                        </td>
                        <td width="50px">性别：</td>
                        <td> <c:if test="${list.ssex==false}">
                            男
                        </c:if>
                            <c:if test="${list.ssex==true}">
                                女
                            </c:if>
                        </td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>学生动向：</td>
                        <td colspan="5">
                                ${list.dname}
                        </td>
                        <td rowspan="4">
                            <button class="mybutton" type="button" onclick="location='${pageContext.request.contextPath}/direction/selectAllDirection2?sid=${list.sid}'">编辑</button>
                            <br>
                            <br>
                            <button class="mybutton" type="button" onclick="AreYouSourUnemp(${list.ueid})">删除</button>
                        </td>
                    </tr>
                </table>
                <div class="table-slipline"></div>
                <!--准备就业的表这是一条记录结束-->
            </c:forEach>
        </div>

    </div>
    <div>
        <div class="pagination pagination-centered">
            <ul>
                ${pageCode }
            </ul>
        </div>
        <div class="left-button-footer">
            <button type="submit" class="mybutton" value="Submit" onclick="window.open('${pageContext.request.contextPath}/unemp/outputUnemp')">导出数据</button>
        </div>
    </div>
</div>

</body>
</html>
