package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.Conversation;
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
public interface IConversationService extends IService<Conversation> {

    List<Conversation> findConversationsByHistory(int historyConversationId);

    String AI(String question);

    void setConversation(String question, String answer,int userId,int historyCoversationId);

    List<Conversation> getByHistoryConversation(int historyConversationId);
}
