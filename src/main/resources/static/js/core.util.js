/* CoreUtil：工具类，类似java静态工具类 */

/**
 * 参数说明：
 * url：要请求的接口地址
 * params：传递给后端的数据
 * ft：请求响应成功回掉方法(function)
 * method：请求的方式例如 GET/POST/PUT/DELETED 等
 * async：是否是异步请求；async的默认方式是true,即异步方式；async设置为false时,为同步方式
 * contentType：类型编码；默认为：application/json; charset=UTF-8
 */
var CoreUtil = (function () {
	var coreUtil = {};
	// ajax请求
	coreUtil.sendAjax = function (url, params, ft, method, async, contentType) {
		var roleSaveLoading = top.layer.msg('请稍候', {icon: 16, time:false, shade:0.8});
		layui.jquery.ajax({
			url: url,
			cache: false,
			async: async == undefined ? true : async,
			data: params,
			type: method == undefined ? "POST" : method,
			contentType: contentType == undefined ? 'application/json; charset=UTF-8': contentType,
			dataType: "json",
			success: function (res) {
				top.layer.close(roleSaveLoading);
				if (typeof ft == "function") {
					if (res.code == 200000) {
						if(ft != null && ft != undefined){
							ft(res);
						}
					} else {
						layer.msg(res.msg)
					}
				}
			},
			error: function (XMLHttpRequest) {
				top.layer.close(roleSaveLoading);
				if (XMLHttpRequest.status==404){
					top.window.location.href="/index/404";
				} else {
					layer.msg("服务器好像除了点问题！请稍后试试");
				}
			}
		});
	};

	/*存入本地缓存*/
	coreUtil.setData = function(key, value){
		layui.sessionData('LocalData',{
			key :key,
			value: value
		});
	};

	/*从本地缓存拿数据*/
	coreUtil.getData = function(key){
		var localData = layui.sessionData('LocalData');
		return localData[key];
	};


	return coreUtil;
})(CoreUtil, window);