package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.habitbuilder.domain.PageQuery;
import com.example.habitbuilder.domain.vo.UserVo;
import com.example.habitbuilder.pojo.User;
import com.example.habitbuilder.mapper.UserMapper;
import com.example.habitbuilder.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.habitbuilder.utils.JwtUtil;
import com.example.habitbuilder.utils.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public List<User> getUserList(User user, PageQuery pageQuery) {
		System.out.println("666");
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
	public User getByUserId(int userId) {
		return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
	}

	@Override
	public List<User> searchUser(User user) {
		LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
		lqw.eq(user.getUserName() != null, User::getUserName, user.getUserName());
		lqw.like(user.getNickName() != null, User::getNickName, user.getNickName());
		return userMapper.selectList(lqw);
	}

	@Override
	public Boolean updateUser(String token, User user) {
		int userId = jwtUtil.extractUserId(token);
		User user1 = userMapper.selectOne(new LambdaQueryWrapper<User>()
				.eq(User::getUserName, user.getUserName()));
		if (user1.getUserId() != userId) {
			return false;
		}
		user.setUserId(userId);
		userMapper.updateById(user);
		return true;
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
	public void changeScore(int userId) {
		User user = getById(userId);
		int score = user.getMyScore() + 5;
		user.setMyScore(score);
		updateById(user);
	}

	@Override
	public boolean deleteById(String token, int userId) {
		int myUserId = jwtUtil.extractUserId(token);
		if (myUserId == userId) {
			userMapper.deleteById(userId);
			return true;
		}
		return false;
	}

	public UserVo ConvertToUserVo(User user) {
		UserVo vo = new UserVo();
		vo.setUserId(user.getUserId());
		vo.setUserName(user.getUserName());
		vo.setNickName(user.getNickName());
		vo.setPassword(user.getPassword());
		vo.setAvatarImg(user.getAvatarImg());
		vo.setMyScore(user.getMyScore());
		vo.setRegisterTime(user.getRegisterTime());
		return vo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
				.eq(User::getUserName,username));
		if(user == null){
			throw new UsernameNotFoundException(username);
		}
		return new LoginManager(user);
	}

	/*private LambdaQueryWrapper<UserVo> buildlqw(UserBo bo){
		LambdaQueryWrapper<UserVo> lqw = new LambdaQueryWrapper<>();
	}*/
}
