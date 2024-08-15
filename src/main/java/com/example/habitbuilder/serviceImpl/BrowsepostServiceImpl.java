package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.habitbuilder.domain.vo.BrowseHistoryVo;
import com.example.habitbuilder.domain.vo.PostOverviewVo;
import com.example.habitbuilder.mapper.PostMapper;
import com.example.habitbuilder.mapper.UserMapper;
import com.example.habitbuilder.pojo.Browsepost;
import com.example.habitbuilder.mapper.BrowsepostMapper;
import com.example.habitbuilder.service.IBrowsepostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.habitbuilder.utils.LoginHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-08-30
 */
@Service
public class BrowsepostServiceImpl extends ServiceImpl<BrowsepostMapper, Browsepost> implements IBrowsepostService {
	@Autowired
	LoginHelper loginHelper;
	@Autowired
	BrowsepostMapper browsepostMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	PostMapper postMapper;

	@Override
	public List<BrowseHistoryVo> getBrowseHistory(String token) {
		int userId = loginHelper.getUserId(token);
		return browsepostMapper.selectBrowseList(userId);
	}

	@Override
	public void startBrowsing(String token, int postId) {
		int userId = loginHelper.getUserId(token);
		Browsepost bp = browsepostMapper.selectOne(new LambdaQueryWrapper<Browsepost>()
				.eq(Browsepost::getUserId,userId)
				.eq(Browsepost::getPostId,postId));
		if(bp != null){
			bp.setStartTime(LocalDateTime.now());
			bp.setEndTime(null);
		}else{
			Browsepost browsepost = new Browsepost();
			browsepost.setUserId(userId);
			browsepost.setPostId(postId);
			browsepost.setStartTime(LocalDateTime.now());
			browsepost.setEndTime(null);
			browsepost.setBrowseTime(0);
			browsepostMapper.insert(browsepost);
		}
	}

	@Override
	public void endBrowsing(String token, int postId) {
		int userId = loginHelper.getUserId(token);
		Browsepost bp = browsepostMapper.selectOne(new LambdaQueryWrapper<Browsepost>()
				.eq(Browsepost::getUserId,userId)
				.eq(Browsepost::getPostId,postId));
		bp.setEndTime(LocalDateTime.now());
		int browseTime = (int) Duration.between(bp.getStartTime(), bp.getEndTime()).getSeconds();
		bp.setBrowseTime(browseTime);
		browsepostMapper.updateById(bp);
	}
}
