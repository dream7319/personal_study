package com.springboot.mybatis.service.impl;

import com.springboot.mybatis.dao.Spring4allMapper;
import com.springboot.mybatis.entity.Spring4all;
import com.springboot.mybatis.service.Spring4AllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-06-26 10:55
 **/
@Service
public class Spring4AllServiceImpl implements Spring4AllService {

	@Autowired
	private Spring4allMapper spring4allMapper;

	@Override
	public List<Spring4all> getAll() {
		return spring4allMapper.selectAll();
	}
}
