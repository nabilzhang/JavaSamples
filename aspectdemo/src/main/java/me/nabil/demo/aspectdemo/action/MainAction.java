package me.nabil.demo.aspectdemo.action;

import com.opensymphony.xwork2.ActionSupport;

public class MainAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4663811679338038451L;

	public String list(){
		System.out.println("home list");
		return SUCCESS;
	}
}
