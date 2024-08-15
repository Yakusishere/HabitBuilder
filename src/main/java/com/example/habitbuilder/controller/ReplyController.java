package com.example.habitbuilder.controller;

import com.example.habitbuilder.mapper.ReplyMapper;
import com.example.habitbuilder.pojo.Reply;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.service.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 实训小组
 * @since 2024-08-15
 */
@RestController
@RequestMapping("/reply")
public class ReplyController {
	@Autowired
	ReplyMapper replyMapper;
	@Autowired
	IReplyService replyService;

	/**
	 * 获取回复对话
	 *
	 * @param replyId 回复id
	 * @return {@link Result }
	 */
	@GetMapping("/getReplyConversation")
	public Result getReplyConversation(@RequestParam int replyId) {
		return Result.success(replyService.getReplyConversation(replyId), "成功获取回复对话");
	}

	@PostMapping("/add")
	public Result addReply(@RequestHeader("Authorization") String token, @RequestBody Reply reply) {
		replyService.addReply(token, reply);
		return Result.success("评论成功");
	}


	@PutMapping("/update")
	public Result updateReply(@RequestHeader("Authorization") String token, @RequestBody Reply reply) {
		if (replyService.updateReply(token, reply)){
			return Result.success("更新成功");
		}else {
			return Result.error("评论失败");
		}
	}
}
