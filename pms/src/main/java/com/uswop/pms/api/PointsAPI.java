/**
 * 
 */
package com.uswop.pms.api;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uswop.pms.api.base.BaseController;
import com.uswop.pms.commons.SystemConfig;
import com.uswop.pms.commons.UswopException;
import com.uswop.pms.dto.TuserPoints;
import com.uswop.pms.service.UserPointsService;


@Controller
@RequestMapping("/point")
public class PointsAPI extends BaseController {
	
	@Autowired
	private UserPointsService userPointsService;
	@Autowired
	private SystemConfig systemConfig;
	
	private Logger logger = Logger.getLogger(PointsAPI.class);
	
	@RequestMapping(value="query",method=RequestMethod.POST)
	@ResponseBody
	public String getPoints(HttpServletResponse response,@RequestHeader("Authorization") String apiKey,@RequestBody String jsonStr) {		
		
		JSONObject jsonObj=null;
		JSONObject respJson = new JSONObject();						
		if(apiKey==null||!apiKey.equalsIgnoreCase(systemConfig.getApiKey())){
			respJson.put("status", false);
			respJson.put("info", "Error API token.");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			logger.error("Failed to query points by user ID, error API token.");
			return JSON.toJSONString(respJson);
		}
		
		if(jsonStr == null||jsonStr.isEmpty()) {
			respJson.put("status", false);
			respJson.put("info", "The request parameter is required.");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			logger.error("Failed to query points by user ID, the request parameter is empty.");
			return JSON.toJSONString(respJson);
		}
		String userid=null;				
		try{
			jsonObj= (JSONObject)JSON.parse(jsonStr);
			userid=jsonObj.getString("userId");
			if(userid==null||userid.isEmpty()){
				respJson.put("status", false);
				respJson.put("info", "The parameter userId is required.");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				logger.error("Failed to query the points by user ID, the parameter is empty.");
				return JSON.toJSONString(respJson);				
			}
			TuserPoints userinfo=userPointsService.getUserInfoById(userid);
			logger.info("Query the points by user ID "+userid+" successfully.");
			int points=0;
			if(userinfo!=null){
				points=userinfo.getPoints();
			}
			
			respJson.put("status", true);
			respJson.put("info", "OK");
			respJson.put("points", points);
			return JSON.toJSONString(respJson);
		}
		catch(UswopException e){
			respJson.put("status", false);
			respJson.put("info", e.getMessage());
			logger.error("Failed to query the points by user ID "+userid+", the exception information is : "+e.getMessage());
			return JSON.toJSONString(respJson);
		}		
	}
		
	
	@RequestMapping(value="deduct",method=RequestMethod.POST)
	@ResponseBody
	public String deductPoints(HttpServletResponse response,@RequestHeader("Authorization") String apiKey,@RequestBody String jsonStr) {
		JSONObject jsonObj=null;
		JSONObject respJson = new JSONObject();						
		if(apiKey==null||!apiKey.equalsIgnoreCase(systemConfig.getApiKey())){
			respJson.put("status", false);
			respJson.put("info", "Error API token.");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			logger.error("Failed to deduct points by user ID, error API token.");
			return JSON.toJSONString(respJson);
		}
		if(jsonStr == null||jsonStr.isEmpty()) {
			respJson.put("status", false);
			respJson.put("info", "The request parameter is required.");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			logger.error("Failed to deduct points by user ID, the request parameter is empty.");
			return JSON.toJSONString(respJson);
		}	
		
		String userid=null;
		try{
			jsonObj= (JSONObject)JSON.parse(jsonStr);
			userid=jsonObj.getString("userId");
			if(userid==null||userid.isEmpty()){
				respJson.put("status", false);
				respJson.put("info", "The parameter userId is required.");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				logger.error("Failed to deduct the points by user ID, the parameter userId is empty.");
				return JSON.toJSONString(respJson);				
			}
						
			int deductPoints=jsonObj.getInteger("points");					
		
			userPointsService.deductPoints(userid, deductPoints);
			respJson.put("status", true);
			respJson.put("info", "OK");
			logger.info("Deducted "+deductPoints+" points for user "+userid+" successfully.");
			return JSON.toJSONString(respJson);								
			
		}		
		catch(UswopException e){
			respJson.put("status", false);
			respJson.put("info", e.getMessage());
			logger.error("Failed to deduct the points by user ID "+userid+", the exception information is : "+e.getMessage());
			return JSON.toJSONString(respJson);			
		}		
		
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public String addPoints(HttpServletResponse response,@RequestHeader("Authorization") String apiKey,@RequestBody String jsonStr) {
		JSONObject jsonObj=null;
		JSONObject respJson = new JSONObject();						
		if(apiKey==null||!apiKey.equalsIgnoreCase(systemConfig.getApiKey())){
			respJson.put("status", false);
			respJson.put("info", "Error API token.");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			logger.error("Failed to add points by user ID, error API token.");
			return JSON.toJSONString(respJson);
		}
		if(jsonStr == null||jsonStr.isEmpty()) {
			respJson.put("status", false);
			respJson.put("info", "The request parameter is required.");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			logger.error("Failed to add points by user ID, the request parameter is empty.");
			return JSON.toJSONString(respJson);
		}
		String userid=null;
		try{
			jsonObj= (JSONObject)JSON.parse(jsonStr);
			userid=jsonObj.getString("userId");
			int addPoints=jsonObj.getInteger("points");					
			if(userid==null||userid.isEmpty()){
				respJson.put("status", false);
				respJson.put("info", "The parameter userId is required.");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				logger.error("Failed to add the points by user ID, the parameter userId is empty.");
				return JSON.toJSONString(respJson);				
			}
			userPointsService.addPoints(userid, addPoints);
			respJson.put("status", true);
			respJson.put("info", "OK");
			logger.info("Added "+addPoints+" points for user "+userid+" successfully.");
			return JSON.toJSONString(respJson);								
			
		}		
		catch(UswopException e){
			respJson.put("status", false);
			respJson.put("info", e.getMessage());
			logger.error("Failed to deduct the points by user ID "+userid+", the exception information is : "+e.getMessage());
			return JSON.toJSONString(respJson);			
		}					
		
	}			
	
}
