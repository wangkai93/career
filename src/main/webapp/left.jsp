<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="css/left.css"/>
    <link rel="stylesheet" type="text/css" href="css/icon.css"/>
    <script type="text/javascript" src="js/icon.js" ></script>

</head>
<style>
    ul{
        list-style: none;
    }
</style>
<body >
<div class="left_bg">
    <div class="left-header">
        <img src="img/logo.png" />
    </div>
    <!--leftbarstart-->
    <div class="leftsidebar_box">
        <div class="line"></div>
        <dl class="source">
            <dt>信息管理<img src="img/select_xl01.png"></dt>
            <dd class="first_dd"><a href="#">企业信息</a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/company/findAllCompany?page=1" target=main>查看企业信息</a></li>
                    <li><a href="${pageContext.request.contextPath}/area/selectAllArea" target=main>添加企业</a></li>
                    <%--<li><a href="system/company/AllCompRecruit.html" target=main>招聘信息</a></li>--%>
                </ul>
            </dd>

            <dd><a href="#" >就业生信息</a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/emp/findAllEmp?page=1" target=main>查看信息</a></li>
                    <li><a href="${pageContext.request.contextPath}/emp/forAddEmp" target=main>添加就业生</a></li>
                </ul>
            </dd>
            <dd><a href="#" >未就业生信息</a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/unemp/findAllUnemp?page=1" target=main>查看信息</a></li>
                    <li><a href="${pageContext.request.contextPath}/direction/selectAllDirection" target=main>添加未就业生</a></li>
                </ul>
            </dd>
            <dd><a href="#" >招聘及面试信息</a>
                <ul>
                    <li><a href="recruit/findAllRecruits?page=1" target=main>查看招聘信息</a></li>
                    <li><a href="${pageContext.request.contextPath}/recruit/addpro" target=main>添加招聘信息</a></li>
                    <li><a href="inter/findAll?page=1" target=main>查看面试信息</a></li>
                </ul>
            </dd>
            <dd><a href="#">学生信息</a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/student/findAllStudents?page=1" target=main>查看信息</a></li>
                   <%-- <li><a href="system/studentsinfo/StudenetsUpload.jsp" target=main>添加学生</a></li>--%>
                </ul>
            </dd>
        </dl>
        <dl class="statistics">
            <dt>统计分析<img src="img/select_xl01.png"></dt>
            <dd class="first_dd"><a href="system/company/DrawComp.html" target=main>企业统计</a></dd>
            <dd ><a href="#">就业生统计</a></dd>
            <dd ><a href="${pageContext.request.contextPath}/unemp/DrawUnEmp" target=main>非就业生统计</a></dd>
            <dd ><a href="#">面试统计</a></dd>
            <dd><a href="#">学生成绩统计</a></dd>
        </dl>
        <dl class="custom">
            <dt>管理员中心<img src="img/select_xl01.png"></dt>
            <dd class="first_dd"><a href="system/admin/adminInfo.jsp" target=main>个人信息</a></dd>
            <dd><a href="${pageContext.request.contextPath}/user/selectAllUser" target=main>管理员列表</a></dd>
            <dd><a href="${pageContext.request.contextPath}/system/admin/inputData.jsp" target=main>数据初始化</a></dd>
            <dd><a href="#" >数据管理</a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/job/findAllJob" target=main>岗位管理</a></li>
                    <li><a href="${pageContext.request.contextPath}/direction/findAllDirection" target=main>未就业方向管理</a></li>
                </ul>
            </dd>
        </dl>
    </div>
    <!--leftbarend-->
</div>
<script src="js/jquery.min.js"></script>
<script>
    $(function(){
        $(".leftsidebar_box dt").css({"background-color":"#3992d0"});
        $(".leftsidebar_box dt img").attr("src","img/select_xl01.png");
        $(".leftsidebar_box dd").hide();
        $(".leftsidebar_box ul").hide();
        $(".leftsidebar_box dt").click(function(){
            $(".leftsidebar_box dt").css({"background-color":"#3992d0"})
            $(this).css({"background-color": "#317eb4"});
            $(this).parent().find('dd').removeClass("menu_chioce");
            $(".leftsidebar_box dt img").attr("src","img/select_xl01.png");
            $(this).parent().find('img').attr("src","img/select_xl.png");
            $(".menu_chioce").slideUp();
            $(this).parent().find('dd').slideToggle();
            $(this).parent().find('dd').addClass("menu_chioce");
            $('ul').removeClass("menu_chioce2");
            $(this).parent().find('dd').click(function(){
                //alert('ss')
                $(this).find('ul').removeClass("menu_chioce2");
                $(".menu_chioce2").slideUp();
                $(this).find('ul').slideDown();
                $(this).find('ul').addClass("menu_chioce2");

            });
        });
    })
</script>

</body>
</html>
