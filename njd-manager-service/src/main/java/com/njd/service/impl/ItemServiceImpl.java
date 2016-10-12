package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.util.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	@Override
	public TbItem getItemById(long itemId) {
		
		TbItemExample ibItemExample = new TbItemExample();
		//添加查询条件
		Criteria criteria = ibItemExample.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(ibItemExample);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		//查询商品列表
		TbItemExample example = new TbItemExample();
		//分页
		PageHelper.startPage(page,rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	/**
	 * 添加商品
	 */
	@Override
	public TaotaoResult addItem(TbItem item,String desc,String itemParams) throws Exception {
		//接受传过来的pojo，传过来的pojo是不完整的，这时候我们就要在这里进行补全；
		Long itmeId = IDUtils.genItemId();
		item.setId(itmeId);
		item.setStatus((byte)1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		//把item插入到数据库中
		itemMapper.insert(item);
		TaotaoResult result = this.addItemDesc(itmeId,desc,date);
		if(result.getStatus() != 200) {
			throw new Exception();
		}
		//添加商品规则参数
		result = this.insertItemParamItem(itmeId, itemParams);
		if(result.getStatus() != 200 ) {
			throw new Exception();
		}
		return TaotaoResult.ok();
	}
	/**
	 * 添加商品描述
	 */
	@Override
	public TaotaoResult addItemDesc(Long itemId, String desc,Date date) {
		TbItemDesc itemDesc  = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		int bool = itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult insertItemParamItem(Long itemId, String ItemParam) {
		//创建pojo,并补全
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(ItemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		//插入数据
		tbItemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
	}
}
