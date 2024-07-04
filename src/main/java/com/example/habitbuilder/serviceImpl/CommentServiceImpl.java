package com.example.habitbuilder.serviceImpl;

import com.example.habitbuilder.pojo.Comment;
import com.example.habitbuilder.mapper.CommentMapper;
import com.example.habitbuilder.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
