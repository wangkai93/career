function ShowDetailTip(){
	document.getElementById('show-detail-tip').style.display="block";
	document.getElementById("zhezhaobg").style.display="block";
}
function HideDetailTip(){
	document.getElementById('show-detail-tip').style.display="none";
	document.getElementById("zhezhaobg").style.display="none";
}
function ShowDetailInfo(){
	document.getElementById('show-detail-info').style.display="block";
	document.getElementById("zhezhaobg").style.display="block";
}
function HideDetailInfo(){
	document.getElementById('show-detail-info').style.display="none";
	document.getElementById("zhezhaobg").style.display="none";
}


function ShowCompByCid(cid){
    var result = confirm('查看该公司信息！');
    if(result){
        window.location.href = "findByCompCid?cid="+cid;
    }else{
        alert('取消！');
    }
}

function findEmpStuByCid(cid){
    var result = confirm('该公司下所有学生信息！');
    if(result){
        window.location.href = "findEmpStuByCid?cid="+cid;
    }else{
        alert('取消！');
    }
}

function selectRecruitInfo(cid,jid){
    var result = confirm('查看该公司该岗位下学生信息！');
    if(result){
        window.location.href = "findStuInfoByJname?cid="+cid +"&jid="+jid;
    }else{
        alert('取消！');
    }
}

function AreYouSourCompany(cid){
    var result = confirm('您确定要删除该条记录吗！');
    if(result){
        window.location.href = "delCompany?cid="+cid;
    }else{
        alert('不删除！');
    }
}

function ShowEmpStuBySclass(sgrade,sclass){
    var result = confirm('查看该年级该班级下已就业生！');
    if(result){
        window.location.href = "findEmpStuBySclass?sgrade="+sgrade+"&sclass="+sclass;
    }else{
        alert('取消！');
    }
}
function ShowEmpStuByJname(jid){
    var result = confirm('查看该岗位下已就业生！');
    if(result){
        window.location.href = "findEmpStuByJname?jid="+jid;
    }else{
        alert('取消！');
    }
}

function ShowUnempStuBySclass(sgrade,sclass){
    var result = confirm('查看该年级该班级下未就业生！');
    if(result){
        window.location.href = "findUnEmpStuBySclass?sgrade="+sgrade+"&sclass="+sclass;
    }else{
        alert('取消！');
    }
}
function AreYouSouremp(eid){
    var result = confirm('您确定要删除该条记录吗！');
    if(result){
        window.location.href = "delEmp?eid="+eid;
    }else{
        alert('不删除！');
    }
}

function AreYouSourDir(did){
    var result = confirm('您确定要删除该条记录吗！');
    if(result){
        window.location.href = "delDiretion?did="+did;
    }else{
        alert('不删除！');
    }
}

function AreYouSourUnemp(ueid){
    var result = confirm('您确定要删除该条记录吗！');
    if(result){
        window.location.href = "delUnEmp?ueid="+ueid;
    }else{
        alert('不删除！');
    }
}

function AreYouSourOut(eve){
	var result = confirm('您确定要退出系统吗！');  
    if(result){  
        alert('欢迎再来！');  
    }else{  
        return false; 
    }  
}
 
function Activeli() {
       function removeActiveClass(node) {
                node.className = '';
       }
        document.querySelector('.table-bar ul ').onclick = function (e) {
        Array.prototype.forEach.call(document.querySelectorAll('.table-bar ul  > li'), removeActiveClass);
        var target = e.target;
        target.className = 'active-li';
        }
} 
/*--------------------QiWang----------------------*/
function ShowQiwangText(){
	document.getElementById("qiwang-text").style.display="block";
	document.getElementById("mark-text").style.display="none";
	document.getElementById("mark-canvars").style.display="none";
}
function ShowMarkText(){
	document.getElementById("mark-text").style.display="block";
	document.getElementById("qiwang-text").style.display="none";
	document.getElementById("mark-canvars").style.display="none";
}
function ShowMarkCanvars(){
    document.getElementById("mark-canvars").style.display="block";
	document.getElementById("mark-text").style.display="none";
	document.getElementById("qiwang-text").style.display="none";
}
/*---------------UnEmp未就业生------------------*/
function ShowAllUnEmp(){
}
function ShowKaoYan(){
    var result = confirm('其他意向！');
    if(result){
        window.location.href = "directionOthers";
    }else{
        return false;
    }
}
function ShowZhunBei(){
    var result = confirm('准备就业！');
    if(result){
        window.location.href = "directionEmp?did=0";
    }else{
        return false;
    }
}
/*-----------------------------------------------*/

function ShowTabs(){
	document.getElementById("showtabs-div").style.display="block";
	document.getElementById("zhezhaobg").style.display="block";
}
function HideTabs(){
	document.getElementById("showtabs-div").style.display="none";
	document.getElementById("zhezhaobg").style.display="none";
}
function ShowUpload(){
	document.getElementById("showupload-div").style.display="block";
	document.getElementById("zhezhaobg").style.display="block";
}
function HideUpload(){
	document.getElementById("showupload-div").style.display="none";
	document.getElementById("zhezhaobg").style.display="none";
}

/*------------------------就业生--------------------------------*/
function ShowAllEmpStu(){
    document.getElementById("kaifastu").style.display="block";
    document.getElementById("feikaifastu").style.display="block";
}
function ShowKaifaEmpStu(){
    var result = confirm('开发岗！');
    if(result){
        window.location.href = "KaifaEmp?jtype=true";
    }else{
        return false;
    }
}
function ShowFeikaifaEmpStu(){
    var result = confirm('非开发岗！');
    if(result){
        window.location.href = "FeikaifaEmp?jtype=false";
    }else{
        return false;
    }
}

/*---------------------------------------------------------*/

/*----------------------添加面试学生开始----*/

function ShowAddStu(){
    document.getElementById("showAddstu-div").style.display="block";
    document.getElementById("zhezhaobg").style.display="block";
}

function  HideAddStu(){
    document.getElementById("showAddstu-div").style.display="none";
    document.getElementById("zhezhaobg").style.display="none";
    document.getElementById("search-result").style.display="none";
}
function  ShowSearchResult(){
    document.getElementById("search-result").style.display="block";
}
function  ShowMeetResult(){
    document.getElementById("showMeetResult").style.display="block";
     document.getElementById("zhezhaobg").style.display="block";
}
function  HideMeetResult(){
    document.getElementById("showMeetResult").style.display="none";
    document.getElementById("zhezhaobg").style.display="none";
}
/*----------------------添加面试学生结束----*/

/*开启编辑开始*/
function BeginJianjie(){
	 
			document.getElementById("jianjie").removeAttribute('disabled');
		}
function beginBeizhu(){
			document.getElementById("beizhu").removeAttribute('disabled');
		}
function beginBianji(){
						/*alert('ss')*/
						document.getElementById('pingjia').removeAttribute('disabled');
					}
function beginEdit(){
						 /*alert('可以进行编辑！')*/
						document.getElementById("qw-gw").removeAttribute('disabled');
						document.getElementById("qw-gz").removeAttribute('disabled');
                        document.getElementById("qw-comp").removeAttribute('disabled');
                        document.getElementById("qw-ad").removeAttribute('disabled');

					}
/*开启编辑结束*/
/*管理员的添加和修改开始*/
function  ShowAdminAdd(){
    document.getElementById("AdminAdd").style.display="block";
     document.getElementById("zhezhaobg").style.display="block";
}
function  HideAdminAdd(){
    document.getElementById("AdminAdd").style.display="none";
    document.getElementById("zhezhaobg").style.display="none";
}
function  ShowAdminUpdate(){
    document.getElementById("AdminUpdate").style.display="block";
     document.getElementById("zhezhaobg2").style.display="block";
}
function  HideAdminUpdate(){
    document.getElementById("AdminUpdate").style.display="none";
    document.getElementById("zhezhaobg2").style.display="none";
}
/*管理员的添加和修改结束*/

function  ShowStuUpdate(){
    document.getElementById("StuUpdate").style.display="block";
     document.getElementById("zhezhaobg").style.display="block";
}
function  HideStuUpdate(){
    document.getElementById("StuUpdate").style.display="none";
    document.getElementById("zhezhaobg").style.display="none";
}
function  ShowGwAdd(){
    document.getElementById("GangWeiAdd").style.display="block";
     document.getElementById("zhezhaobg").style.display="block";
}
function  HideGwAdd(){
    document.getElementById("GangWeiAdd").style.display="none";
    document.getElementById("zhezhaobg").style.display="none";
}

function  ShowFXAdd(){
    document.getElementById("FangXiangAdd").style.display="block";
     document.getElementById("zhezhaobg").style.display="block";
}
function  HideFXAdd(){
    document.getElementById("FangXiangAdd").style.display="none";
    document.getElementById("zhezhaobg").style.display="none";
}
function  ShowAdminPwd(){
    document.getElementById("AdminPwd").style.display="block";
     document.getElementById("zhezhaobg").style.display="block";
}
function  HideAdminPwd(){
    document.getElementById("AdminPwd").style.display="none";
    document.getElementById("zhezhaobg").style.display="none";
}









