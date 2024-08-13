package com.example.habitbuilder.serviceImpl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.habitbuilder.domain.PageQuery;
import com.example.habitbuilder.pojo.User;
import com.example.habitbuilder.mapper.UserMapper;
import com.example.habitbuilder.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.habitbuilder.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<User> getUserList(User user, PageQuery pageQuery){
		// 创建查询条件
		LambdaQueryWrapper<User> lqw = Wrappers.lambdaQuery();
		lqw.eq(user.getUserId() != null, User::getUserId, user.getUserId());
		lqw.eq(user.getUserName() != null, User::getUserName, user.getUserName());
		lqw.eq(user.getNickName() != null, User::getNickName, user.getNickName());
		// 执行分页查询
		Page<User> resultPage = userMapper.selectPage(pageQuery.build(), lqw);
		// 返回当前页的数据记录
		return resultPage.getRecords();
	}

	@Override
	public User findByUserId(int userId) {
		LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
		System.out.println("FindByUserId:" + userId);
		lqw.eq(User::getUserId, userId);
		User user = userMapper.selectOne(lqw);
		if (user == null) {
			System.out.println("User not found");
		}else{
			System.out.println("Found User");
		}
		return user;
	}

	@Override
	public boolean findByUserName(String username) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userName", username);
		List<User> users = userMapper.selectList(queryWrapper);
		if (users.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public void register(String username, String password) {
		LocalDateTime localDateTime = LocalDateTime.now();
		//添加
		userMapper.insert(new User(null, username, null, password, null, localDateTime, 100));
	}

	@Override
	public String login(String username, String password) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userName", username).eq("password", password);
		User user = userMapper.selectOne(queryWrapper);
		if (user != null) {
			JwtUtils jwtUtils = new JwtUtils();
			return jwtUtils.createToken(user.getUserId(), user.getUserName());
		}
		return null;
	}

	@Override
	public void changeScore(int userId) {
		User user = getById(userId);
		int score = user.getMyScore() + 5;
		user.setMyScore(score);
		updateById(user);
	}

	@Override
	public boolean deleteById(Integer id) {
		userMapper.deleteById(id);
		return true;
	}

	@Override
	public User getUserId(Integer id) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userId", id);
		return userMapper.selectOne(queryWrapper);
	}

	/*private LambdaQueryWrapper<UserVo> buildlqw(UserBo bo){
		LambdaQueryWrapper<UserVo> lqw = new LambdaQueryWrapper<>();
	}*/
}
