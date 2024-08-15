package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.domain.vo.CommentVo;
import com.example.habitbuilder.domain.vo.ReplyVo;
import com.example.habitbuilder.mapper.*;
import com.example.habitbuilder.pojo.*;
import com.example.habitbuilder.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.habitbuilder.service.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private PostMapper postMapper;
	@Autowired
	private UserMapper userMapper;
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private IReplyService replyService;

    @Override
    public void addComment(Comment comment) { //评论
        List<Comment> commentList = commentMapper.selectList(null);
        /*if(!commentList.isEmpty()){
            commentList.sort((comment1,comment2)->comment2.getCommentCount() - comment1.getCommentCount()); //按commentCount倒序排序
            comment.setCommentCount(commentList.getFirst().getCommentCount()+1); // 取最大评论数+1
        }else{
            comment.setCommentCount(1);// 如果为空设置为1
        }*/
        int postId=comment.getPostId();
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("postId", postId);
        Post post=postMapper.selectOne(queryWrapper);
        post.setCommentCount(post.getCommentCount()+1);
        postMapper.updateById(post);
        commentMapper.insert(comment);
    }

    @Override
    public void addReplyComment(Comment comment) {
        commentMapper.insert(comment);
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("postId",comment.getPostId());
        Post post = postMapper.selectOne(queryWrapper);
        post.setCommentCount(post.getCommentCount()+1);
        postMapper.updateById(post);
    }

    @Override
    public List<Comment[]> getThisPostComments(int postId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("postId", postId);
        List<Comment>comments = commentMapper.selectList(wrapper);
        // 按 commentCount 分组
        /*Map<Integer, List<Comment>> groupedComments = comments.stream()*/
        // 将每一组转换为二维数组的一行
       /* List<Comment[]> result = groupedComments.values().stream()
                .map(group -> group.toArray(new Comment[0]))
                .collect(Collectors.toList());*/
        List<Comment[]> result = new ArrayList<>();
        return result;
    }

    @Override
    public void deleteComment(int commentId) {
        commentMapper.deleteById(commentId);
    }

    @Override
    public CommentVo ConvertToCommentVo(int userId, Comment comment){
        CommentVo vo = new CommentVo();
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserId,comment.getUserId()));
        vo.setCommentId(comment.getCommentId());
        vo.setPostId(comment.getPostId());
        vo.setContent(comment.getContent());
        vo.setUserId(comment.getUserId());
        vo.setNickName(user.getNickName());
        vo.setAvatarImg(user.getAvatarImg());
        vo.setCommentDate(comment.getCommentDate());
        vo.setLikeCount(comment.getLikeCount());
        vo.setReplyCount(comment.getReplyCount());
        List<Reply> replyList = replyMapper.selectList(new LambdaQueryWrapper<Reply>().eq(Reply::getCommentId,comment.getCommentId()));
        List<ReplyVo> replyVoList = new ArrayList<>();
        for(Reply reply:replyList){
            replyVoList.add(replyService.convertToReplyVo(userId,reply));
        }
        vo.setReplyVoList(replyVoList);
        return vo;
    }
}
