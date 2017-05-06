<%--
  Created by IntelliJ IDEA.
  User: w
  Date: 2016/10/26
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css"/>
    <script src="${pageContext.request.contextPath}/js/showele.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/showele.js" ></script>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>

    <script type="text/javascript">
        function showMarkCanvars(){
            document.getElementById("showAbility").style.display="block";
            document.getElementById("zhezhaobg").style.display="block";
        }
        function  hideMarkCanvars(){
            document.getElementById("showAbility").style.display="none";
            document.getElementById("zhezhaobg").style.display="none";
        }
        function updateAbility(sid) {

            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/student/updateAbilityPro?sid="+sid,
                dataType:"text",
                success: function(data){
                    //alert(data);
                    var json = eval("("+data+")"); // data的值是json字符串，这行把字符串转成object
                    var json = JSON.parse( data );
                    $("#sid").attr("value",json.sid);
                    if(json.sassess!=null){
                        $("#pingjia").text(json.sassess);
                    }else{
                        $("#pingjia").text("请填写对该生的评价！");
                    }
                    //alert(json.smark);
                    if(json.smark!=null){
                        /*$("input[name=smark]").each(function(index) {
                         if ($("input[name=smark]").get(index).value = json.smark) {
                         $("input[name=smark]").get(index).attr("checked","checked");
                         }
                         });*/
                        $("input[name=smark][value="+json.smark+"]").attr("checked","true");
                    }else{
                        //清除以前单选框的checked属性
                        $("input[type='radio']").removeAttr('checked');
                    }
                    showMarkCanvars();
                },
                error: function(XMLHttpRequest) {
                    alert(XMLHttpRequest.status);
                }
            });
        }
        function postwith(url, param) {
            var myForm = document.createElement("form");
            myForm.method = "post";
            myForm.action = url;
            for ( var k in param){
                var myInput = document.createElement("input");
                myInput.setAttribute("name", k);
                myInput.setAttribute("value", param[k]);
                myForm.appendChild(myInput);
            }
            document.body.appendChild(myForm);
            myForm.submit();
            document.body.removeChild(myForm);
        }
        function query(){
            var searchtext = document.getElementById("search").value;
            if (searchtext == "") {
                alert("关键字不能为空！");
            }else{
                $("#search").submit();
            }
        }
    </script>
</head>
<body onload="Activeli()">
<div class="table-box">
    <div class="table-content">
        <div class="table-head">
            <div class="table-address">
                <div style="float: left;">
                    <span>信息管理</span>
                    <div class="left-arrow"></div>
                    <span><a href="selecteAllC.html">学生列表</a></span>
                    <div class="left-arrow"></div>
                    <span>搜索结果</span>
                </div><br />
                <div class="Big-title">
                    <div class="littil-title">
                        学生列表
                    </div>
                    <div class="search-box">
                        <form action="${pageContext.request.contextPath}/student/query" method="post" name="search" id="search">
                            <select name="type">
                                <option value="0">按年级</option>
                                <option value="1">按专业</option>
                                <option value="2">按姓名</option>
                            </select>
                            <input type="text" name="searchtext" id="searchtext" placeholder="请输入……"/>
                            <button class="mybutton" type="button" onclick="query()"> <span>搜索</span> </button>
                            <button class="mybutton" type="button" onclick="JavaScript :history.back(-1)">
                                返回上一页
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <c:if test="${studentList!=null}">
                <!--这是表格开始-->
                <table  class="pure-table pure-table-bordered">
                    <tr>
                        <td>姓名</td>
                        <td>学号</td>
                        <td>性别</td>
                        <td>年级</td>
                        <td>班级</td>
                        <td>能力认定</td>
                        <td>状态</td>
                    </tr>
                    <c:forEach var="student" items="${studentList}">
                    <!--这是一条记录开始-->
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/student/findBySid?sid=${student.sid}">${student.sname}</a>
                        </td>
                        <td>
                            ${student.sno}
                        </td>
                        <td>
                            <c:if test="${!(student.ssex)}">
                                男
                            </c:if>
                            <c:if test="${student.ssex}">
                                女
                            </c:if>
                        </td>
                        <td><a href="${pageContext.request.contextPath}/student/findBySgrade?sgrade=${student.sgrade}">${student.sgrade}级</a></td>
                        <td><a href="javascript:postwith('${pageContext.request.contextPath}/student/findBySclass',{'spro':'${student.spro}','sclass':'${student.sclass}'})">${student.spro}${student.sclass}班</a></td>
                        <td>
                            <c:if test="${student.smark!=null}">
                                <button class="mybutton" type="button" onclick="updateAbility(${student.sid})">${student.smark}星</button>
                            </c:if>
                            <c:if test="${student.smark==null}">
                                <button class="mybutton" type="button" onclick="updateAbility(${student.sid})">暂无评分</button>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${student.sstate==0}">
                                正常
                            </c:if>
                            <c:if test="${student.sstate==2}">
                                留级
                            </c:if>
                            <c:if test="${student.sstate==3}">
                                休学
                            </c:if>
                        </td>
                    </tr>
                    <!--这是一条记录结束-->
                    </c:forEach>

                </table>
                <div class="table-slipline"></div>
                <!--这是表格结束-->
            </c:if>
            <c:if test="${studentList==null}">
                此处没有学生数据
            </c:if>
        </div>
    </div>
    <div class="button-footer">

        <div class="right-button-footer">
            <%--<div id="Page">
                <a href="#">«</a>
                <span>1</span>
                <a href="#">2</a>
                <a href="#">3</a>
                <a href="#">4</a>
                <a href="#">5</a>
                <a href="#">6</a>
                <a href="#">»</a>
            </div>--%>
        </div>
        <div class="left-button-footer">
            <%--<button class="mybutton" type="button" onclick="alert('弹出下载框！')"> <span>导出数据</span></button>--%>
        </div>
    </div>
</div>

<div id="showupload-div">
    <div class="tab-close">
        <button class="mybutton" type="button" onclick="HideUpload()">关闭</button>
    </div>
    <iframe src="../tools/UploadExcel.html" width="810px" height="340px" frameborder="0"></iframe>
</div>

<div id="showAbility">
    <div class="tab-close">
        <button class="mybutton" type="button" onclick="hideMarkCanvars()">取消</button>
    </div>
    <form action="${pageContext.request.contextPath}/student/updateAbility2" method="post">
        <div class="starability-container">
            <h3>能力评分：</h3>
            <input type="hidden" name="sid" id="sid">
            <fieldset class="starability-checkmark">
                <input type="radio" id="rate5-6" name="smark" value="5" />
                <label for="rate5-6" title="非常好">5 stars</label>

                <input type="radio" id="rate4-6" name="smark" value="4" />
                <label for="rate4-6" title="好">4 stars</label>

                <input type="radio" id="rate3-6" name="smark" value="3" />
                <label for="rate3-6" title="一般">3 stars</label>

                <input type="radio" id="rate2-6" name="smark" value="2" />
                <label for="rate2-6" title="不好">2 stars</label>

                <input type="radio" id="rate1-6" name="smark" value="1" />
                <label for="rate1-6" title="特别差">1 star</label>
            </fieldset>
        </div>
        <div class="starability-container">
            <h3>教师评价：</h3>
            <textarea name="sassess" id="pingjia"></textarea><br />
            <div class="buttonbox">
                <input type="button" value="保存" class="mybutton" onclick="this.form.submit()"/>
            </div>
        </div>
    </form>
</div>
<div id="zhezhaobg"></div>
</body>
</html>
