package com.flouis.util;

import com.flouis.vo.PageVo;
import com.github.pagehelper.Page;

import java.util.List;

public class PageUtil {

	private PageUtil(){}

	public static PageVo getPageVo(List list){
		PageVo result = new PageVo();
		if (list instanceof Page){
			Page page = (Page) list;
			result.setTotal(page.getTotal());
			result.setPages(page.getPages());
			result.setPage(page.getPageNum());
			result.setSize(page.getPageSize());
			result.setCurPageSize(page.size());
			result.setRows(page.getResult());
		}
		return result;
	}

}
