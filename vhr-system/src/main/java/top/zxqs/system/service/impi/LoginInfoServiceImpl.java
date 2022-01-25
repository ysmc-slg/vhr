package top.zxqs.system.service.impi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zxqs.system.domain.LoginInfo;
import top.zxqs.system.mapper.LoginInfoMapper;
import top.zxqs.system.service.ILoginInfoService;

/**
 * @Author: zxq
 * @date: 2022-01-25 12:39
 */
@Service
public class LoginInfoServiceImpl implements ILoginInfoService {

    @Autowired
    private LoginInfoMapper loginInfoMapper;

    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(LoginInfo logininfor) {
        loginInfoMapper.insertLogininfor(logininfor);
    }
}
