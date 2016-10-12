package com.taotao.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent content) {
		TaotaoResult result = contentService.insertContent(content);
		return result;
	}
	//取内容列表
	@RequestMapping("/query/list")
	@ResponseBody
	public EUDataGridResult getContentList(Long categoryId,Integer page,Integer rows) throws Exception {
		EUDataGridResult dataGridResult =  contentService.getContentList(categoryId, page, rows);
		return dataGridResult;
	}
	//内容的编辑功能
	@RequestMapping("/edit")
	@ResponseBody
	public  TaotaoResult modifyContent(TbContent content) throws Exception {
		return contentService.modifyContent(content);
	}
	//内容的删除功能
	@RequestMapping("/delete") 
	@ResponseBody
	public TaotaoResult deleteContent(Long[] ids) throws Exception {
		List<Long> idsArr = new ArrayList<>();
		for(int i = 0 ; i < ids.length; i ++) {
			idsArr.add(ids[i]);
		}
		return contentService.deleteContent(idsArr);
	}
}
