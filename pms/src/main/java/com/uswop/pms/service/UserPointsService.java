package com.uswop.pms.service;

import com.uswop.pms.dto.TuserPoints;


public interface UserPointsService {
			
	TuserPoints getUserInfoById(String userId);
	
	void deductPoints(String userId,int deductedPoints);
	
	void addPoints(String userId,int addedPoints);
}
