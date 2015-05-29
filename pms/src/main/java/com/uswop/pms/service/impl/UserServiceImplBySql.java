package com.uswop.pms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uswop.pms.commons.SystemConstants;
import com.uswop.pms.dao.UserAddrDao;
import com.uswop.pms.dao.UserDao;
import com.uswop.pms.dto.Tuser;
import com.uswop.pms.dto.TuserAddr;
import com.uswop.pms.service.UserService;

@Service
public class UserServiceImplBySql implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserAddrDao userAddrDao;

	@Override
	public Tuser getUser(String userId) {		
		Tuser user = userDao.findUniqueByHqlName(SystemConstants.SQL_NAME_QUERY_FIND_USER,userId);		
		return user;
	}

	@Override
	public Tuser validateUser(String userId, String pwd) {		
		String[] value=new String[]{userId,pwd};
		//return userDao.findUniqueByHqlName(SystemConstants.SQL_NAME_QUERY_VALIDATE_USER, new String[]{"ID","PWD"}, value);
		return userDao.findUniqueByHqlName(SystemConstants.SQL_NAME_QUERY_VALIDATE_USER, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TuserAddr> getUserAddrs(String userId) {
		return userAddrDao.findByHqlName(SystemConstants.SQL_NAME_QUERY_FIND_USERADDR, new String[]{userId});		
	}
}
