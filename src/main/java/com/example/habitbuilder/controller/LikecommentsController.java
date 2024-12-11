package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.service.ILikecommentsService;
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
@RequestMapping("/likecomments")
public class LikecommentsController {
	@Autowired
	ILikecommentsService likeCommentsService;

	@PostMapping("/add/{commentId}")
	public Result addLikeComment(@RequestHeader("Authorization") String token, @PathVariable int commentId) {
		if (!likeCommentsService.addLikeComment(token, commentId)) {
			return Result.error("重复点赞");
		}
		return Result.success("评论点赞成功");
	}

	@DeleteMapping("/deleteLikeComment/{commentId}")
	public Result deleteLikeComment(@RequestHeader("Authorization") String token, @PathVariable int commentId) {
		if (!likeCommentsService.deleteLikeComment(token, commentId)) {
			return Result.success("用户未点赞过该评论");
		}
		return Result.success("取消评论点赞成功");
	}

	/*@GetMapping("/getIfLikeComment/{commentId}")
	public Result getIfLikeComment(@RequestHeader("Authorization") String token, @PathVariable int commentId) {
		return Result.success(likeCommentsService.getIfLikeComment(token, commentId).toString(), "获取是否点赞成功");
	}*/

	@GetMapping("/getLikeComment")
	public Result getLikeComment(@RequestHeader("Authorization") String token) {
		return Result.success(likeCommentsService.getLikeComment(token), "获取点赞列表成功");
	}
}
