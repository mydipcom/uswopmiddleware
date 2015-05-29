package com.uswop.pms.service;

import java.util.List;

import com.uswop.pms.dto.Tuser;
import com.uswop.pms.dto.TuserAddr;


public interface UserService {
			
	Tuser getUser(String userId);
	
	Tuser validateUser(String userId,String pwd);
	
	List<TuserAddr> getUserAddrs(String userId);
}
