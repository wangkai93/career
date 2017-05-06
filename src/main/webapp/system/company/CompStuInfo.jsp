<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.*" isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css"/>
    <script src="${pageContext.request.contextPath}/js/showele.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css" />
</head>
<body>
<div class="table-box">
    <div class="table-content">
        <!--这是标题栏-->
        <div class="table-head">
            <div class="table-address">
                <div style="float: left;">
                    <span>信息管理</span><div class="left-arrow"></div>
                    <span><a href="selecteAllC.html">企业管理</a></span><div class="left-arrow"></div>
                    <span>在岗学生名单</span>
                </div> <br />
                <div class="Big-title">
                    <div class="littil-title">
                        在岗学生名单
                    </div>
                    <div class="search-box">
                        <form action="${pageContext.request.contextPath}/company/findByType">
                            <select id="searchType" name="searchType" style="width:120px;height: 30px;">
                                <option value="sname">按姓名</option>
                                <option value="jname">按岗位</option>
                                <option value="sgrade">按年级</option>>
                            </select>
                            <input type="text" id="searchtext" name="searchtext" style="width:120px;height: 30px;" onfocus="javascript:if(this.value=='请输入字符...')this.value='';" required="required" placeholder="请输入字符..."/>
                            <button class="mybutton" type="button" onclick="this.form.submit()"> <span>搜索</span> </button>
                            <button class="mybutton" type="button" onclick="JavaScript :history.back(-1)">
                                返回上一页
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!--这是标题栏结束-->
        <div>
            <table  class="pure-table pure-table-bordered CompInfo1">
                <tr>
                    <td>姓名</td>
                    <td>年级</td>
                    <td>班级</td>
                    <td>性别</td>
                    <td>岗位</td>
                    <td>联系电话</td>
                    <td>入职日期</td>
                </tr>
                <c:if test="${! empty dataList }">
                <c:forEach var="dataList" items="${dataList}" varStatus="i">
                <!--这是一条记录开始-->
                <tr>
                    <td>${dataList.sname}
                    </td>
                    <td>${dataList.sgrade}
                    </td>
                    <td>${dataList.spro}${dataList.sclass}班
                    </td>
                    <td><c:if test="${dataList.ssex==false}">
                        男
                    </c:if>
                        <c:if test="${dataList.ssex==true}">
                            女
                        </c:if>
                    </td>
                    <td>
                        ${dataList.jname}&nbsp;
                    </td>
                    <td>${dataList.sphone}</td>
                    <td>${fn:substring(dataList.etime,0,10)}</td>
                </tr>
                </c:forEach>
                </c:if>
            </table>
            <div class="table-slipline"></div>
        </div>
        <div class="button-footer">

            <div class="right-button-footer">
                <div id="Page">
                </div>
            </div>
            <div class="left-button-footer">
            	<c:if test="${! empty dataList }">
                <button type="submit" class="mybutton" value="Submit" onclick="window.open('${pageContext.request.contextPath }company/outputComStu?cid=${dataList.get(0).cid}')">导出数据</button>
            	</c:if>
            </div>
        </div>
    </div>
    </div>
</body>
</html>

