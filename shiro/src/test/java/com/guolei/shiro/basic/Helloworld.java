package com.guolei.shiro.basic;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

public class Helloworld {
	
	Subject subject;
	
	/**
	 * 对shiro的基本配置操作
	 * 比如通过资源文件获取对用户的基本信息进行判断,获取用户信息
	 */
	@Test
	//测试shiro框架的基本操作
	public void testHelloworld()
	{
		//1.获取SecurityManager工厂
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-authenticator-all-success.ini");
		org.apache.shiro.mgt.SecurityManager securityManager =  factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		
		Subject subject = SecurityUtils.getSubject();
		
	    UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
	    
	    UsernamePasswordToken token1 = new UsernamePasswordToken("wang", "123");
	    
	    

	    try {  
	        //4、登录，即身份验证  
	        subject.login(token);  
	        
	        subject.login(token1);
	    } catch (AuthenticationException e) {  
	        //5、身份验证失败  
	    }  
	  
	    //Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录  
	    System.out.println(subject.isAuthenticated()); //断言用户已经登录  
	    
	    //得到一个身份集合，其包含了Realm验证成功的身份信息  
	    PrincipalCollection principalCollection = subject.getPrincipals();  
	  
	    System.out.println(principalCollection.asList().toString());  
	    
	    //6、退出  
	    subject.logout();  
		
		
	}
	

	/**
	 * 对授权的操作,为了方便先封装一个方法类,完成基本配置
	 */
	public void shiroUtil(String config,String username,String password)
	{
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:"+config);
		SecurityManager manager = factory.getInstance();
		SecurityUtils.setSecurityManager(manager);
		
		subject = SecurityUtils.getSubject();
		
		AuthenticationToken token = new UsernamePasswordToken(username, password);
			
		subject.login(token);
		
	}
	
	
	/**
	 * 测试授权
	 */
	@Test
	public void testShouquan(){
		
		shiroUtil("shiro-role.ini", "zhang", "123");  
		    //判断拥有角色：role1  
		    System.out.println(subject.hasRole("role1"));  
		    //判断拥有角色：role1 and role2  
		    System.out.println(subject.hasAllRoles(Arrays.asList("role1", "role2")));  
		    //判断拥有角色：role1 and role2 and !role3  
		    boolean[] result = subject.hasRoles(Arrays.asList("role1", "role2", "role3"));  
		    System.out.println( result[0]);  
		    System.out.println( result[1]);  
		    System.out.println( result[2]); 
		    
		    hello();
	}
	
	
	@Test
	@RequiresRoles("role3")  
	public void hello() {  
	    System.out.println("有这个权限");
	}
	
	
	@Test  
	public void testIsPermitted() {  
		shiroUtil("shiro-permission.ini", "zhang", "123");  
	    //判断拥有权限：user:create  
	    Assert.assertTrue(subject.isPermitted("user:create"));  
	    //判断拥有权限：user:update and user:delete  
	    Assert.assertTrue(subject.isPermittedAll("user:update", "user:delete"));  
	    //判断没有权限：user:view  
	    Assert.assertFalse(subject.isPermitted("user:view"));  
	}   
	
	

}
