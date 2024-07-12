package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.Conversation;
import com.example.habitbuilder.pojo.HistoryConversation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-06
 */
public interface IHistoryConversationService extends IService<HistoryConversation> {

    HistoryConversation getHistoryConversation(HistoryConversation historyConversation);


    List<HistoryConversation> getByUserId(int userId);
}
