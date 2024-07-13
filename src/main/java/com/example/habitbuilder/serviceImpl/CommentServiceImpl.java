package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.mapper.LikecommentsMapper;
import com.example.habitbuilder.pojo.Comment;
import com.example.habitbuilder.mapper.CommentMapper;
import com.example.habitbuilder.pojo.Likecomments;
import com.example.habitbuilder.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private LikecommentsMapper likecommentsMapper;

    @Override
    public void addComment(Comment comment) { //评论
        List<Comment> commentList = commentMapper.selectList(null);
        if(!commentList.isEmpty()){
            commentList.sort((comment1,comment2)->comment2.getCommentCount() - comment1.getCommentCount()); //按commentCount倒序排序
            comment.setCommentCount(commentList.getFirst().getCommentCount()+1); // 取最大评论数+1
        }else{
            comment.setCommentCount(1);// 如果为空设置为1
        }
        commentMapper.insert(comment);
    }

    @Override
    public void addReplyComment(Comment comment) {
        //回复的commentCount 和 评论的commentCount相同
        commentMapper.insert(comment);
    }

    @Override
    public List<Comment[]> getThisPostComments(int postId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("postId", postId);
        List<Comment>comments = commentMapper.selectList(wrapper);
        // 按 commentCount 分组
        Map<Integer, List<Comment>> groupedComments = comments.stream()
                .collect(Collectors.groupingBy(Comment::getCommentCount));
        // 将每一组转换为二维数组的一行
        List<Comment[]> result = groupedComments.values().stream()
                .map(group -> group.toArray(new Comment[0]))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public void deleteComment(int commentId) {
        commentMapper.deleteById(commentId);
    }
}
