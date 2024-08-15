package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.habitbuilder.pojo.Likereply;
import com.example.habitbuilder.mapper.LikereplyMapper;
import com.example.habitbuilder.service.ILikereplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-08-15
 */
@Service
public class LikereplyServiceImpl extends ServiceImpl<LikereplyMapper, Likereply> implements ILikereplyService {
	@Autowired
	LikereplyMapper likereplyMapper;

	@Override
	public String getIfLikeReply(int userId, int replyId) {
		LambdaQueryWrapper<Likereply> lqw = new LambdaQueryWrapper<>();
		lqw.eq(Likereply::getUserId,userId);
		lqw.eq(Likereply::getReplyId,replyId);
		int count = Math.toIntExact(likereplyMapper.selectCount(lqw));
		if(count==0){
			return "false";
		} else if (count == 1) {
			return "true";
		}else{
			return "点赞评论信息有错误";
		}
	}
}
