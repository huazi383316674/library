[#ftl]

[#-- pageId curPage sortable headIndex  scheme fixPageSize--]
[#macro bar pageId curPage extra...]
	<script>
	if(pages["${pageId}"]==null){
		pages["${pageId}"]=new Object();
	}
	
	pages["${pageId}"].id="${pageId}";
	pages["${pageId}"].action="${requestURI}";
	pages["${pageId}"].target="${extra['target']!""}";
	pages["${pageId}"].params=new Object();
	[#list Parameters?keys as key]
	pages["${pageId}"].params["${key}"]="${Parameters[key]?js_string}";
	[/#list]
	[#if extra['sortable']!false]
	initSortTable('${pageId}',${extra['headIndex']!(0)},"${Parameters['orderBy']!('null')}");
	[/#if]
	</script>
	[#if extra['fixPageSize']?? && (extra['fixPageSize']=='1' || extra['fixPageSize']=='true')]
		[#assign fixPageSize=true]
	[#else]
		[#assign fixPageSize=false]
	[/#if]
	[#if pagechecker.isPage(curPage)]
		[#local pageBaseTemplate]/template/${extra['scheme']!"xhtml"}/pageBar.ftl[/#local]
		[#include pageBaseTemplate/]
	[/#if]
[/#macro]