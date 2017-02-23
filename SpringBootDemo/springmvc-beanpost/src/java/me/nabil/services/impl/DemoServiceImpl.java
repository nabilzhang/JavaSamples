package me.nabil.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import me.nabil.proxies.DemoProxy;
import me.nabil.proxies.post.DemoPost;
import me.nabil.services.DemoService;

@Service
public class DemoServiceImpl implements DemoService {

	@DemoPost
	private DemoProxy demoProxy;

	@Override
	public List<String> getDemoStrings() {
		System.out.println(this);
		return demoProxy.getList();
	}

}
