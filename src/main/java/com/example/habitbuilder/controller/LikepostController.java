package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.serviceImpl.LikepostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-06
 */
@RestController
@RequestMapping("/likepost")
public class LikepostController {
/*	@Autowired
	LoginHelper loginHelper;*/
	@Autowired
	LikepostServiceImpl likepostService;

	@PostMapping("/add/{postId}")
	public Result addLikePost(@RequestHeader("Authorization") String token,@PathVariable int postId) {
		if (!likepostService.likePost(token, postId)) {
			return Result.error("重复点赞");
		}
		return Result.success("点赞帖子成功");
	}

	@DeleteMapping("/deleteLikePost/{postId}")
	public Result deleteLikePost(@RequestHeader("Authorization") String token, @PathVariable int postId) {
		if (!likepostService.deleteLikePost(token, postId)) {
			return Result.error("用户未点赞过此帖子");
		}
		return Result.success("取消帖子点赞成功");
	}

/*	@GetMapping("/getLikePosts")
	public Result getLikePosts(@RequestHeader String token) {
		int userId = loginHelper.getUserId(token);
		return Result.success(likepostService.getLikePosts(userId), "获取喜欢列表成功");
	}

	@GetMapping("/getIfLikePost/{postId}")
	public Result getIfLikePost(@RequestHeader String token, @PathVariable int postId) {
		return Result.success(likepostService.getIfLikePost(loginHelper.getUserId(token), postId));
	}

	@GetMapping("/getLikePostByUserId")
	public Result getLikePostByUserId(@RequestHeader String token) {
		List<Post> posts = likepostService.getLikePostByUserId(loginHelper.getUserId(token));
		if (posts.isEmpty()) {
			return Result.error("无点赞过的帖子");
		} else {
			return Result.success(posts, "获取成功");
		}
	}*/
}
