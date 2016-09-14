package com.taotao.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.util.FTPUtil;
import com.taotao.common.util.IDUtils;
import com.taotao.service.PictureService;
@Service
public class PcitureServiceImpl implements PictureService {
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PROT}")
	private int FTP_PROT;
	@Value("${FTP_BASEPATH}")
	private String FTP_BASEPATH;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${IMAGE_BATH_PATH}")
	private String IMAGE_BATH_PATH;
	
	@Override
	public Map uploadPicture(MultipartFile uploadFile) {
		Map resultMap = new HashMap();
		try {
			//得到图片原来的名字；
			String oldName = uploadFile.getOriginalFilename();
			//生成图片新的名字，新名字之间不能重复；
			StringBuffer newNameTmp = new StringBuffer(IDUtils.genImageName());
			newNameTmp.append(oldName.substring(oldName.lastIndexOf(".")));
			String newName = newNameTmp.toString();
			//图片上传
			String imagePath = new DateTime().toString("/yyyy/MM/dd");
			Boolean bool = FTPUtil.uploadFile(FTP_ADDRESS, FTP_PROT, FTP_USERNAME, FTP_PASSWORD, 
					FTP_BASEPATH, imagePath,newName, uploadFile.getInputStream());
			if(!bool) {
				resultMap.put("error",1);
				resultMap.put("message","文件上传失败");
				return resultMap;
			}
			if(bool) {
				resultMap.put("error",0);
				resultMap.put("url", IMAGE_BATH_PATH +imagePath +"/"+newName);
				return resultMap;
			}
		} catch (IOException e) {
			resultMap.put("error",1);
			resultMap.put("message","文件上传发生异常");
			return resultMap;
		}
		return null;
	}

}
