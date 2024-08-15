package com.example.habitbuilder.service;

import com.example.habitbuilder.domain.vo.ReplyVo;
import com.example.habitbuilder.pojo.Reply;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-08-15
 */
public interface IReplyService extends IService<Reply> {

	ReplyVo convertToReplyVo(int userId, Reply reply);
}
