<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"  %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css"/>
    <script src="${pageContext.request.contextPath}/js/showele.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/valid.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Cityjs/jquery.js" ></script>
    <script type="text/javascript">
	    
	    function job(){
	        document.getElementById("job").submit();
	    }
	    
	    function department(){
	        document.getElementById("department").submit();
	    }
	
	    function direction(){
	        document.getElementById("direction").submit();
	    }
	
        function area(){
            document.getElementById("area").submit();
        }

        function student(){
            document.getElementById("student").submit();
        }

        function grade(){
            document.getElementById("grade").submit();
        }

        function company(){
            document.getElementById("company").submit();
        }

        function empstu(){
            document.getElementById("empstu").submit();
        }

        function unempstu(){
            document.getElementById("unempstu").submit();
        }
    </script>
</head>
<body onload="init()">
<div class="table-box">
    <span style="color: red" ><h3>${file}</h3></span><br>
    <div class="table-content">
        <!--这是标题栏-->
        <div class="table-head">
            <div class="table-address">
                <div style="float: left;">
                    <span>管理中心</span><div class="left-arrow"></div><span>数据初始化</span>
                </div> <br />
                <div class="Big-title">
                    <div class="littil-title">
                        Excel初始数据导入
                    </div>
                </div>
            </div>
        </div>
        <!--这是标题栏结束-->
        <div>
            <!--这是表格开始-->
            <table  class="pure-table pure-table-bordered left">
            
            <tr>
                    <td>专业初始化</td>
                    <td>
                        <button type="submit" class="mybutton" value="Submit" onclick="window.open('${pageContext.request.contextPath}/upload/department(ok).xls')">下载模板</button>
                        </td>
                    <td>
                       <form action="${pageContext.request.contextPath}/api/department/inputDepartment" method="post" enctype="multipart/form-data" id="department">
                          <input type="file" name="file" />
                          <button type="submit" class="mybutton" value="Submit" onclick="direction()">开始导入</button>
                       </form>
                    </td>
                </tr>
            
              <tr>
                    <td>方向初始化</td>
                    <td>
                        <button type="submit" class="mybutton" value="Submit" onclick="window.open('${pageContext.request.contextPath}/upload/direction(ok).xls')">下载模板</button>
                        </td>
                    <td>
                       <form action="${pageContext.request.contextPath}/direction/inputDirection" method="post" enctype="multipart/form-data" id="direction">
                          <input type="file" name="file" />
                          <button type="submit" class="mybutton" value="Submit" onclick="direction()">开始导入</button>
                       </form>
                    </td>
                </tr>
                
                
                  <tr>
                    <td>职位初始化</td>
                    <td>
                        <button type="submit" class="mybutton" value="Submit" onclick="window.open('${pageContext.request.contextPath}/upload/job(ok).xls')">下载模板</button>
                        </td>
                    <td>
                       <form action="${pageContext.request.contextPath}/job/inputJob" method="post" enctype="multipart/form-data" id="job">
                          <input type="file" name="file" />
                          <button type="submit" class="mybutton" value="Submit" onclick="jobjobjobjob()">开始导入</button>
                       </form>
                    </td>
                </tr>
            
                <tr>
                    <td>地区初始化</td>
                    <td>
                        <button type="submit" class="mybutton" value="Submit" onclick="window.open('${pageContext.request.contextPath}/upload/Area(ok).xls')">下载模板</button>
                        </td>
                    <td>
                       <form action="${pageContext.request.contextPath}/area/inputArea" method="post" enctype="multipart/form-data" id="area">
                          <input type="file" name="file" />
                          <button type="submit" class="mybutton" value="Submit" onclick="area()">开始导入</button>
                       </form>
                    </td>
                </tr>
                <tr>
                    <td>学生初始化</td>
                    <td>
                        <button type="submit" class="mybutton" value="Submit" onclick="window.open('${pageContext.request.contextPath}/upload/Student(ok).xls')">下载模板</button>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/student/inputStudent" method="post" enctype="multipart/form-data" id="student">
                            <input type="file" name="file" />
                            <button type="submit" class="mybutton" value="Submit" onclick="student()">开始导入</button>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td>公司初始化</td>
                    <td>
                        <button type="submit" class="mybutton" value="Submit" onclick="window.open('${pageContext.request.contextPath}/upload/Company(ok).xls')">下载模板</button>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/company/inputCompany" method="post" enctype="multipart/form-data" id="company">
                            <input type="file" name="file" />
                            <button type="submit" class="mybutton" value="Submit" onclick="company()">开始导入</button>
                        </form>
                    </td>
                </tr>
                
                </tr>
                <tr>
                    <td>学生成绩导入</td>
                    <td>
                        <button type="submit" class="mybutton" value="Submit" onclick="window.open('${pageContext.request.contextPath}/upload/Grade(ok).xls')">下载模板</button>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/grade/inputGrade" method="post" enctype="multipart/form-data" id="grade">
                            <input type="file" name="file" />
                            <button type="submit" class="mybutton" value="Submit" onclick="grade()">开始导入</button>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td>已就业学生导入</td>
                    <td>
                        <button type="submit" class="mybutton" value="Submit" onclick="window.open('${pageContext.request.contextPath}/upload/Emp(ok).xls')">下载模板</button>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/emp/inputEmp" method="post" enctype="multipart/form-data" id="empstu">
                            <input type="file" name="file" />
                            <button type="submit" class="mybutton" value="Submit" onclick="empstu()">开始导入</button>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td>未就业学生导入</td>
                    <td>
                        <button type="submit" class="mybutton" value="Submit" onclick="window.open('${pageContext.request.contextPath}/upload/Unemp(ok).xls')">下载模板</button>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/unemp/inputUnemp" method="post" enctype="multipart/form-data" id="unempstu">
                            <input type="file" name="file" />
                            <button type="submit" class="mybutton" value="Submit" onclick="unempstu()">开始导入</button>
                        </form>
                    </td>
                </tr>
            </table>
            <div class="table-slipline"></div>
            <!--这是表格结束-->
        </div>
        <div id="zhezhaobg2"></div>
    </div>
</div>
<script type="text/javascript">
function init() {
<c:if test="${!empty file}">
    alert(${file});
</c:if>
alert("File"+${file});
}
</script>
</body>
</html>
