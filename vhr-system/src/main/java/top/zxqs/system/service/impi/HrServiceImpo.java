package top.zxqs.system.service.impi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zxqs.common.core.domain.entity.Hr;
import top.zxqs.system.mapper.HrMapper;
import top.zxqs.system.service.IHrService;

/**
 * 用户  业务层
 * @Author: zxq
 * @date: 2022-01-24 11:12
 */
@Service
public class HrServiceImpo implements IHrService {

    @Autowired
    private HrMapper hrMapper;

    /**
     * 通过用户名查询HR用户
     * @param username
     * @return
     */

    @Override
    public Hr selectUserByUserName(String username) {

        return hrMapper.selectUserByUserName(username);
    }

    /**
     * 修改hr信息
     * @param hr
     * @return
     */
    @Override
    public int updateHrProfile(Hr hr) {
        return hrMapper.updateHr(hr);
    }
}
