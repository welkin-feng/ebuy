<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<table cellpadding="5" style="margin-left: 30px" id="itemParamEditTable" class="itemParam">
	<tr>
		<td>
			<span>商品类目ID：</span>
			<span class="itemCatId"></span>
		</td>
	</tr>
	<tr>
		<td>
			<span>商品类目名称：</span>
			<span class="itemCatName"></span>
		</td>
	</tr>
	<tr class="hide editGroupTr">
		<td>规格参数:</td>
		<td>
			<ul>
				<li><a href="javascript:void(0)" class="easyui-linkbutton addGroup">添加分组</a></li>
				<li><a href="javascript:void(0)" class="easyui-linkbutton removeAllGroup">删除全部分组</a></li>
				<li class="param">
					
				</li>
			</ul>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<a href="javascript:void(0)" class="easyui-linkbutton submit">提交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton close">关闭</a>
		</td>
	</tr>
</table>
<input type="hidden" id="itemParamsInfo" name="itemParamsInfo"/>
<div  class="itemParamEditTemplate" style="display: none;">
	<ul class="paramul">	
	</ul>
	
	<li class="paramli_parent">
		<input class="easyui-textbox" style="width: 150px;" name="group"/>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton addParam"  title="添加参数" data-options="plain:true,iconCls:'icon-add'"></a>
	</li>
	
	<li class="paramli_children">
		<span>|-------</span><input  style="width: 150px;" class="easyui-textbox" name="param"/>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton delParam" title="删除" data-options="plain:true,iconCls:'icon-cancel'"></a>						
	</li>
</div>
<script style="text/javascript">
	$(function(){
		$(".addGroup").click(function(){
			//$.messager.alert("提示", "hello");
			var templeul = $(".itemParamEditTemplate .paramul").clone();
			var templeliparent = $(".itemParamEditTemplate .paramli_parent").clone();
			var templelichildren = $(".itemParamEditTemplate .paramli_children").clone();
			
			templeliparent.find(".addParam").click(function(){
				var templelichildren = $(".itemParamEditTemplate .paramli_children").clone();
				templelichildren.find(".delParam").click(function(){
					$(this).parent().remove();
				});
				templelichildren.appendTo($(this).parentsUntil("ul").parent());
			});
			templelichildren.find(".delParam").click(function(){
				$(this).parent().remove();
			});
			
			templeul.append(templeliparent);
			templeul.append(templelichildren);
			$(".param").append(templeul);
		});
		
		$(".removeAllGroup").click(function(){
			$.messager.confirm('确认', '确定清除所有规格信息吗？', function(r){
				if (r){
					$("#itemParamEditTable .param").empty();
				}
				else {
				}
			});
		});
		
		$("#itemParamEditTable .close").click(function(){
			$(".panel-tool-close").click();
		});
		
		$("#itemParamEditTable .submit").click(function(){
			var params = [];
			var groups = $("#itemParamEditTable [name=group]");
			groups.each(function(i,e){
				var p = $(e).parentsUntil("ul").parent().find("[name=param]");
				var _ps = [];
				p.each(function(_i,_e){
					var _val = $(_e).siblings("input").val();
					if($.trim(_val).length>0){
						_ps.push(_val);						
					}
				});
				var _val = $(e).siblings("input").val();
				if($.trim(_val).length>0 && _ps.length > 0){
					params.push({
						"group":_val,
						"params":_ps
					});					
				}
			});
			var url = "item/param/update/"+$("#itemParamEditTable .itemCatId").text();
			$.post(url,{"paramData":JSON.stringify(params)},function(data){
				alert(data.status);
				if(data.status == 200){
					$.messager.alert('提示','修改商品规格成功!',undefined,function(){
						$(".panel-tool-close").click();
    					$("#itemParamList").datagrid("reload");
    				});
				}
				else {
					$.messager.alert('错误','修改商品规格失败!');
				}
			});
		});
	});
</script>