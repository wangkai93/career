<%--
  Created by IntelliJ IDEA.
  User: w
  Date: 2016/11/1
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css"/>
    <script src="${pageContext.request.contextPath}/js/showele.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/icon.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/showele.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Date.js" ></script>
    <script src="${pageContext.request.contextPath}/js/addtime.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>

    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/lang/zh-cn/zh-cn.js"></script>

</head>
<body onload="addtime()">
<div class="table-box">
    <div class="table-content">
        <div class="table-head">
            <div class="table-address">
                <div style="float: left;">
                    <span>信息管理</span><div class="left-arrow"></div>
                    <span>招聘及面试信息</span><div class="left-arrow"></div>
                    <span>修改招聘信息</span>
                </div><br />
                <br />
                <div class="Big-title">
                    <div class="littil-title">
                        修改招聘信息
                    </div>
                    <div class="search-box">
                        <button class="mybutton" type="button" onclick="JavaScript :history.back(-1)">
                            返回上一页
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <!--这是一条记录开始-->
            <form action="${pageContext.request.contextPath}/recruit/updateRecruit" method="post">
                <table  class="pure-table pure-table-bordered" style="text-align:left">
                    <input type="hidden" name="rid" value="${recruit.rid }">
                    <tr>
                        <td>企业名称：</td>
                        <td>
                            <select name="cid">
                                <c:forEach items="${companyList}" var="company">
                                    <c:choose>
                                        <c:when test="${company.cid==recruit.cid }">
                                            <option value="${company.cid }" selected="selected">${company.cname}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${company.cid }">${company.cname}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>工作城市：</td>
                        <td>
                            <select name="aprovince" id="aprovince" onchange="findcity();">
                                <c:forEach items="${areaList}" var="area">
                                    <c:choose>
                                        <c:when test="${area.aprovince.equals(recruit.aprovince)}">
                                            <option value="${area.aprovince}" selected="selected">${area.aprovince}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${area.aprovince}">${area.aprovince}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <select id="city" name="aid">
                                <c:forEach items="${cityList}" var="mycity">
                                    <c:choose>
                                        <c:when test="${mycity.aid==recruit.aid }">
                                            <option value="${mycity.aid}" selected="selected">${mycity.acity}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${mycity.aid}">${mycity.acity}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>竞聘岗位：</td>
                        <td>
                            <select name="jid">
                                <c:forEach items="${jobList}" var="job">
                                    <c:choose>
                                        <c:when test="${job.jid==recruit.jid }">
                                            <option value="${job.jid }" selected="selected">${job.jname}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${job.jid }">${job.jname}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <a href="${pageContext.request.contextPath}/job/findAllJob" style="color:#518bff;">&nbsp;&nbsp;若没有需要的岗位，请点击这里添加。</a>
                        </td>
                    </tr>
                    <tr>
                        <td>月薪：</td>
                        <td>
                            <input type="text" name="rsalary" value="${recruit.rsalary}" />RMB
                        </td>
                    </tr>
                    <tr>
                        <td >性别要求：</td>
                        <td >
                            <%--<c:if test="${recruit.rsex==null}">
                                <input type="radio" name="rsex" value="0">男
                                <input type="radio" name="rsex" value="1">女
                                <input type="radio" name="rsex" value="2" checked="checked">不限
                            </c:if>--%>
                            <c:if test="${!(recruit.rsex)}">
                                <input type="radio" name="rsex" value="0" checked="checked">男
                                <input type="radio" name="rsex" value="1">女
                                <%--<input type="radio" name="rsex" value="2">不限--%>
                            </c:if>
                            <c:if test="${recruit.rsex}">
                                <input type="radio" name="rsex" value="0">男
                                <input type="radio" name="rsex" value="1" checked="checked">女
                                <%--<input type="radio" name="rsex" value="2">不限--%>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td >招聘人数：</td>
                        <td ><input type="text" name="rnum" value="${recruit.rnum}"/>人</td>
                    </tr>

                    <tr>
                        <td>截止时间：</td>
                        <td >
                            <input type="text" id="add_date" name="rend" value="<fmt:formatDate value="${recruit.rend}" pattern="yyyy-MM-dd"/>" onclick="choose_date_czw('add_date');"/>
                        </td>
                    </tr>
                    <tr>
                        <td>详情:</td>
                        <td>
                            <script id="editor" name="rinfo" type="text/plain" style="width:800px;height:300px;">${recruit.rinfo}</script>
                        </td>
                    </tr>

                    <tr style="text-align: center;">
                        <td colspan="2">
                            <button class="mybutton" type="button" onclick="this.form.submit()">保存</button>
                        </td>
                    </tr>
                </table>
            </form>
            <div class="table-slipline"></div>
            <!--这是一条记录结束-->


        </div>
    </div>

</div>

<script type="text/javascript">

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');


    function isFocus(e){
        alert(UE.getEditor('editor').isFocus());
        UE.dom.domUtils.preventDefault(e)
    }
    function setblur(e){
        UE.getEditor('editor').blur();
        UE.dom.domUtils.preventDefault(e)
    }
    function insertHtml() {
        var value = prompt('插入html代码', '');
        UE.getEditor('editor').execCommand('insertHtml', value)
    }
    function createEditor() {
        enableBtn();
        UE.getEditor('editor');
    }
    function getAllHtml() {
        alert(UE.getEditor('editor').getAllHtml())
    }
    function getContent() {
        var arr = [];
        arr.push("使用editor.getContent()方法可以获得编辑器的内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getContent());
        alert(arr.join("\n"));
    }
    function getPlainTxt() {
        var arr = [];
        arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getPlainTxt());
        alert(arr.join('\n'))
    }
    function setContent(isAppendTo) {
        var arr = [];
        arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
        UE.getEditor('editor').setContent('欢迎使用ueditor', isAppendTo);
        alert(arr.join("\n"));
    }
    function setDisabled() {
        UE.getEditor('editor').setDisabled('fullscreen');
        disableBtn("enable");
    }

    function setEnabled() {
        UE.getEditor('editor').setEnabled();
        enableBtn();
    }

    function getText() {
        //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
        var range = UE.getEditor('editor').selection.getRange();
        range.select();
        var txt = UE.getEditor('editor').selection.getText();
        alert(txt)
    }

    function getContentTxt() {
        var arr = [];
        arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
        arr.push("编辑器的纯文本内容为：");
        arr.push(UE.getEditor('editor').getContentTxt());
        alert(arr.join("\n"));
    }
    function hasContent() {
        var arr = [];
        arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
        arr.push("判断结果为：");
        arr.push(UE.getEditor('editor').hasContents());
        alert(arr.join("\n"));
    }
    function setFocus() {
        UE.getEditor('editor').focus();
    }
    function deleteEditor() {
        disableBtn();
        UE.getEditor('editor').destroy();
    }
    function disableBtn(str) {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            if (btn.id == str) {
                UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
            } else {
                btn.setAttribute("disabled", "true");
            }
        }
    }
    function enableBtn() {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
        }
    }

    function getLocalData () {
        alert(UE.getEditor('editor').execCommand( "getlocaldata" ));
    }

    function clearLocalData () {
        UE.getEditor('editor').execCommand( "clearlocaldata" );
        alert("已清空草稿箱")
    }

    <!--查询地区-->
    function findcity() {
        var myselect = document.getElementById("aprovince");
        var index = myselect.selectedIndex;
        var aprovince = myselect.options[index].value;
        //alert(aprovince);
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/area/findcity",
            data:"aprovince="+aprovince,
            dataType:"text",
            success: function(data){
                var json = eval("("+data+")"); // data的值是json字符串，这行把字符串转成object
                var json = JSON.parse( data );
                var city = $("#city");
                var str = '';
                //清空以前的option
                $("#city").find("option").remove();
                for(var o in json) {
                    str += '<option value="'+json[o].aid+'">'+json[o].acity+'</option>';
                }
                city.append(str);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        });
    }
</script>

</body>
</html>

