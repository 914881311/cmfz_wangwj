<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function(){
        $("#abtable").jqGrid({
            url : '${path}/album/showAllByPage',
            editurl:"${path}/album/edit",
            datatype : "json",
            autowidth:true,
            styleUI:"Bootstrap",
            height : "auto",
            rowNum : 5,
            rowList : [ 8, 10, 20, 30 ],
            pager : '#abpage',
            viewrecords : true,
            colNames : [ 'ID', '名称', '封面', '作者', '评分','播音', '集数',"内容","发布时间" ],
            colModel : [
                {name : 'id',   width : 55},
                {name : 'title',editable:true,width : 90},
                {name : 'cover',editable:true,width : 100,align : "center",edittype:"file",
                    formatter:function (cellValue) {
                        return "<img src='${path}/upload/photo/"+cellValue+"' style='width:100px;height:80px' >";
                    }
                },
                {name : 'author',editable:true,width : 80,align : "right"},
                {name : 'score',editable:true,width : 80,align : "right"},
                {name : 'broadcast',editable:true,width : 80,align : "right"},
                {name : 'counts',editable:true,width : 80,align : "right"},
                {name : 'content',editable:true,width : 80,align : "right"},
                {name : 'crea_date',width : 150,sortable : true}
            ],
            subGrid : true,  //是否开启子表格  // subgrid_id是在表数据中创建的div标签的id
            subGridRowExpanded : function(subgrid_id,row_id) {  //subgrid_id  子表格id  row_id 行id
                //开启子表格
                addSubGrid(subgrid_id,row_id);
            }
        });
        //处理增删改方法
        $("#abtable").jqGrid('navGrid', '#abpage', {add : true,edit : true,del : true},
            {
                //关闭对话框
                closeAfterEdit:true,
                beforeShowForm:function (obj) {
                    obj.find("#cover").attr("disabled",true)//禁用按钮
                }
            },//执行修改操作的额外配置
            {
                //关闭对话框
                closeAfterAdd:true,
                afterSubmit:function(data){
                    $.ajaxFileUpload({
                        url:"${path}/album/albumUpload",
                        datatype:"json",
                        type:"post",
                        fileElementId:"cover", // 需要上传的文件域的ID
                        data:{id:data.responseText},
                        success:function(){
                            //刷新页面
                            $("#abtable").trigger("reloadGrid");
                        }
                    });
                    return "aaaaa";
                }
            },//执行添加操作的额外配置
            {}
        );
    });

    //子表格
    function addSubGrid(subgridId,rowId){

        //tableId
        var subgridTableId = subgridId+"table";

        //工具栏Id
        var pagerId = subgridId+"page";

        //在子表格中创建一个表单table，创建一个工具栏div
        $("#"+subgridId).html("" +
            "<table id='"+subgridTableId+"'/>" +
            "<div id='"+pagerId+"' />"
        );

        //初始子表格
        $("#" + subgridTableId).jqGrid({
            url : "${path}/chapter/queryByPage?albumId="+rowId,
            editurl:"${path}/chapter/edit?albumId="+rowId,
            datatype : "json",
            rowNum : 20,
            pager : "#"+pagerId,
            autowidth:true,
            styleUI:"Bootstrap",
            height : "auto",
            colNames : [ 'Id', '名称', '大小','时长','上传时间','专辑Id','操作'],
            colModel : [
                {name : "id",  index : "num",width : 80,key : true},
                {name : "url",index : "qty",width : 70,align : "right",edittype:"file",editable:true},
                {name : "size",index : "unit",width : 70,align : "right"},
                {name : "duration",index : "total",width : 70},
                {name : "up_date",index : "total",width : 70},
                {name : "album_id",index : "total",width : 70},
                {name : "url",index : "total",width : 70,
                    formatter:function(value){
                        return "<a href='#' onclick='play(\""+value+"\")'><span class='glyphicon glyphicon-play-circle'/></a> &emsp; &nbsp; " +
                            "<a href='#' onclick='downloads(\""+value+"\")'><span class='glyphicon glyphicon-download'/></a> ";
                    }
                }
            ]
        });

        //增删该方法
        $("#" + subgridTableId).jqGrid('navGrid',"#" + pagerId,
            {edit : true,add : true,del : true},
            {},
            {
                closeAfterAdd:true,
                afterSubmit:function(data){
                    $.ajaxFileUpload({
                        url:"${path}/chapter/uploadChapter",
                        type:"post",
                        datatype:"json",
                        fileElementId:"url",
                        data:{id:data.responseText},
                        success:function(){
                            //刷新页面
                            $("#"+subgridTableId).trigger("reloadGrid");
                        }
                    })

                    return "hello";
                }
            }
        );
    }

    //在线播放
    function play(name){
        //alert("播放"+name);
        //展示模态框
        $("#myModal").modal("show");
        $("#myAudio").attr("src","${path}/upload/audio/"+name);

    }

    //下载
    function downloads(name){
        //alert("下载"+name);

        //向后台发请求  文件下载
        location.href="${path}/chapter/downloadChapter?fileName="+name;
    }


</script>

<%--初始化面板--%>
<div class="panel panel-info">

    <%--初始化面板--%>
    <div class="panel panel-info">

        <%--面板头--%>
        <div class="panel panel-heading">
            <h2>专辑信息</h2>
        </div>

        <ul class="nav nav-tabs" >
            <li class="active"><a href="#">专辑信息</a></li>
        </ul>

        <%--初始表单--%>
        <table id="abtable" />

        <%--分页工具栏--%>
        <div id="abpage" />

        <%--点击播放模态框--%>
        <div id="myModal" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <audio id="myAudio" controls/>
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
</div>