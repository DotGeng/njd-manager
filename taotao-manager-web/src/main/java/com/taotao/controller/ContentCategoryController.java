package com.taotao.controller;

import java.util.List;

import org.junit.runners.Parameterized.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

/**
 * 内容分类管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value="id",defaultValue="0") Long parentId) throws Exception {
		List<EUTreeNode> resultList = contentCategoryService.getCategoryList(parentId);
		return resultList;
	}
	/**
	 * 创建节点
	 */
	@RequestMapping("/create")
	@ResponseBody
	public  TaotaoResult createContentCategory(@RequestParam("parentId")Long parentId,String name) throws Exception {
		TaotaoResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}
	/**
	 * 删除节点
	 * 
	 */
	@RequestMapping("/delete")
	@ResponseBody                                                           
	public TaotaoResult deleteContentCategory(Long parentId,Long id) throws Exception {
		TaotaoResult result = contentCategoryService.deleteContentCategory(parentId, id);
		return result;
	}
	
	/**
	 * 重命名节点
	 */
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateContentCategoryNodeName(Long id,String name) throws Exception {
		TaotaoResult result = contentCategoryService.renameNode(id, name);
		return result;
	}
}
