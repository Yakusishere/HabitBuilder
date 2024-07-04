package com.example.habitbuilder.serviceImpl;

import com.example.habitbuilder.pojo.Manager;
import com.example.habitbuilder.mapper.ManagerMapper;
import com.example.habitbuilder.service.IManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 实训小组
 * @since 2024-07-03
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager> implements IManagerService {

}
