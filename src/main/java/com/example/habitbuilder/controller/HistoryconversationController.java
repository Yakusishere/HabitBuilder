package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.HistoryConversation;
import com.example.habitbuilder.service.IHistoryConversationService;
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
@RequestMapping("/historyconversation")
public class HistoryConversationController {
    @Autowired
    private IHistoryConversationService historyConversationService;

    // 增加历史对话
    @PostMapping
    public boolean addHistoryConversation(@RequestBody HistoryConversation historyConversation) {
        return historyConversationService.save(historyConversation);
    }

    // 删除历史对话
    @DeleteMapping("/{id}")
    public boolean deleteHistoryConversation(@PathVariable Integer id) {
        return historyConversationService.removeById(id);
    }

    // 更新历史对话
    @PutMapping
    public boolean updateHistoryConversation(@RequestBody HistoryConversation historyConversation) {
        return historyConversationService.updateById(historyConversation);
    }

    // 查询所有历史对话
    @GetMapping
    public List<HistoryConversation> getAllHistoryConversations() {
        return historyConversationService.list();
    }

    // 根据 ID 查询历史对话
    @GetMapping("/{id}")
    public HistoryConversation getHistoryConversationById(@PathVariable Integer id) {
        return historyConversationService.getById(id);
    }
}
