package com.boot.proxyip.parser;

import java.util.List;

public interface Parser<T> {
	/**
	 * 解析单个实体
	 * 
	 * @param url
	 *            url链接
	 * @return
	 */
	T parse(String url);

	/**
	 * 解析实体列表
	 * 
	 * @param url
	 *            url链接
	 * @return
	 */

	List<T> parseForList(String url);

}
