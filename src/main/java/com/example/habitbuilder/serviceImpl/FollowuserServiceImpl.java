package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.pojo.Followuser;
import com.example.habitbuilder.mapper.FollowuserMapper;
import com.example.habitbuilder.service.IFollowuserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-06
 */
@Service
public class FollowuserServiceImpl extends ServiceImpl<FollowuserMapper, Followuser> implements IFollowuserService {
    @Autowired
    FollowuserMapper followuserMapper;

    @Override
    public void addFollowUser(Followuser followuser) {
        followuserMapper.insert(followuser);
    }

    @Override
    public void deleteFollowUser(int followUserId) {
        followuserMapper.deleteById(followUserId);
    }

    @Override
    public List<Followuser> getFollowUsers(int sendUserId)
    {
        QueryWrapper<Followuser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sendUserId", sendUserId);
        return followuserMapper.selectList(queryWrapper);
    }

    @Override
    public String getIfFollow(int sendUserId,int receiveUserId) {
        QueryWrapper<Followuser> queryWrapper = new QueryWrapper<Followuser>();
        queryWrapper.eq("sendUserId", sendUserId);
        queryWrapper.eq("receiveUserId", receiveUserId);
        List<Followuser> followUsers = followuserMapper.selectList(queryWrapper);
        if(!followUsers.isEmpty()){
            return "true";
        }else{
            return "false";
        }
    }
}
