package com.tuniu.extension;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;



public class RegexSqlSessionFactoryBean extends SqlSessionFactoryBean {
	Logger logger=LoggerFactory.getLogger(getClass());
	private static final String ROOT_PATH="com"+File.separator+"tuniu"+File.separator;//com\tuniu\
	private static final String ROOT_PATH_SPLIT = ",";
	private static final String[] PATH_REPLACE_ARRAY={"]"};
	
	@Override
	public void setTypeAliasesPackage(String typeAliasesPackage) {
		if(StringUtils.isEmpty(typeAliasesPackage)){
			super.setTypeAliasesPackage(typeAliasesPackage);
			return;
		}
		ResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
		StringBuffer buffer=new StringBuffer();
		try{
			for(String location : typeAliasesPackage.split(",")){
				if(StringUtils.isEmpty(location)){
					continue;
				}
				location = "classpath*:" + location.trim().replace(".", File.separator);
				buffer.append(getResources(resolver,location));
			}
		}catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		if("".equals(buffer.toString())){
			throw new RuntimeException("别名路径扫描错误!");
		}
		typeAliasesPackage = replaceResult(buffer.toString()).replace(File.separator, ".");
		logger.info(typeAliasesPackage);
		super.setTypeAliasesPackage(typeAliasesPackage);
		
	}

	private String replaceResult(String string) {
		for(String replace : PATH_REPLACE_ARRAY){
			string = string.replace(replace, "");
		}
		return string;
	}

	private String getResources(ResourcePatternResolver resolver, String location)throws IOException {
		StringBuffer buff=new StringBuffer();		
		for(Resource resource : resolver.getResources(location)){
			String description = resource==null?"":resource.getDescription();
			if(StringUtils.isEmpty(resource.getDescription())||description.indexOf(ROOT_PATH)==-1){
				continue;
			}
			buff.append(description.substring(description.indexOf(ROOT_PATH))).append(ROOT_PATH_SPLIT);
		}
		
		return buff.toString();
	}
}
