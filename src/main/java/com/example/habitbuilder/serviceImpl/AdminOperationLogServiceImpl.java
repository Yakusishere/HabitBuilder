package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.habitbuilder.pojo.AdminOperationLog;
import com.example.habitbuilder.service.AdminOperationLogService;
import com.example.habitbuilder.mapper.AdminOperationLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【admin_operation_log】的数据库操作Service实现
* @createDate 2024-12-04 10:49:15
*/
@Service
public class AdminOperationLogServiceImpl extends ServiceImpl<AdminOperationLogMapper, AdminOperationLog>
    implements AdminOperationLogService{

    @Autowired
    private AdminOperationLogMapper adminOperationLogMapper;

    @Override
    public void add(AdminOperationLog adminOperationLog) {
        adminOperationLogMapper.insert(adminOperationLog);
    }

    @Override
    public List<AdminOperationLog> selectByManagerId(String managerId) {
        QueryWrapper<AdminOperationLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("managerId", managerId);
        return adminOperationLogMapper.selectList(queryWrapper);
    }

    @Override
    public List<AdminOperationLog> selectAll() {
        return adminOperationLogMapper.selectList(null);
    }

}




