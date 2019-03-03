package com.sunrise.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.sunrise.engine.GlobalServiceEngine;

/**
 * Created on January, 2018
 *
 * @author adilcan
 */
@Controller
public class HomeController {

	@Inject
	private GlobalServiceEngine globalServiceEngine;
	
	@GetMapping("")
	public String getHome(){
		System.out.println("Engine: " + globalServiceEngine.getEngine());
		return "pages/public/dashboard/master";//"index";
	}
}
