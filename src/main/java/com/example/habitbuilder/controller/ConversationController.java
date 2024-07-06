package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Conversation;
import com.example.habitbuilder.service.IConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    // 增加对话
    @PostMapping
    public boolean addConversation(@RequestBody Conversation conversation) {
        return conversationService.save(conversation);
    }

    // 删除对话
    @DeleteMapping("/{id}")
    public boolean deleteConversation(@PathVariable Integer id) {
        return conversationService.removeById(id);
    }

    // 更新对话
    @PutMapping
    public boolean updateConversation(@RequestBody Conversation conversation) {
        return conversationService.updateById(conversation);
    }

    // 查询所有对话
    @GetMapping
    public List<Conversation> getAllConversations() {
        return conversationService.list();
    }

    // 根据 ID 查询对话
    @GetMapping("/{id}")
    public Conversation getConversationById(@PathVariable Integer id) {
        return conversationService.getById(id);
    }
}
