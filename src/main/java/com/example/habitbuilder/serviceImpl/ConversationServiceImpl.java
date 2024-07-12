package com.example.habitbuilder.serviceImpl;

import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.Qwen2;
import com.example.habitbuilder.pojo.Conversation;
import com.example.habitbuilder.mapper.ConversationMapper;
import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.service.IConversationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-06
 */
@Service
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, Conversation> implements IConversationService {
    @Autowired
    private ConversationMapper conversationMapper;
    @Override
    public List<Conversation> findConversationsByHistory(int historyConversationId) {
        QueryWrapper<Conversation> conversationQueryWrapper = new QueryWrapper<>();
        conversationQueryWrapper.in("historyConversationId", historyConversationId);
        List<Conversation> conversations = conversationMapper.selectList(conversationQueryWrapper);
        return conversations;
    }

    @Override
    public String AI(String question) {
        String answer;
        try{
            answer=Qwen2.callWithMessage(question);
        }catch (ApiException | NoApiKeyException | InputRequiredException e){
            return "error";
        }
        return answer;
    }

    @Override
    public void setConversation(String question, String answer,int userId,int historyConversationId) {
        conversationMapper.insert(new Conversation(null,userId,question,answer, LocalDateTime.now(),historyConversationId));
    }

    @Override
    public List<Conversation> getByHistoryConversation(int historyConversationId) {
        QueryWrapper<Conversation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("historyConversationId", historyConversationId);
        return conversationMapper.selectList(queryWrapper);
    }
}
