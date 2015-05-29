package com.uswop.pms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uswop.pms.dao.UserAddrDao;
import com.uswop.pms.dao.UserDao;
import com.uswop.pms.dto.Tuser;
import com.uswop.pms.dto.TuserAddr;
import com.uswop.pms.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserAddrDao userAddrDao;

	@Override
	public Tuser getUser(String userId) {
		Tuser user = userDao.get(userId);		
		return user;
	}

	@Override
	public Tuser validateUser(String userId, String pwd) {
		String[] popertyName=new String[]{"userId","pwd"};
		String[] value=new String[]{userId,pwd};
		Tuser user=userDao.findUnique(popertyName, value);
		return user;
	}

	@Override
	public List<TuserAddr> getUserAddrs(String userId) {
		return userAddrDao.findBy("userId", userId);		
	}
}
