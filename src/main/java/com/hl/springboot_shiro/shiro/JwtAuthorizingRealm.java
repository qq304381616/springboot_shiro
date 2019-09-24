package com.hl.springboot_shiro.shiro;


import com.hl.springboot_shiro.domain.Permission;
import com.hl.springboot_shiro.domain.Role;
import com.hl.springboot_shiro.domain.User;
import com.hl.springboot_shiro.repository.UserRepository;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class JwtAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserRepository userRepository;

    /**
     * 注意坑点 : 必须重写此方法，不然Shiro会报错 因为创建了 JWTToken 用于替换Shiro原生
     * token,所以必须在此方法中显式的进行替换，否则在进行判断时会一直失败
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("用户权限配置-->JwtAuthorizingRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();

        for (Role role : user.getRoles()) {
            authorizationInfo.addRole(role.getName());
            for (Permission p : role.getPermissions()) {
                authorizationInfo.addStringPermission(p.getName());
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("JwtAuthorizingRealm.doGetAuthenticationInfo()");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        User user = userRepository.findByUsername(username);
        return new SimpleAuthenticationInfo(user, username, getName());
    }
}
