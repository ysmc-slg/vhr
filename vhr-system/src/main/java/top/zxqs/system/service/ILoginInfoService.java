package top.zxqs.system.service;

import top.zxqs.system.domain.LoginInfo;

/**
 * 系统访问日志情况信息 服务层
 */
public interface ILoginInfoService {
    /**
     * 新增系统登录日志
     * @param logininfor
     */
    void insertLogininfor(LoginInfo logininfor);
}
