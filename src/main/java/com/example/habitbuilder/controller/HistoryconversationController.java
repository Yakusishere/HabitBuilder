package com.example.habitbuilder.controller;

import com.example.habitbuilder.pojo.Conversation;
import com.example.habitbuilder.pojo.HistoryConversation;
import com.example.habitbuilder.pojo.Result;
import com.example.habitbuilder.service.IConversationService;
import com.example.habitbuilder.service.IHistoryConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
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
@RequestMapping("/historyconversation")
public class HistoryConversationController {
    @Autowired
    private IHistoryConversationService historyConversationService;
    @Autowired
    private IConversationService iConversationService;
    @Qualifier("conversionService")

    // 增加历史对话
    @PostMapping("/addHistoryConversation")
    public Result addHistoryConversation(@RequestBody HistoryConversation historyConversation) {
        historyConversation.setCreateTime(LocalDateTime.now());
        if(historyConversationService.save(historyConversation)) {
            HistoryConversation newHistoryConversation=historyConversationService.getHistoryConversation(historyConversation);
            return Result.success(newHistoryConversation,"添加成功");
        }else{
            return Result.error("添加失败");
        }
    }

    // 删除历史对话
    @DeleteMapping("/deleteHistoryConversation")
    public Result deleteHistoryConversation(Integer id) {
        if(historyConversationService.removeById(id)){
            return Result.success("删除成功");
        }else{
            return Result.error("删除失败");
        }
    }

    // 更新历史对话
    @PutMapping("/updateHistoryConversation")
    public Result updateHistoryConversation(@RequestBody HistoryConversation historyConversation) {
        if(historyConversationService.updateById(historyConversation)){
            return Result.success("更新成功");
        }else{
            return Result.error("更新失败");
        }
    }

    // 查询所有历史对话
    @GetMapping("getAllHistoryConversations")
    public Result getAllHistoryConversations() {
        List<HistoryConversation>historyConversations=historyConversationService.list();
        if(historyConversations.isEmpty()){
            return Result.error("无历史记录");
        }else{
            return Result.success(historyConversations,"查询成功");
        }
    }

    // 根据 ID 查询历史对话
    @GetMapping("/getHistoryConversationByUserId")
    public Result getHistoryConversationByUserId(int userId) {
        List<HistoryConversation> historyConversation=historyConversationService.getByUserId(userId);
        if(historyConversation.isEmpty()){
            return Result.error("该对话不存在");
        }else{
            return Result.success(historyConversation,"查询成功");
        }
    }

    @GetMapping("/findConversationsByHistory")
    public Result findConversationsByHistory(int historyConversationId){
       List<Conversation>conversations= iConversationService.findConversationsByHistory(historyConversationId);
       if(conversations.isEmpty()){
           return Result.error("无历史记录");
       }else {
           return Result.success(conversations,"查找成功");
       }
    }
}
