[#ftl/]
[@b.form action="!bulkSave" ]
	[@b.grid width="95%" items=propertyConfigs var="config"]
		[@b.row]
		 [@b.col title="序号" width="7%"]${config_index+1}[/@]
		 [@b.col title="参数名称" width="20%" style="text-align:left"]<input name="config${config.id}.name"  value="${config.name}" style="width:97%"/>[/@]
		 [@b.col title="类型" width="15%"]<input name="config${config.id}.type" value="${config.type!}" style="width:97%"/>[/@]
		 [@b.col title="参数值" width="35%"]<input name="config${config.id}.value" value="${config.value?default("")}" maxlength="300" style="width:97%"/>[/@]
		 [@b.col title="说明" width="15%"]<input name="config${config.id}.description" value="${config.description!}" style="width:97%"/>[/@]
		 [@b.col title="删除" width="8%"]<button onclick="remove(${config.id})">删除</button>[/@]
		[/@]
	[/@]
[#if propertyConfigs?size>0]<div align="center"><br/><br/>[@b.submit value="保存"/]</div>[/#if]
[/@]