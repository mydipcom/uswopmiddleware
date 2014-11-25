/**
 * 
 */
package com.uswop.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.wink.common.annotations.Workspace;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uswop.resource.UserInfo;

/**
 * @author phills.li
 *
 */
@Workspace(workspaceTitle = "Uswop Middel Ware Service", collectionTitle = "Point Service")
@Path("point")
public class PointService {

	@GET
	@Path("{userid}")
	@Produces("application/json")
	public Response getPoints(@PathParam("userid") String userId) {	
		UserInfo ui=new UserInfo();
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("point", "300");
		String str= JSON.toJSONString(jsonobj);
		return Response.ok(str).type(MediaType.APPLICATION_JSON).build();
	}
		
	
	@POST
	@Path("expense")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response expensePoints(String jsonStr) {
		if(jsonStr == null) {
			return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).build();
		}		
		JSONObject ja= (JSONObject)JSON.parse(jsonStr);
		String id=ja.getString("userid");
		UserInfo ui=new UserInfo();
		ui.setPoints("100");
		String str= JSON.toJSONString(ui);
		return Response.ok(str).type(MediaType.APPLICATION_JSON).build();
	}
	
	
}
