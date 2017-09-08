package com.boot.proxyip.service;

import com.boot.proxyip.entity.ProxyIp;

public interface ProxyIpService {

	boolean insert(ProxyIp proxyIp);

	boolean existById(int id);

	boolean existByIpAndPort(String ip, Integer port);

}
