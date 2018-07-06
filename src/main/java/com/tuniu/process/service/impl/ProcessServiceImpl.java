package com.tuniu.process.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.process.service.ProcessService;
@Service
public class ProcessServiceImpl implements ProcessService {

	@Autowired
	private RepositoryService resService;
	
	@Override
	public List<ProcessDefinition> processDefinitionList() {
		return resService.createProcessDefinitionQuery()//
				.orderByProcessDefinitionVersion().desc().list();
	}

	@Override
	public List<Deployment> deploymentList() {
		return resService.createDeploymentQuery()//
				.orderByDeploymenTime().desc().list();
	}

	@Override
	public Deployment deployment(ZipInputStream zip, String name) {
		return resService.createDeployment()//
				.name(name).addZipInputStream(zip).deploy();
	}

	@Override
	public String getDiagramResourceName(String deployId) {
		return resService.createProcessDefinitionQuery()//
				.deploymentId(deployId).singleResult().getDiagramResourceName();
	}

	@Override
	public InputStream getResourceAsStream(String deployId, String resourceName) {
		return resService.getResourceAsStream(deployId, resourceName);
	}

	@Override
	public void deleteProcessByDeployId(String deployId) {
		resService.deleteDeployment(deployId, true);
	}

}
