<%--
  Created by IntelliJ IDEA.
  User: LENOVO
  Date: 2016/11/2
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/showele.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/upload.js" ></script>

</head>
<body onload="Activeli()">
<div class="table-box">
    <div class="table-content">
        <div class="table-head">
            <div class="table-address">
                <div style="float: left;">
                    <span>信息管理</span><div class="left-arrow"></div>
                    <span>就业生信息</span><div class="left-arrow"></div>
                </div><br />
                <div class="Big-title">
                    <div class="littil-title">
                        就业生信息
                    </div>
                    <div class="search-box">
                        <form action="${pageContext.request.contextPath}/emp/findByEmp">
                            <select id="searchType" name="searchType" style="width:120px;height: 30px;">
                                <option value="sname">按姓名</option>
                                <option value="jname">按岗位</option>
                                <option value="cname">按企业</option>
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
        <div>
            <c:forEach varStatus="i" var="emplist" items="${empStu}">
                <!--这是一条记录开始-->
                <table  class="pure-table pure-table-bordered left">
                    <tr>
                        <td rowspan="6" width="70px"><button class="mybutton" type="button" onclick="location='${pageContext.request.contextPath}/student/findBySid?sid=${emplist.sid}'">${emplist.sname}</button></td>
                        <td width="80px">班级：</td>
                        <td><a onclick="ShowEmpStuBySclass(${emplist.sgrade},${emplist.sclass})">${emplist.spro}${emplist.sclass}班</a></td>
                        <td width="60px">年级：</td>
                        <td width="120px">${emplist.sgrade}级</td>
                        <td width="60px">性别：</td>
                        <td><c:if test="${emplist.ssex==false}">
                            男
                        </c:if>
                            <c:if test="${emplist.ssex==true}">
                                女
                            </c:if>
                        </td>
                        <td rowspan="3">
                            <button class="mybutton" type="button" onclick="location='${pageContext.request.contextPath}/emp/forUpdateEmp?sid=${emplist.sid}'">编辑</button>
                        </td>
                    </tr>
                    <tr>
                        <td >就业企业：</td>
                        <td colspan="5"><button class="mybutton" type="button" onclick="findEmpStuByCid(${emplist.cid})">${emplist.cname}</button></td>
                    </tr>
                    <tr>
                        <td>岗位:</td>
                        <td colspan="5">
                            <button class="mybutton" type="button" onclick="ShowEmpStuByJname(${emplist.jid})">@${emplist.jname}</button>
                        </td>
                    </tr>
                    <tr>
                        <td>实习日期：</td>
                        <td colspan="2">${fn:substring(emplist.etime,0,10)}</td>
                        <td>实习补贴：</td>
                        <td colspan="2">${emplist.esalary}元/月</td>
                        <td rowspan="4">
                            <button class="mybutton" type="button" onclick="AreYouSouremp(${emplist.eid})">删除</button>
                        </td>
                    </tr>
                    <tr>
                        <td>推荐人：</td>
                        <td colspan="2">${emplist.urname}</td>
                        <td >是否网签：</td>
                        <td colspan="2">
                            <c:if test="${emplist.ewq==false}">
                                否
                            </c:if>
                            <c:if test="${emplist.ewq==true}">
                                是
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>备注:</td>
                        <td colspan="5">${emplist.einfo}</td>
                    </tr>
                </table>
                <div class="table-slipline"></div>
                <!--这是一条记录结束-->
            </c:forEach>
        </div>

        <div>
            <div class="pagination pagination-centered">
                <ul>
                    ${pageCode }
                </ul>
            </div>
            <div class="left-button-footer">
                <%--<button class="mybutton" type="button" onclick="alert('弹出下载框')"> <span>导出数据</span></button>--%>
            </div>
        </div>
    </div>
</div>

</body>
</html>

