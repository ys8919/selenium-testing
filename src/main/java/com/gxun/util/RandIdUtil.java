package com.gxun.util;

import java.util.Random;
import java.util.UUID;

public class RandIdUtil {
	private static final long WEB_LOAD_LENGTH = 7;
	public static String rangId() {
		Long id=(long)Math.abs(UUID.randomUUID().toString().replace("-", "").substring(0,10).hashCode());
		return id.toString();
	}
	public static String randomWebScriptId() {
		Long id=(long)Math.abs(UUID.randomUUID().toString().replace("-", "").substring(0,12).hashCode());
		return id.toString();
	}
	public static String randomScriptId() {
		return UUID.randomUUID().toString().replace("-", "").toString().substring(0,11);
	}

	public static String randomWebLoadId() {
		long min = 1, max = 12;
		for (int i = 1; i < WEB_LOAD_LENGTH; i++) {
			min *= 10;
			max *= 10;
		}
		long rangeLong = (((long) (new Random().nextDouble() * (max - min)))) + min;
		return TimeUtil.nowDateById() + String.valueOf(rangeLong);
	}
}
