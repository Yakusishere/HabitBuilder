package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.pojo.Conversation;
import com.example.habitbuilder.mapper.ConversationMapper;
import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.service.IConversationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
