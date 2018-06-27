<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>bootStartTest</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/jquery.form/3.51/jquery.form.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</head>
<body>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/trace/requests">调用链</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <form id="form1" class="navbar-form navbar-left" role="search">
                <span>时间过滤:</span>
                <select name="afterSeconds" class="form-control" style="width: 100px;">
                    <option value="259200">36小时内</option>
                    <option value="3600" selected="selected" >1小时内</option>
                    <option value="1800">30分钟内</option>
                    <option value="60">1分钟内</option>
                    <option value="30">30秒内</option>
                    <option value="10">10秒内</option>
                </select>
                <span>&nbsp;&nbsp;IP过滤:</span>
                <input name="clientIps" value='${clientIps!""}'type="text" class="form-control" placeholder="ip过滤,多个用号逗隔开" />
                <span>&nbsp;&nbsp;关键字过滤:</span>
                <input type="text" name="queryWord" class="form-control" placeholder="关键字过滤"/>
                <#--TODO 未作项目登录所已先写了一个固定的key -->
                <input type="hidden" name="projectKey" value="b71e2da2f7b74cba94ad008403ba594f"/>

                <button type="submit" class="btn btn-success">&nbsp;&nbsp;刷新&nbsp;&nbsp;</button>
            </form>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

    <div id="requestContent" style="margin-left:14px;">

    </div>
</body>

<script type="text/javascript">
    $(function(){
        refreshData();
    });

    $("#form1").submit(function(e){
        refreshData();
        return false;
    });

    function refreshData(){

        var options = {
            target:'#requestContent', //后台将把传递过来的值赋给该元素
            url:'${request.contextPath}/trace/requestTableContent', //提交给哪个执行
            type:'GET',
            success: function( text ){
            }
        };
        $('#form1').ajaxSubmit(options);
    }
     function refreshDataByParam(param){
     //alert(param);
     	var url_text="${request.contextPath}/trace/requestTableContent?"+param;
     	htmlobj=$.ajax({url:url_text,async:false});
  		$("#requestContent").html(htmlobj.responseText);
     }
</script>


</html>