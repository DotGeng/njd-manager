package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList  = new ArrayList<EUTreeNode>();
		for (TbContentCategory tbContentCategory : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed" : "open");
			resultList.add(node);
		}
		return resultList;
	}
	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		//status：1为正常 2 为删除；
		tbContentCategory.setStatus(1);
		tbContentCategory.setIsParent(false);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		//插入商品分类信息
		contentCategoryMapper.insert(tbContentCategory);
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentCat.getIsParent()) {
			//如果不是父类别，则把它修改为父类别
			parentCat.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		//返回结果集(其中包含id属性)
		return TaotaoResult.ok(tbContentCategory);
	}
	@Override
	public TaotaoResult deleteContentCategory(long parentId, long id) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		contentCategoryMapper.deleteByExample(example);
		TbContentCategoryExample example2 = new TbContentCategoryExample();
		Criteria criteria2 = example2.createCriteria();
		criteria2.andParentIdEqualTo(parentId);
		List<TbContentCategory> categories = contentCategoryMapper.selectByExample(example2);
		if(categories == null || categories.size() == 0) {
			TbContentCategory category =  new TbContentCategory();
			category.setId(parentId);
			category.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKeySelective(category);
		}
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult renameNode(long id, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setId(id);
		contentCategory.setName(name);
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		return TaotaoResult.ok();
	}
}
