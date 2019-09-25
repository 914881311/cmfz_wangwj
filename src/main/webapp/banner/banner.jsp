<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
         //初始化表单
        $("#bntable").jqGrid({
            url : "${path}/banner/queryByPage",
            editurl:"${path}/banner/edit",
            datatype : "json",
            rowNum : 3,  //每页展示条数   page   rows
            rowList : [ 5,10, 20, 30 ],  //可选展示条数
            styleUI:"Bootstrap",
            pager : '#bnpage',  //分页工具栏
            viewrecords : true,  //是否显示总条数
            autowidth:true,
            height : "auto",
            colNames : [ 'Id', '名字', '图片', '状态', '时间','描述'],
            colModel : [
                {name : 'id',width : 55},
                {name : 'name',editable:true,width : 90},
                {name : 'img_path',editable:true,width : 100,align : "center",edittype:"file",
                    formatter:function (cellValue) {
                        return "<img src='${path}/upload/photo/"+cellValue+"' style='width:100px;height:80px' >";
                     }
                },
                {name : 'status', width : 80,align : "center",
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
                {name : 'up_date',width : 80,align : "right"},
                {name : 'description',editable:true,width : 80,align : "right"},
            ]
        });

        //处理增删改查操作
        $("#bntable").jqGrid('navGrid', '#bnpage', {edit : true,add : true,del : true,addtext:"添加",edittext:"修改",deltext:"删除"},
            {
                //关闭对话框
                closeAfterEdit:true,
                beforeShowForm:function (obj) {
                    obj.find("#img_path").attr("disabled",true)//禁用按钮
                }
            },  //执行修改操作的额外配置
            {
                //关闭对话框
                closeAfterAdd:true,
                afterSubmit:function(data){
                    $.ajaxFileUpload({
                        url:"${path}/banner/bannerUpload",
                        datatype:"json",
                        type:"post",
                        fileElementId:"img_path", // 需要上传的文件域的ID
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
                url:"${path}/banner/updateStatus",
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
                url:"${path}/banner/updateStatus",
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
</script>

<%--初始化面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>轮播图信息</h2>
    </div>

    <ul class="nav nav-tabs" >
        <li class="active"><a href="#">轮播图信息</a></li>
    </ul>

    <%--初始表单--%>
    <table id="bntable" />

    <%--分页工具栏--%>
    <div id="bnpage" />

</div>