<%@page contentType="text/html; charset=UTF-8"%>

<html>
<head>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	//alert("jquery load success!");
	//jquery的ajax请求
	//$.get("请求url ",回调函数);
	$.get('tbitem/findpages?page=1&rows=20',function(data){
		alert(typeof data);
		var x=eval('('+data+')')
	});
});
</script>
<title>Insert title here</title>
</head>
<body>
<h1>test</h1>
</body>
</html>