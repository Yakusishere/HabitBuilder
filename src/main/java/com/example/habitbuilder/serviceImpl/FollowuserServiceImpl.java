package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.habitbuilder.domain.PageQuery;
import com.example.habitbuilder.domain.vo.UserVo;
import com.example.habitbuilder.mapper.UserMapper;
import com.example.habitbuilder.pojo.Followuser;
import com.example.habitbuilder.mapper.FollowuserMapper;
import com.example.habitbuilder.pojo.User;
import com.example.habitbuilder.service.IFollowuserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.habitbuilder.service.IUserService;
import com.example.habitbuilder.utils.LoginHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    LoginHelper loginHelper;
    @Autowired
    FollowuserMapper followuserMapper;
    @Autowired
    IUserService userService;
	@Autowired
	private UserMapper userMapper;

    @Override
    public List<UserVo> getFollowList(String token, PageQuery pageQuery) {
        int userId = loginHelper.getUserId(token);
	    return followuserMapper.selectPage(pageQuery.build(),
                        new LambdaQueryWrapper<Followuser>().eq(Followuser::getFollowerId, userId))
                .getRecords()
                .stream()
                .map(Followuser::getFolloweeId)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        userIdList -> userMapper.selectList(new LambdaQueryWrapper<User>().in(User::getUserId, userIdList))
                ))
                .stream()
                .map(userService::ConvertToUserVo)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserVo> getFanList(String token, PageQuery pageQuery) {
        int userId = loginHelper.getUserId(token);
        return followuserMapper.selectPage(pageQuery.build(),
                        new LambdaQueryWrapper<Followuser>().eq(Followuser::getFollowerId, userId))
                .getRecords()
                .stream()
                .map(Followuser::getFolloweeId)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        userIdList -> userMapper.selectList(new LambdaQueryWrapper<User>().in(User::getUserId, userIdList))
                ))
                .stream()
                .map(userService::ConvertToUserVo)
                .collect(Collectors.toList());
    }

    @Override
    public void addFollowUser(Followuser followuser) {
        followuserMapper.insert(followuser);
    }

    @Override
    public void deleteFollowUser(int followUserId) {
        followuserMapper.deleteById(followUserId);
    }

    @Override
    public List<Followuser> getFollowUsers(int receiveUserId)
    {
        QueryWrapper<Followuser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiveUserId", receiveUserId);
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
