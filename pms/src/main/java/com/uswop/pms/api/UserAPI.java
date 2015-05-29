/**
 * 
 */
package com.uswop.pms.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uswop.pms.api.base.BaseController;
import com.uswop.pms.commons.SecurityTools;
import com.uswop.pms.commons.SystemCached;
import com.uswop.pms.commons.SystemConfig;
import com.uswop.pms.commons.UswopException;
import com.uswop.pms.dto.Tuser;
import com.uswop.pms.dto.TuserAddr;
import com.uswop.pms.service.UserService;


@Controller
@RequestMapping("/api/user")
public class UserAPI extends BaseController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private SystemConfig systemConfig;
	
	private Logger logger = Logger.getLogger(UserAPI.class);
	
	@RequestMapping(value="login",method=RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request,HttpServletResponse response,@RequestHeader("Authorization") String apiKey,@RequestBody String jsonStr) {		
		//String referer = request.getHeader("Referer");
		JSONObject jsonObj=null;
		JSONObject respJson = new JSONObject();						
		if(apiKey==null||!apiKey.equalsIgnoreCase(SystemCached.Api_Token)||System.currentTimeMillis()>(SystemCached.Api_Token_CreatedTime+systemConfig.getTokenExpiresIn()*1000)){
			respJson.put("errCode", 40001);
			respJson.put("errMsg", "Error API token.");			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			logger.error("Failed to access the login API, error API token.");
			return JSON.toJSONString(respJson);
		}
		
		if(jsonStr == null||jsonStr.isEmpty()) {
			respJson.put("errCode", 40006);
			respJson.put("errMsg", "The request parameter is required.");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			logger.error("Failed to access the login API, the request parameter is empty.");
			return JSON.toJSONString(respJson);
		}
		String userid=null;				
		try{
			jsonObj= (JSONObject)JSON.parse(jsonStr);
			userid=jsonObj.getString("userId");
			String pwd=SecurityTools.MD5(jsonObj.getString("pwd"));
			if(userid==null||userid.isEmpty()){
				respJson.put("errCode", 40005);
				respJson.put("errMsg", "The parameter userId is required.");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				logger.error("Failed to access the login API, the parameter userId is empty.");
				return JSON.toJSONString(respJson);				
			}
			Tuser userinfo=userService.validateUser(userid,pwd);
			if(userinfo==null){
				respJson.put("errCode", 40004);
				respJson.put("errMsg", "Login fails, user name or password is error.");				
				logger.error("Login fails, user name or password is error.");
				return JSON.toJSONString(respJson);				
			}
			logger.info("User "+userid+" login successfully.");
			String ticket=SecurityTools.SHA1(userid+System.currentTimeMillis());
			SystemCached.User_Ticket.put(userid, ticket);
			JSONObject dataObj=new JSONObject();
			dataObj.put("ticket", ticket);
			respJson.put("errCode", 0);
			respJson.put("errMsg", "OK");
			respJson.put("data", dataObj);
			return JSON.toJSONString(respJson);
		}
		catch(UswopException e){
			respJson.put("errCode", -1);
			respJson.put("errMsg", e.getMessage());
			logger.error("Failed to access the login API, the exception information is : "+e.getMessage());
			return JSON.toJSONString(respJson);
		}		
	}
	
	@RequestMapping(value="logout",method=RequestMethod.POST)
	@ResponseBody
	public String logout(HttpServletRequest request,HttpServletResponse response,@RequestHeader("Authorization") String apiKey,@RequestBody String jsonStr) {		
		//String referer = request.getHeader("Referer");
		JSONObject jsonObj=null;
		JSONObject respJson = new JSONObject();						
		if(apiKey==null||!apiKey.equalsIgnoreCase(SystemCached.Api_Token)||System.currentTimeMillis()>(SystemCached.Api_Token_CreatedTime+systemConfig.getTokenExpiresIn()*1000)){
			respJson.put("errCode", 40001);
			respJson.put("errMsg", "Error API token.");			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			logger.error("Failed to access the login API, error API token.");
			return JSON.toJSONString(respJson);
		}
		
		if(jsonStr == null||jsonStr.isEmpty()) {
			respJson.put("errCode", 40006);
			respJson.put("errMsg", "The request parameter is required.");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			logger.error("Failed to access the login API, the request parameter is empty.");
			return JSON.toJSONString(respJson);
		}
		String userid=null;				
		try{
			jsonObj= (JSONObject)JSON.parse(jsonStr);
			userid=jsonObj.getString("userId");			
			if(userid==null||userid.isEmpty()){
				respJson.put("errCode", 40005);
				respJson.put("errMsg", "The parameter userId is required.");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				logger.error("Failed to access the logout API, the parameter userId is empty.");
				return JSON.toJSONString(respJson);				
			}
			SystemCached.User_Ticket.remove(userid);			
			logger.info("User "+userid+" logout successfully.");			
			respJson.put("errCode", 0);
			respJson.put("errMsg", "OK");			
			return JSON.toJSONString(respJson);
		}
		catch(UswopException e){
			respJson.put("errCode", -1);
			respJson.put("errMsg", e.getMessage());
			logger.error("Failed to access the logout API, the exception information is : "+e.getMessage());
			return JSON.toJSONString(respJson);
		}		
	}
	
	@RequestMapping(value="getUserInfo",method=RequestMethod.POST)
	@ResponseBody
	public String getUserInfo(HttpServletRequest request,HttpServletResponse response,@RequestHeader("Authorization") String apiKey,@RequestBody String jsonStr) {		
		JSONObject jsonObj=null;
		JSONObject respJson = new JSONObject();						
		if(apiKey==null||!apiKey.equalsIgnoreCase(SystemCached.Api_Token)||System.currentTimeMillis()>(SystemCached.Api_Token_CreatedTime+systemConfig.getTokenExpiresIn()*1000)){
			respJson.put("errCode", 40001);
			respJson.put("errMsg", "Error API token.");
//			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			logger.error("Failed to access the getUserInfo API, error API token.");
			return JSON.toJSONString(respJson);
		}
		
		if(jsonStr == null||jsonStr.isEmpty()) {
			respJson.put("errCode", 40006);
			respJson.put("errMsg", "The request parameter is required.");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			logger.error("Failed to access the getUserInfo API, the request parameter is empty.");
			return JSON.toJSONString(respJson);
		}
		String userid=null;				
		try{
			jsonObj= (JSONObject)JSON.parse(jsonStr);
			userid=jsonObj.getString("userId");			
			if(userid==null||userid.isEmpty()){
				respJson.put("errCode", 40005);
				respJson.put("errMsg", "The request parameter is required.");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				logger.error("Failed to access the getUserInfo API, the parameter is empty.");
				return JSON.toJSONString(respJson);				
			}
			Tuser userinfo=userService.getUser(userid);			
			logger.info("Get user "+userid+" information successfully.");
						
			respJson.put("errCode", 0);
			respJson.put("errMsg", "OK");
			if(userinfo==null){
				respJson.put("data", null);
			}
			else{
				JSONObject dataObj=new JSONObject();
				dataObj.put("userName", userinfo.getUserName());
				dataObj.put("email", userinfo.getPwd());				
				respJson.put("data", dataObj);
			}
			
			return JSON.toJSONString(respJson);
		}
		catch(UswopException e){
			respJson.put("errCode", -1);
			respJson.put("errMsg", e.getMessage());
			logger.error("Failed to access the getUserInfo API, the exception information is : "+e.getMessage());
			return JSON.toJSONString(respJson);
		}		
	}
	
	@RequestMapping(value="getUserAddr",method=RequestMethod.POST)
	@ResponseBody
	public String getUserAddr(HttpServletRequest request,HttpServletResponse response,@RequestHeader("Authorization") String apiKey,@RequestBody String jsonStr) {				
		JSONObject jsonObj=null;
		JSONObject respJson = new JSONObject();						
		if(apiKey==null||!apiKey.equalsIgnoreCase(SystemCached.Api_Token)||System.currentTimeMillis()>(SystemCached.Api_Token_CreatedTime+systemConfig.getTokenExpiresIn()*1000)){
			respJson.put("errCode", 40001);
			respJson.put("errMsg", "Error API token.");
//			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			logger.error("Failed to access the getUserAddr API, error API token.");
			return JSON.toJSONString(respJson);
		}
		
		if(jsonStr == null||jsonStr.isEmpty()) {
			respJson.put("errCode", 40006);
			respJson.put("errMsg", "The request parameter is required.");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			logger.error("Failed to access the getUserAddr API, the request parameter is empty.");
			return JSON.toJSONString(respJson);
		}
		String userid=null;				
		try{
			jsonObj= (JSONObject)JSON.parse(jsonStr);
			userid=jsonObj.getString("userId");			
			if(userid==null||userid.isEmpty()){
				respJson.put("errCode", 40005);
				respJson.put("errMsg", "The parameter userId is required.");
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				logger.error("Failed to access the getUserAddr API, the parameter is empty.");
				return JSON.toJSONString(respJson);				
			}
			List<TuserAddr> userAddrList=userService.getUserAddrs(userid);
			logger.info("Query all of the addresses owned by user "+userid+" successfully.");
			JSONArray arrayObj=new JSONArray();
			for (TuserAddr userAddr : userAddrList) {
				JSONObject dataObj=new JSONObject();
				dataObj.put("addrId", userAddr.getAddrId());
				dataObj.put("address", userAddr.getAddress());
				dataObj.put("recipients", userAddr.getRecipients());
				dataObj.put("phone", userAddr.getPhone());
				dataObj.put("country", userAddr.getCountry());
				dataObj.put("postalcode", userAddr.getPostalcode());
				arrayObj.add(dataObj);
			}
						
			respJson.put("errCode", 0);
			respJson.put("errMsg", "OK");
			respJson.put("data", arrayObj);
			return JSON.toJSONString(respJson);
		}
		catch(UswopException e){
			respJson.put("errCode", -1);
			respJson.put("errMsg", e.getMessage());
			logger.error("Failed to access the getUserAddr API, the exception information is : "+e.getMessage());
			return JSON.toJSONString(respJson);
		}		
	}			
	
}
