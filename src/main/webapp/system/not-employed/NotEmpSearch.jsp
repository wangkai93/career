<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2016/11/3
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<%@ page import="com.*" isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css"/>
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
                    <span>非就业生信息</span><div class="left-arrow"></div>
                    <span>搜索结果</span></div><br />
                <div class="Big-title">
                    <div class="littil-title">
                        非就业生信息
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
                            <button class="mybutton" type="button" onclick="JavaScript :history.back(-1)">
                                返回上一页
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <c:forEach varStatus="i" var="list" items="${listdata}">
            <div id="allUnEmp-table">
                <!--准备就业的表这是一条记录开始-->
                <table  class="pure-table pure-table-bordered left">
                    <tr>
                        <td rowspan="5" width="70px"><a href="/student/findBySid?sid=${list.sid}">${list.sname}</a></td>
                        <td width="100px">班级：</td>
                        <td width="100px">
                            <a href="NotEmpSearch.html">${list.spro}${list.sclass}班</a>
                        </td>
                        <td width="50px">年级：</td>
                        <td>
                            <a href="NotEmpSearch.html">${list.sgrade}级</a>
                        </td>
                        <td width="50px">性别：</td>
                        <td><c:if test="${list.ssex==false}">
                            <a>男</a>
                        </c:if>
                            <c:if test="${list.ssex==true}">
                                <a>女</a>
                            </c:if>
                        </td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>学生动向：</td>
                        <td colspan="5">${list.dname}</td>
                        <td rowspan="4">
                            <button class="mybutton" type="button" onclick="location='${pageContext.request.contextPath}/direction/selectAllDirection2?sid=${list.sid}'">编辑</button>
                            <br>
                            <br>
                            <button class="mybutton" type="button" onclick="AreYouSourUnemp(${list.ueid})">删除</button>
                        </td>
                    </tr>
                    <c:if test="${list.did == 1}">
                        <tr>
                            <td>期望岗位：</td>
                            <td colspan="5">${list.jname}</td>
                        </tr>
                        <tr>
                            <td>期望月薪:</td>
                            <td colspan="5">${list.uesalary}元/月</td>
                        </tr>
                        <tr>
                            <td>期望实习时间:</td>
                            <td colspan="5">${list.uetime}</td>
                        </tr>
                    </c:if>
                    <c:if test="${list.did == 2}">
                        <tr>
                            <td>期望院校：</td>
                            <td colspan="5">${list.ueschool}</td>
                        </tr>
                        <tr>
                            <td>期望专业:</td>
                            <td colspan="5">${list.uemajor}</td>
                        </tr>
                        <tr>
                            <td>结果:</td>
                            <td colspan="5">
                                <c:if test="${list.uesuccess == 1}">成功</c:if>
                                <c:if test="${list.uesuccess == 0}">暂无</c:if>
                                <c:if test="${list.uesuccess == 2}">失败</c:if>
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${list.did == 3}">
                        <tr>
                            <td>期望岗位：</td>
                            <td colspan="5">${list.jname}</td>
                        </tr>
                        <tr>
                            <td>期望月薪:</td>
                            <td colspan="5">${list.uesalary}元/月</td>
                        </tr>
                        <tr>
                            <td>结果:</td>
                            <td colspan="5">
                                <c:if test="${list.uesuccess == 1}">成功</c:if>
                                <c:if test="${list.uesuccess == 0}">暂无</c:if>
                                <c:if test="${list.uesuccess == 2}">失败</c:if>
                            </td>
                        </tr>
                    </c:if>
                </table>
                <div class="table-slipline"></div>
                <!--准备就业的表这是一条记录结束-->
            </div>
        </c:forEach>
    </div>
    <div class="button-footer">

        <div class="right-button-footer">

        </div>
        <div class="left-button-footer">
            <button class="mybutton" type="button" onclick="alert('弹出下载框！')">导出数据</button>
        </div>
    </div>
</div>
</body>
</html>

