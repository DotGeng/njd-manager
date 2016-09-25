package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import com.taotao.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;
	@Override
	public TaotaoResult insertContent(TbContent tbContent) {
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		//把内容添加到数据库中
		contentMapper.insert(tbContent);
		return TaotaoResult.ok();
	}
	@Override
	public EUDataGridResult getContentList(long catId, Integer page,
			Integer rows) throws Exception {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(catId);
		PageHelper.startPage(page,rows);
		List<TbContent> list = contentMapper.selectByExample(example);
		EUDataGridResult dataGridResult = new EUDataGridResult();
		//取分页信息
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		dataGridResult.setTotal(pageInfo.getTotal());
		dataGridResult.setRows(list);
		return dataGridResult;
	}
	@Override
	public TaotaoResult modifyContent(TbContent content) throws Exception {
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKeySelective(content);
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult deleteContent(List<Long> ids) throws Exception {
		for(int i = 0 ; i < ids.size() ; i ++) {
			contentMapper.deleteByPrimaryKey(ids.get(i));
		}
		return TaotaoResult.ok();
	}
}
