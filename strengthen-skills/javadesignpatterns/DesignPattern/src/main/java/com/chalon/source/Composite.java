package com.chalon.source;

import java.util.HashMap;
import java.util.Map;

public class Composite {
	public static void main(String[] args) {
		Map<Integer, String> hashMap = new HashMap<Integer, String>();
		hashMap.put(0, "东游记");

		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "西游记");
		map.put(2, "红楼梦");

		hashMap.putAll(map);
		System.out.println(hashMap);

	}

}
