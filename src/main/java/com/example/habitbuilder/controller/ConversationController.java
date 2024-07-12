package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Conversation;
import com.example.habitbuilder.pojo.HistoryConversation;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.service.IConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-06
 */
@RestController
@RequestMapping("/conversation")
public class ConversationController {
    @Autowired
    private IConversationService conversationService;
    //AI问答
    @PostMapping ("/AI")
    public Result AI(@RequestParam String question,@RequestPart HistoryConversation historyConversation){
        String answer=conversationService.AI(question);
        int userId= historyConversation.getUserId();
        int historyConversationId=historyConversation.getHistoryConversationId();
        if(answer=="error"){
            return Result.error("回答失败");
        }else{
            conversationService.setConversation(question,answer,userId,historyConversationId);
            return Result.success(answer,"成功回答");
        }
    }
    // 增加对话
    @PostMapping("/addConversation")
    public Result addConversation(@RequestBody Conversation conversation) {
        conversation.setCreateTime(LocalDateTime.now());
        if(conversationService.save(conversation)){
            return Result.success("添加成功");
        }else{
            return Result.error("添加失败");
        }
    }

    // 删除对话
    @DeleteMapping("/deleteConversation")
    public Result deleteConversation(Integer id) {
        if(conversationService.removeById(id)){
            return Result.success("删除成功");
        }else{
            return Result.error("删除失败");
        }
    }

    // 更新对话
    @PutMapping("/updateConversation")
    public Result updateConversation(@RequestBody Conversation conversation) {
        if(conversationService.updateById(conversation)){
            return Result.success("更新成功");
        }else{
            return Result.error("更新失败");
        }
    }

    // 查询所有对话
    @GetMapping("/getAllConversations")
    public Result getAllConversations() {
        List<Conversation>conversations=conversationService.list();
        if(conversations.isEmpty()){
            return Result.error("没有对话");
        }else{
            return Result.success(conversations,"查询成功");
        }
    }

    // 根据 ID 查询对话
    @GetMapping("/getConversationByHistoryConversationId")
    public Result getConversationByHistoryConversationId(int historyConversationId) {
        List<Conversation> conversation = conversationService.getByHistoryConversation(historyConversationId);
        if(conversation==null){
            return Result.error("该对话不存在");
        }else{
            return Result.success(conversation,"查询成功");
        }
    }
}
