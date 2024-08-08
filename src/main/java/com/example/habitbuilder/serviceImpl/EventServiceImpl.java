package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.mapper.PlanMapper;
import com.example.habitbuilder.pojo.Event;
import com.example.habitbuilder.mapper.EventMapper;
import com.example.habitbuilder.pojo.Plan;
import com.example.habitbuilder.pojo.User;
import com.example.habitbuilder.service.IEventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements IEventService {
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private PlanMapper planMapper;

    @Override
    public void createEvent(Event event) {
        LocalDate today = LocalDate.now();
        event.setExecutionDate(today);
        eventMapper.insert(event);
    }

    @Override
    public boolean changeEvent(int eventId, String eventDescription, LocalTime startTime, LocalTime endTime) {
        Event event = getById(eventId);
        if (event != null) {
            event.setDescription(eventDescription);
            event.setStartTime(startTime);
            event.setEndTime(endTime);
            updateById(event);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteEvent(Integer eventId) {
        Event event = getById(eventId);
        if (event != null) {
            removeById(eventId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean completeEvent(int eventId) {
        Event event = getById(eventId);

        if (event != null) {
            event.setIsCompleted(true);
            updateById(event);
            int planId = event.getPlanId();
            QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("planId", planId);
            List<Event> events = eventMapper.selectList(queryWrapper);
            int count = 0;
            for (int i = 0; i < events.size(); i++) {
                if (events.get(i).getIsCompleted()) {
                    count++;
                }
            }
            System.out.println(count);
            double completion_percentage = (count * 100) / (events.size());
            System.out.println(completion_percentage);
            QueryWrapper<Plan> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("planId", planId);
            Plan plan = planMapper.selectOne(queryWrapper1);
            plan.setCompletionPercentage((int) completion_percentage);
            planMapper.updateById(plan);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<Event> eventInPlan(LocalDate date, int planId) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(Map.of("planId", planId, "executionDate", date));
        List<Event> events = eventMapper.selectList(queryWrapper);
        events.sort(Comparator.comparing(Event::getStartTime));
        return events;
    }

    @Override
    public List<Event> dailyEvent(int userId, LocalDate date) {
        QueryWrapper<Plan> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("userId", userId);
        List<Plan> plans = planMapper.selectList(queryWrapper1);
        List<Integer> planIds = plans.stream()
                .map(Plan::getPlanId)
                .distinct()
                .collect(Collectors.toList());
        if (plans.isEmpty()) {
            return new ArrayList<>();
        }
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("planId", planIds).eq("executionDate", date);
        List<Event> events = eventMapper.selectList(queryWrapper);
        events.sort(Comparator.comparing(Event::getStartTime));
        return events;
    }

    @Override
    public List<Integer> findPlanIdByDate(LocalDate date) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("executionDate", date);
        List<Event> events = eventMapper.selectList(queryWrapper);
        return events.stream()
                .map(Event::getPlanId)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public int findPlanIdByEventId(int eventId) {
        Event event = getById(eventId);
        return event.getPlanId();
    }

    @Override
    public List<Event> getPlanEvents(int planId) {
        return eventMapper.selectList(new QueryWrapper<Event>().eq("planId", planId));
    }

    @Override
    public void addEvent(Event event) {
        eventMapper.insert(event);
    }

    @Override
    public Object setColor(int userId, LocalDate localDate) {
        class Temp {
            public LocalDate localDate;
            public int type;
            public int time;
        }
        LocalDate startDate = localDate.withDayOfMonth(1);
        LocalDate lastDayOfMonth = localDate.withDayOfMonth(localDate.lengthOfMonth());
        QueryWrapper<Plan> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("userId", userId);
        List<Plan> plans = planMapper.selectList(queryWrapper1);
        List<Integer> planIds = plans.stream()
                .map(Plan::getPlanId)
                .distinct()
                .collect(Collectors.toList());
        if (planIds.isEmpty()) {
            return new ArrayList();
        }
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("planId", planIds).between("executionDate", startDate.minusDays(1), lastDayOfMonth);

        List<Event> events = eventMapper.selectList(queryWrapper);

        // 按照日期分组
        Map<LocalDate, List<Event>> eventsGroupedByDate = events.stream()
                .collect(Collectors.groupingBy(Event::getExecutionDate));

        // 筛选出所有事件都完成的日期
        List<LocalDate> localDates = eventsGroupedByDate.entrySet().stream()
                .filter(entry -> entry.getValue().stream().allMatch(Event::getIsCompleted))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 创建并填充 temps 数组
        List<Temp> temps = new ArrayList<>();
        LocalDate dateIterator = startDate;

        while (!dateIterator.isAfter(lastDayOfMonth)) {
            Temp temp = new Temp();
            temp.localDate = dateIterator;
            temp.time = dateIterator.getDayOfMonth(); // 设置 time 为日期的号数
            if (localDates.contains(dateIterator)) {
                temp.type = 1;
            } else if (!eventsGroupedByDate.containsKey(dateIterator)) {
                temp.type = -1;
            } else {
                temp.type = 0;
            }
            temps.add(temp);
            dateIterator = dateIterator.plusDays(1);
        }
        return temps;
    }
}