package com.uswop.pms.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uswop.pms.commons.SystemConstants;
import com.uswop.pms.commons.UswopException;
import com.uswop.pms.dao.UserPointsDao;
import com.uswop.pms.dto.TuserPoints;
import com.uswop.pms.service.UserPointsService;

@Service
public class UserPointsServiceImplBySql implements UserPointsService {
	@Autowired
	private UserPointsDao userPointsDao;
				
	public TuserPoints getUserInfoById(String userId) {							
		TuserPoints userInfo = userPointsDao.findUniqueByHqlName(SystemConstants.SQL_QUERY_NAME_FINDUSER,userId);
		if(userInfo==null){
			throw new UswopException(SystemConstants.MSGNAME_USERPOINTS_GETUSERINFO_IS_NULL);
		}
		return userInfo;
	}
		
	public void deductPoints(String userId,int deductedPoints) {
		TuserPoints userInfo = userPointsDao.findUniqueByHqlName(SystemConstants.SQL_QUERY_NAME_FINDUSER,userId);
		if(userInfo==null){
			throw new UswopException(SystemConstants.MSGNAME_USERPOINTS_GETUSERINFO_IS_NULL);
		}
		int curPoints=userInfo.getPoints();
		if(curPoints>=deductedPoints){
			Map<String,Object> paramMap=new HashMap<String,Object>();
			paramMap.put(SystemConstants.SQL_PARAM_NAME_USERID, userId);
			paramMap.put(SystemConstants.SQL_PARAM_NAME_POINTS, curPoints-deductedPoints);			
			userPointsDao.updateByHqlName(SystemConstants.SQL_QUERY_NAME_UPDATEUSER,paramMap);			
		}
		else{
			throw new UswopException(SystemConstants.MSGNAME_USERPOINTS_DEDUCTPOINTS_FAILED);
		}
		
	}
	
	public void addPoints(String userId,int addedPoints) {
		TuserPoints userInfo = userPointsDao.findUniqueByHqlName(SystemConstants.SQL_QUERY_NAME_FINDUSER,userId);
		if(userInfo==null){
			throw new UswopException(SystemConstants.MSGNAME_USERPOINTS_GETUSERINFO_IS_NULL);
		}
		int curPoints=userInfo.getPoints();
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put(SystemConstants.SQL_PARAM_NAME_USERID, userId);
		paramMap.put(SystemConstants.SQL_PARAM_NAME_POINTS, curPoints+addedPoints);		
		userPointsDao.updateByHqlName(SystemConstants.SQL_QUERY_NAME_UPDATEUSER,paramMap);		
	}
}
