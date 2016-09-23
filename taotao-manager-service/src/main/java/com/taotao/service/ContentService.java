package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	//插入内容
	public TaotaoResult insertContent(TbContent tbContent) ;
	//得到内容列表
	public EUDataGridResult  getContentList(long catId, Integer page, Integer rows) throws Exception;
}
