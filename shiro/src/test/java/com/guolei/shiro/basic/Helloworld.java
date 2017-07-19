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
	 * ��shiro�Ļ������ò���
	 * ����ͨ����Դ�ļ���ȡ���û��Ļ�����Ϣ�����ж�,��ȡ�û���Ϣ
	 */
	@Test
	//����shiro��ܵĻ�������
	public void testHelloworld()
	{
		//1.��ȡSecurityManager����
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-authenticator-all-success.ini");
		org.apache.shiro.mgt.SecurityManager securityManager =  factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		
		Subject subject = SecurityUtils.getSubject();
		
	    UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
	    
	    UsernamePasswordToken token1 = new UsernamePasswordToken("wang", "123");
	    
	    

	    try {  
	        //4����¼���������֤  
	        subject.login(token);  
	        
	        subject.login(token1);
	    } catch (AuthenticationException e) {  
	        //5�������֤ʧ��  
	    }  
	  
	    //Assert.assertEquals(true, subject.isAuthenticated()); //�����û��Ѿ���¼  
	    System.out.println(subject.isAuthenticated()); //�����û��Ѿ���¼  
	    
	    //�õ�һ����ݼ��ϣ��������Realm��֤�ɹ��������Ϣ  
	    PrincipalCollection principalCollection = subject.getPrincipals();  
	  
	    System.out.println(principalCollection.asList().toString());  
	    
	    //6���˳�  
	    subject.logout();  
		
		
	}
	

	/**
	 * ����Ȩ�Ĳ���,Ϊ�˷����ȷ�װһ��������,��ɻ�������
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
	 * ������Ȩ
	 */
	@Test
	public void testShouquan(){
		
		shiroUtil("shiro-role.ini", "zhang", "123");  
		    //�ж�ӵ�н�ɫ��role1  
		    System.out.println(subject.hasRole("role1"));  
		    //�ж�ӵ�н�ɫ��role1 and role2  
		    System.out.println(subject.hasAllRoles(Arrays.asList("role1", "role2")));  
		    //�ж�ӵ�н�ɫ��role1 and role2 and !role3  
		    boolean[] result = subject.hasRoles(Arrays.asList("role1", "role2", "role3"));  
		    System.out.println( result[0]);  
		    System.out.println( result[1]);  
		    System.out.println( result[2]); 
		    
		    hello();
	}
	
	
	@Test
	@RequiresRoles("role3")  
	public void hello() {  
	    System.out.println("�����Ȩ��");
	}
	
	
	@Test  
	public void testIsPermitted() {  
		shiroUtil("shiro-permission.ini", "zhang", "123");  
	    //�ж�ӵ��Ȩ�ޣ�user:create  
	    Assert.assertTrue(subject.isPermitted("user:create"));  
	    //�ж�ӵ��Ȩ�ޣ�user:update and user:delete  
	    Assert.assertTrue(subject.isPermittedAll("user:update", "user:delete"));  
	    //�ж�û��Ȩ�ޣ�user:view  
	    Assert.assertFalse(subject.isPermitted("user:view"));  
	}   
	
	

}
