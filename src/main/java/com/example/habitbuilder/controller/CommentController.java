package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Comment;
import com.example.habitbuilder.pojo.Post;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	ICommentService commentService;

	@GetMapping("/listByUserId")
	public Result listByUserId(@RequestHeader("Authorization") String token) {
		return Result.success(commentService.listByUserId(token), "获取用户评论列表成功");
	}

	@GetMapping("/getPostCommentSection")
	public Result listByPostId(@RequestParam int postId) {
		return Result.success(commentService.getPostCommentSection(postId), "获取该帖子评论区成功");
	}

	@PostMapping("/add")
	public Result addComment(@RequestHeader("Authorization") String token, @RequestBody Comment comment) {
		return Result.success(commentService.addComment(token, comment), "评论成功");
	}

	@DeleteMapping("/deleteComment")
	public Result deleteComment(@RequestParam int commentId) {
		commentService.deleteComment(commentId);
		return Result.success("成功删除评论");
	}
}
