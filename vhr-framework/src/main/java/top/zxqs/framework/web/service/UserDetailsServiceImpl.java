package top.zxqs.framework.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.zxqs.common.core.domain.entity.Hr;
import top.zxqs.common.core.domain.model.LoginUser;
import top.zxqs.common.enums.UserStatus;
import top.zxqs.common.exception.ServiceException;
import top.zxqs.common.utils.StringUtils;
import top.zxqs.system.service.IHrService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private IHrService hrService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Hr hr = hrService.selectUserByUserName(username);
        if (StringUtils.isNull(hr))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("登录用户：" + username + " 不存在");
        }
        else if (UserStatus.DELETED.getCode().equals(hr.getDelFlag()))
        {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        else if (UserStatus.DISABLE.getCode().equals(hr.getStatus()))
        {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }

        return createLoginUser(hr);
    }

    public UserDetails createLoginUser(Hr hr)
    {
        return new LoginUser(hr.getHrId(), hr.getDeptId(), hr, permissionService.getMenuPermission(hr));
    }
}