package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.pojo.Conversation;
import com.example.habitbuilder.pojo.HistoryConversation;
import com.example.habitbuilder.mapper.HistoryConversationMapper;
import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.service.IHistoryConversationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
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
public class HistoryConversationServiceImpl extends ServiceImpl<HistoryConversationMapper, HistoryConversation> implements IHistoryConversationService {
    @Autowired
    private HistoryConversationMapper historyConversationMapper;

    @Override
    public HistoryConversation getHistoryConversation(HistoryConversation historyConversation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = historyConversation.getCreateTime().format(formatter);
        QueryWrapper<HistoryConversation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",historyConversation.getUserId()).eq("createTime", formattedDate);
        HistoryConversation newHistoryConversation = historyConversationMapper.selectOne(queryWrapper);
        return newHistoryConversation;
    }

    @Override
    public List<HistoryConversation> getByUserId(int userId) {
        QueryWrapper<HistoryConversation> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("userId",userId);
        return historyConversationMapper.selectList(QueryWrapper);
    }
}
