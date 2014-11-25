package com.uswop.pms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uswop.pms.commons.SystemConstants;
import com.uswop.pms.commons.UswopException;
import com.uswop.pms.dao.UserPointsDao;
import com.uswop.pms.dto.TuserPoints;
import com.uswop.pms.service.UserPointsService;

@Service
public class UserPointsServiceImpl implements UserPointsService {
	@Autowired
	private UserPointsDao userPointsDao;		

	public TuserPoints getUserInfoById(String userId) {							
		TuserPoints userInfo = userPointsDao.get(userId);
		if(userInfo==null){
			throw new UswopException(SystemConstants.MSGNAME_USERPOINTS_GETUSERINFO_IS_NULL);
		}
		return userInfo;
	}
		
	
	public void deductPoints(String userId,int deductedPoints) {
		TuserPoints userInfo = userPointsDao.get(userId);				
		if(userInfo==null){			
			throw new UswopException(SystemConstants.MSGNAME_USERPOINTS_GETUSERINFO_IS_NULL);
		}
		int curPoints=userInfo.getPoints();
		if(curPoints>=deductedPoints){
			userInfo.setPoints(curPoints-deductedPoints);
			userPointsDao.update(userInfo);				
		}
		else{			
			throw new UswopException(SystemConstants.MSGNAME_USERPOINTS_DEDUCTPOINTS_FAILED);
		}
		
	}
	
	public void addPoints(String userId,int addedPoints) {
		TuserPoints userInfo = userPointsDao.get(userId);		
		if(userInfo==null){
			throw new UswopException(SystemConstants.MSGNAME_USERPOINTS_GETUSERINFO_IS_NULL);
		}
		int curPoints=userInfo.getPoints();
		userInfo.setPoints(curPoints+addedPoints);
		userPointsDao.update(userInfo);				
	}
}
