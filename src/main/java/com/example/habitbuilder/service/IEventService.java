package com.example.habitbuilder.service;

import com.example.habitbuilder.domain.bo.EventBo;
import com.example.habitbuilder.domain.vo.EventVo;
import com.example.habitbuilder.pojo.Event;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
public interface IEventService extends IService<Event> {

   List<EventVo> getEventList(String token, EventBo bo);

   void addEvent(String token,Event event);

   void changeEvent(String token, Event event);

   void deleteEvent(String token, int eventId);

   boolean completeEvent(int eventId);

   List<EventVo> dailyEvent(String token,LocalDate date);

   int findPlanIdByEventId(int eventId);

   Object setColor(String token,LocalDate localDate);
}
