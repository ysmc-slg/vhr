package top.zxqs.system.service;

import top.zxqs.common.core.domain.entity.Hr;

import java.util.List;

/**
 * 用户  业务层
 * @Author: zxq
 * @date: 2022-01-24 11:12
 */

public interface IHrService {
    /**
     * 通过用户名查询HR用户
     * @param username
     * @return
     */
    Hr selectUserByUserName(String username);

    /**
     * 修改hr信息
     * @param hr
     * @return
     */
    int updateHrProfile(Hr hr);
}
