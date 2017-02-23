package me.nabil.proxies;

import java.util.ArrayList;
import java.util.List;

public class DemoProxy {
	
	public List<String> getList() {
		return new ArrayList<String>();
	}
}
