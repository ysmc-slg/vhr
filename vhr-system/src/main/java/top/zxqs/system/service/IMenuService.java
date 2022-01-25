package top.zxqs.system.service;

import java.util.List;
import java.util.Set;

public interface IMenuService {

    /**
     * 根据用户id 查询权限
     * @param hrId
     * @return
     */
    Set<String> selectMenuPermsByUserId(Long hrId);
}
