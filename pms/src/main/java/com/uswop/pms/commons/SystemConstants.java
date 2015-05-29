/**   
* @Title: SystemConstants.java 
* @Package com.uswop.commons 
*
* @Description: 系统全局常量类
* 
* @date Sep 9, 2014 7:14:02 PM
* @version V1.0   
*/ 
package com.uswop.pms.commons;

/** 
 * @ClassName: SystemConstants 
 * @Description: TODO
 * @author Phills Li
 * @date Sep 9, 2014 7:14:02 PM 
 *  
 */
public class SystemConstants {
	public static final String MSGNAME_USERPOINTS_GETUSERINFO_IS_NULL="error.UserPointsServiceImpl.getUserInfoById.isNull";
	public static final String MSGNAME_USERPOINTS_DEDUCTPOINTS_FAILED="error.UserPointsServiceImpl.deductPoints";
	
	public static final String MSGNAME_USER_GETUSERINFO_IS_NULL="error.UserServiceImpl.getUser.isNull";
	
    public static final String SQL_NAME_QUERY_FIND_USER="getUserInfo";
    
    public static final String SQL_NAME_QUERY_VALIDATE_USER="validateUser";
    
    public static final String SQL_NAME_QUERY_FIND_USERADDR="getUserAddr";
		
	
	public static final String SQL_NAME_QUERY_FIND_USERPOINTS="findUserPoints";
	
	public static final String SQL_NAME_QUERY_UPDATE_USERPOINTS="updateUserPoints";
			
	public static final String SQL_PARAM_USERID="ID";
	
	public static final String SQL_PARAM_POINTS="POINTS";
	
}
