package me.nabil.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import me.nabil.services.DemoService;

@Controller
@RequestMapping(value="/api/demos")
public class DemoController {
	
	@Autowired
	private DemoService demoService;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("")
	@ResponseBody
	public List<String> getDemo(){
		System.out.println(this);
		
		return demoService.getDemoStrings();
	}
}
