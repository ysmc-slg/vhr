package top.zxqs.system.mapper;

import top.zxqs.common.core.domain.entity.Hr;

/**
 * HR表 数据层
 */
public interface HrMapper {
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
    int updateHr(Hr hr);
}
