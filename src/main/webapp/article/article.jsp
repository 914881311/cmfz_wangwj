<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%--页面添加以下脚本--%>
<script charset="utf-8" src="${path}/kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="${path}/kindeditor/lang/zh-CN.js"></script>
<script>
    KindEditor.create('#editor_id',{
            uploadJson:"${path}/editor/uploadEditor",   //指定上传图片的路径
            filePostName:"photo",   //设置上传图片的名称
            allowFileManager:true, //是否展示浏览远程图片按钮
            fileManagerJson:"${path}/editor/queryPhotos", //指定浏览远程图片的路径
            afterBlur:function(){   //在kindeditor失去焦点之后执行的内容
                this.sync();  //将kindeditor中的内容同步到表单中
            }
        }
    );
</script>
<script>
    $(function () {

        //初始化表单
        $("#atctable").jqGrid({
            url : "${path}/article/queryByPage",
            editurl:"${path}/article/deleteArticle",
            datatype : "json",
            rowNum : 2,  //每页展示条数   page   rows
            rowList : [ 2,3,10, 20, 30 ],  //可选展示条数
            styleUI:"Bootstrap",
            pager : '#atcpage',  //分页工具栏
            viewrecords : true,  //是否显示总条数
            autowidth:true,
            height : "auto",
            colNames : [ 'Id', '名字', '作者', '内容', '上传时间','所属上师'],
            colModel : [
                {name : 'id',width : 55},
                {name : 'title',editable:true,width : 90,align : "center"},
                {name : 'author',editable:true,width : 100,align : "center"},
                {name : 'content',width : 80,align : "center"},
                {name : 'crea_date',editable:true,width : 80,align : "center"},
                {name : 'guru_id',width : 80,align : "center"}
            ]
        });

        //处理增删改查操作
        $("#atctable").jqGrid('navGrid', '#atcpage',
            {edit : false,add : false,del : true,search:false,deltext:"删除"}
        );

        /*展示文章信息*/
        $("#btn1").click(function(){

            //只读属性，最后选择行的id
            var rowId = $("#atctable").jqGrid("getGridParam","selrow");

            //判断是否选中一行
            if(rowId!=null){

                //根据行Id获取行数据
                var row= $("#atctable").jqGrid("getRowData",rowId);

                //给  title input框设置数据
                $("#title").val(row.title);

                //给  author input框设置数据
                $("#author").val(row.author);

                //给富文本编辑器设置内容
                KindEditor.html("#editor_id",row.content);

                //展示模态框
                $("#MyModals").modal("show");

                /*设置按钮*/
                $("#MyFooter").html("<button class='btn btn-primary' onclick='updateArticle(\""+rowId+"\")' >提交</button>" +
                    "<button class='btn btn-primary' data-dismiss='modal'>关闭</button>");

            }else{
                alert("请选择一行");
            }
        })

        /*添加文章*/
        $("#btn2").click(function(){

            //清空表单
            $("#MyForm")[0].reset();

            //清空kindeditor
            KindEditor.html("#editor_id","");

            //展示模态框
            $("#MyModals").modal("show");

            /*设置按钮*/
            $("#MyFooter").html("<button class='btn btn-primary' onclick='addArticle()' >提交</button>" +
                "<button class='btn btn-primary' data-dismiss='modal'>关闭</button>");
        });

    });

    //点击添加按钮 添加文章
    function addArticle(){
        $.ajax({
            url:"${path}/article/addArticle",
            type:"post",
            dataType:"json",
            data:$("#MyForm").serialize(),
            success:function(){

                //关闭模态框
                $("#MyModals").modal('hide');

                //刷新页面
                $("#atctable").trigger("reloadGrid");
            }
        });
    }

    //点击展示详情 的提交按钮   修改文章信息
    function updateArticle(rowId){
        $.ajax({
            url:"${path}/article/updateArticle?id="+rowId,
            type:"post",
            dataType:"json",
            data:$("#MyForm").serialize(),
            success:function(){

                //关闭模态框
                $("#MyModals").modal('hide');

                //刷新页面
                $("#atctable").trigger("reloadGrid");
            }
        });
    }
</script>

<%--初始化面板--%>
<div class="panel panel-danger">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>文章信息</h2>
    </div>

    <ul class="nav nav-tabs" >
        <li class="active"><a href="#">文章信息</a></li>
    </ul>
        <div class="panel panel-body">
            <button id="btn1" class="btn btn-info">文章信息</button>&emsp;
            <button id="btn2" class="btn btn-info">添加文章</button>&emsp;
            <button id="btn3" class="btn btn-info">删除文章</button>
        </div>


    <%--初始表单--%>
    <table id="atctable" />

    <%--分页工具栏--%>
    <div id="atcpage" />

    <%--初始化模态框--%>
    <div id="MyModals" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content" style="width:730px;">

                <%--模态框标题--%>
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="gridSystemModalLabel">文章信息</h4>
                </div>

                <%--模态框内容--%>
                <div class="modal-body" align="center">
                    <%--放一个表单--%>
                    <form id="MyForm" class="form-horizontal">
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-addon1">名字</span>
                            <input id="title" name="title" type="text" class="form-control" aria-describedby="basic-addon1" style="width: 200px">
                        </div></br></br>

                        <div class="input-group">
                            <span class="input-group-addon" id="basic-addon2">作者</span>
                            <input id="author" name="author" type="text" class="form-control" aria-describedby="basic-addon1" style="width: 200px">
                        </div></br></br>
                        <%--kindeditor输入框--%>
                        <div align="center" class="input-group">
                            <%--在需要显示编辑器的位置添加textarea输入框--%>
                            <textarea id="editor_id" name="content" style="width:700px;height:300px;"></textarea>
                        </div>
                    </form>
                </div>

                <%--模态框按钮--%>
                <div class="modal-footer" id="MyFooter">
                    <%--<button type="button" class="btn btn-default">提交</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>--%>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</div>