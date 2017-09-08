package com.boot.proxyip.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.proxyip.entity.ProxyIp;
import com.boot.proxyip.mapper.ProxyIpMapper;
import com.boot.proxyip.service.ProxyIpService;

@Service
public class ProxyIpServiceImpl implements ProxyIpService {
	@Autowired
	private ProxyIpMapper proxyIpMapper;

	@Override
	public boolean insert(ProxyIp proxyIp) {
		return proxyIpMapper.insert(proxyIp) > 0;
	}

	@Override
	public boolean existById(int id) {
		ProxyIp proxyIp = proxyIpMapper.selectByPrimaryKey(id);
		return proxyIp == null;
	}

	@Override
	public boolean existByIpAndPort(String ip, Integer port) {
		ProxyIp proxyIp = proxyIpMapper.queryByIpAndPort(ip, port);
		return proxyIp != null;
	}

}
