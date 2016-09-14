package com.taotao.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {
	public TaotaoResult getItemParamByCid(Long cid);
	public TaotaoResult insertItemParam(TbItemParam itemParam);
	
}
