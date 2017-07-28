<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="easyui-datagrid" id="itemParamList" title="商品列表" 
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'item/param/list',method:'get',pageSize:30,toolbar:itemParamListToolbar">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'id',width:60">ID</th>
        	<th data-options="field:'itemCatId',width:80">商品类目ID</th>
        	<th data-options="field:'itemCatName',width:100">商品类目</th>
            <th data-options="field:'paramData',width:300,formatter:formatItemParamData">规格(只显示分组名称)</th>
            <th data-options="field:'created',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
            <th data-options="field:'updated',width:130,align:'center',formatter:TAOTAO.formatDateTime">更新日期</th>
        </tr>
    </thead>
</table>
<div id="itemParamEditWindow" class="easyui-window" title="编辑商品" data-options="modal:true,closed:true,iconCls:'icon-save',href:'item-param-edit'" style="width:80%;height:80%;padding:10px;">
</div>
<script>

	function formatItemParamData(value , index){
		var json = JSON.parse(value);
		var array = [];
		$.each(json,function(i,e){
			array.push(e.group);
		});
		return array.join(",");
	}

    function getSelectionsIds(){
    	var itemList = $("#itemParamList");
    	var sels = itemList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].id);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var itemParamListToolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
        	TAOTAO.createWindow({
        		url : "item-param-add",
        	});
        }
    },{
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','必须选择一个商品才能编辑!');
        		return ;
        	}
        	if(ids.indexOf(',') > 0){
        		$.messager.alert('提示','只能选择一个商品!');
        		return ;
        	}
        	$("#itemParamEditWindow").window({
        		onLoad :function(){
					//回显数据
        			var _data = $("#itemParamList").datagrid("getSelections")[0];
					var _itemCatId = _data.itemCatId;
					var _itemCatName = _data.itemCatName;
					var _paramData = _data.paramData;
					//向item-param-edit页面传递id和name
					$(".itemCatId").html(_itemCatId);
					$(".itemCatName").html(_itemCatName);
					//num用于记录有多少条paramli_children
					var num = 0;
					//得到json字符串并将其转换为json对象
					$("#itemParamsInfo").val(_paramData);
					var paramData = JSON.parse(_paramData);
					//根据json对象动态生成编辑框 和按钮点击事件
					for(var i in paramData) {
						var pd = paramData[i];
						var templeul = $(".itemParamEditTemplate .paramul").clone();
						$(".editGroupTr .param").append(templeul);
						var templeliparent = $(".itemParamEditTemplate .paramli_parent").clone();
						//为paramli_children的删除按钮添加点击事件
						templeliparent.find(".addParam").click(function(){
							var templelichildren = $(".itemParamEditTemplate .paramli_children").clone();
							templelichildren.find(".delParam").click(function(){
								$(this).parent().remove();
							});
							templelichildren.appendTo($(this).parentsUntil("ul").parent());
						});
						$(".editGroupTr .param .paramul").eq(i).append(templeliparent);
						//为group输入框赋值
						$(".editGroupTr .param .paramli_parent [name=group]").eq(i).siblings("input").val(pd.group);
						for(var j in pd.params) {
							var ps = pd.params[j];
							var templelichildren = $(".itemParamEditTemplate .paramli_children").clone();
							//为paramli_children的删除按钮添加点击事件
							templelichildren.find(".delParam").click(function(){
								$(this).parent().remove();
							});
							$(".editGroupTr .param .paramul").eq(i).append(templelichildren);
							//为param输入框赋值
							$(".editGroupTr .param .paramli_children [name=param]").eq(num).siblings("input").val(ps);
							num++;
						}
					}
					$(".editGroupTr").show();
        		}
        	}).window("open");
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中商品规格!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的商品规格吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
                	$.post("/item/param/delete",params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','删除商品规格成功!',undefined,function(){
            					$("#itemParamList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    }];
</script>