<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
        //初始化表单
        $("#bntable").jqGrid({
            url : "${path}/user/queryByPage",
            editurl:"${path}/user/edit",
            datatype : "json",
            rowNum : 3,  //每页展示条数   page   rows
            rowList : [ 5,10, 20, 30 ],  //可选展示条数
            styleUI:"Bootstrap",
            pager : '#bnpage',  //分页工具栏
            viewrecords : true,  //是否显示总条数
            autowidth:true,
            height : "auto",
            colNames : [ 'Id','头像','名字','密码','性别','状态','手机号','注册时间','地址','签名'],
            colModel : [
                {name : 'id',width : 55},
                {name : 'avatar',width : 90,align : "center",edittype:"file"},
                {name : 'name',editable:true,width : 100},
                {name : 'password',editable:true,width : 80,align : "right"},
                {name : 'sex',editable:true,width : 80,align : "right"},
                {name : 'status',editable:true,width : 80,align : "right",
                    formatter:function (cellValue,option,row) {
                        if(cellValue==1){
                            //展示
                            return "<button class='btn btn-danger' onclick='updateStatus(\""+row.id+"\",\""+cellValue+"\")' >不展示</button>";
                        }else {
                            //不展示
                            return "<button class='btn btn-success' onclick='updateStatus(\""+row.id+"\",\""+cellValue+"\")' >展示</button>";
                        }
                    }
                },
                {name : 'phone',editable:true,width : 80,align : "right"},
                {name : 'crea_date',editable:true,width : 80,align : "right"},
                {name : 'city',editable:true,width : 80,align : "right"},
                {name : 'sign',editable:true,width : 80,align : "right"}
            ]
        });

        //处理增删改查操作
        $("#bntable").jqGrid('navGrid', '#bnpage', {edit : true,add :false,del : true},
            {
                //关闭对话框
                closeAfterEdit:true,
                beforeShowForm:function (obj) {
                    obj.find("#avatar").attr("disabled",true)//禁用按钮
                }
            },  //执行修改操作的额外配置
            {
                //关闭对话框
                closeAfterAdd:true,
                afterSubmit:function(data){
                    $.ajaxFileUpload({
                        url:"${path}/user/bannerUpload",
                        datatype:"json",
                        type:"post",
                        fileElementId:"avatar", // 需要上传的文件域的ID
                        data:{id:data.responseText},
                        success:function(){
                            //刷新页面
                            $("#bntable").trigger("reloadGrid");
                        }
                    });
                    return "ssss";
                }
            }, //执行添加操作的额外配置
            {}
        );
    });
    //修改状态
    function updateStatus(id, status) {

        if(status==1){
            $.ajax({
                url:"${path}/user/updateStatus",
                type:"post",
                dataType:"JSON",
                data:{"id":id,"status":"2"},
                success:function(){
                    //刷新页面
                    $("#bntable").trigger("reloadGrid");
                }
            });
        }else{
            $.ajax({
                url:"${path}/user/updateStatus",
                type:"post",
                dataType:"json",
                data:{"id":id,"status":"1"},
                success:function(){
                    //刷新页面
                    $("#bntable").trigger("reloadGrid");
                }
            });
        }
    }


    $("#btn1").click(function(){
        $.ajax({
            url:"${path}/user/queryAllUser",
            dataType:"JSON",
            type:"GET",
            success:function (data) {
                $("#message").text(data.message);
            }
        })
    });
    //点击发送短信验证码按钮
    $("#btnphone").click(function(){

        //获取手机号
        var phone = $("#phoneInput").val();
        //alert(phone);

        $.post("${path}/user/getPhone?phone="+phone,function(data){},"json");

    });

</script>

<%--初始化面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>

    <ul class="nav nav-tabs" >
        <li class="active">
            <a href="#">用户管理</a>
        </li>
    </ul>
        <div class="panel panel-body">
            <button id="btn1" class="btn btn-info">导出用户</button>
            <div align="right">
                <input id="phoneInput" name="phone" type="text" />&emsp;
                <button id="btnphone" class="btn btn-info">发送验证码</button>
            </div>
        </div>
    <%--初始表单--%>
    <table id="bntable" />

    <%--分页工具栏--%>
    <div id="bnpage" />

</div>