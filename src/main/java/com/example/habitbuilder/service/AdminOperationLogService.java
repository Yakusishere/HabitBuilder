package com.example.habitbuilder.service;

import com.example.habitbuilder.pojo.AdminOperationLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【admin_operation_log】的数据库操作Service
* @createDate 2024-12-04 10:49:15
*/
public interface AdminOperationLogService extends IService<AdminOperationLog> {

    void add(AdminOperationLog adminOperationLog);

    List<AdminOperationLog> selectAll();

    List<AdminOperationLog> selectByManagerId(String managerId);

}
