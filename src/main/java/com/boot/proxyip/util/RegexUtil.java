package com.boot.proxyip.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	/**
	 * 获取第一个匹配项
	 * 
	 * @param str
	 *            被匹配的字符串
	 * @param regex
	 *            正则
	 * @return 第一个匹配项
	 */
	public static String matchFirstString(String str, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(matchFirstString("123123tag=asd&", "tag=(.*?)&"));
	}

}
