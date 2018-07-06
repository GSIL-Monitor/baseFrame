package com.tuniu.process.service;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

public interface ProcessService {
	
	List<ProcessDefinition> processDefinitionList();
	List<Deployment> deploymentList();
	Deployment deployment(ZipInputStream zip,String name);
	String getDiagramResourceName(String deployId);
	InputStream getResourceAsStream(String deployId,String resourceName);
	void deleteProcessByDeployId(String deployId);

}
