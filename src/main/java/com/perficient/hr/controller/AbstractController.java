package com.perficient.hr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.perficient.hr.utils.PerfProperties;

public abstract class AbstractController {
	
	protected Logger logger = LoggerFactory.getLogger(AbstractController.class);
	
	@Autowired
	public PerfProperties perfProperties;
	
}
