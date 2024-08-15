package com.example.habitbuilder.domain.vo;
import com.example.habitbuilder.pojo.Post;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PostVo {
	private Integer postId;

	private Integer userId;

	private String nickName;

	private String avatarImg;

	private Boolean isViewable;

	private String title;

	private String content;

	private LocalDate publishDate;

	private Integer likeCount;

	private Integer favCount;

	private Integer commentCount;

	private List<String> images;

	private List<CommentVo> commentVoList;

	public PostVo(){}

	/*PostVo(Post post){
		setPostId(post.getPostId());
		setUserId(post.getUserId());
		setNickName(user.getNickName());
		setAvatarImg(user.getAvatarImg());
		setIsViewable(post.getIsViewable());
		setTitle(post.getTitle());
		setContent(post.getContent());
		setPublishDate(post.getPublishDate());
		setLikeCount(post.getLikeCount());
		setFavCount(post.getFavCount());
		setCommentCount(post.getCommentCount());
	}*/
}
