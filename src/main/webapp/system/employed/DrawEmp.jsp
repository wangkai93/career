<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/6
  Time: 17:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"  %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/icon.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/canvarsDraw.js" ></script>

    <script>
        window.onload = function() {
            var canvas = document.getElementById("pie_canvas");
            var seriesData = [
                {name:"开发岗", value:${kaifa}, color:"RGBA(255,0,0,1)"},
                {name:"非开发岗", value:${feikaifa}, color:"RGBA(255,255,0,1)"}]
            var config = {
                width : 475,
                height: 400,
                series: seriesData,
                canvas: canvas,
                unit: "kg",
                title:"${year}级学生非就业情况分布",
                tooltips : {
                    enable : true
                },
                animation :{
                    enable: true
                },
                legend : {
                    enable : true
                },
                text : {
                    enable: true
                },
            };
            pieChart.initSettings(config);
            pieChart.render();
        }
    </script>
</head>
<body>
<div class="table-content">
    <div class="table-head">
        <div class="table-address">
            <span>统计分析</span><i style="color: #317EB4;">>></i><span>未就业生</span>
        </div>
    </div>
    <div class="big-canvar-box">
        <div class="canvars-box2">
            <div id="my_container" style="width:95%; height:95%;">
                <canvas id="pie_canvas"></canvas>
            </div>
        </div>
    </div>
</div>

</body>
</html>
