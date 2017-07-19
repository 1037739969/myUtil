package com.guolei.shiro.basic;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

public class MyRealm2 implements Realm {

    public String getName() {  
        return "myrealm2";  
    }  
    public boolean supports(AuthenticationToken token) {  
        //��֧��UsernamePasswordToken���͵�Token  
        return token instanceof UsernamePasswordToken;   
    }  
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {  
        String username = (String)token.getPrincipal();  //�õ��û���  
        String password = new String((char[])token.getCredentials()); //�õ�����  
        if(!"wang".equals(username)) {  
            throw new UnknownAccountException(); //����û�������  
        }  
        if(!"123".equals(password)) {  
            throw new IncorrectCredentialsException(); //����������  
        }  
        //���������֤��֤�ɹ�������һ��AuthenticationInfoʵ�֣�  
        return new SimpleAuthenticationInfo(username, password, getName());  
    }  
}