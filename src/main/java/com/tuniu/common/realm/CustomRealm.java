package com.tuniu.common.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.common.model.ActiveUser;
import com.tuniu.common.model.Resource;
import com.tuniu.common.model.User;
import com.tuniu.common.service.UserService;

public class CustomRealm extends AuthorizingRealm {
	
	@Autowired
	private UserService service;

	// 支持什么类型的token
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	@Override
	public String getName() {
		return "customRealm";
	}
	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		//获取身份
		ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();	
		//获取资源权限
		List<Resource> permissions = service.getResByUserName(activeUser.getUsercode());
		
		//将权限封装为AuthorizationInfo
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		for(Resource permission : permissions){
			if(permission.getPercode()!=null && !"".equals(permission.getPercode()))
				authorizationInfo.addStringPermission(permission.getPercode());
		}
		return authorizationInfo;
	}
	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String usercode=(String)token.getPrincipal();
		User user = service.getUserByName(usercode);
		
		if(user ==null){//查询不到
			return null;
		}
		if(user.getLocked()==1){//用户锁定
			throw new DisabledAccountException();
		}
		ActiveUser activeUser = new ActiveUser();
		activeUser.setId(user.getId());
		activeUser.setUsercode(usercode);
		activeUser.setUsername(user.getUsername());
		//密码123,加盐，一次散列,5e202b9bfd175a81248aed6cd7c797f9
		String password=user.getPassword();
		String salt = user.getSalt();
		// 返回认证信息由父类AuthenticatingRealm进行认证
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, password,
				ByteSource.Util.bytes(salt),getName());
		return info;
	}
	
	//清除缓存
	public void clearCached(){
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}

}
