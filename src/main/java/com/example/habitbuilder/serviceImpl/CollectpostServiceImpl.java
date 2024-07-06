package com.example.habitbuilder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.habitbuilder.pojo.Collectpost;
import com.example.habitbuilder.mapper.CollectpostMapper;
import com.example.habitbuilder.service.ICollectpostService;
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
public class CollectpostServiceImpl extends ServiceImpl<CollectpostMapper, Collectpost> implements ICollectpostService {
    @Autowired
    private CollectpostMapper collectpostMapper;

    @Override
    public void addCollection(Collectpost collectpost) {
        collectpostMapper.insert(collectpost);
    }

    @Override
    public void deleteCollection(int collectionId) {
        collectpostMapper.deleteById(collectionId);
    }

    @Override
    public List<Collectpost> getCollections(int userId) {
        QueryWrapper<Collectpost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        return collectpostMapper.selectList(queryWrapper);
    }

    @Override // 找一个post是否为收藏
    public String getPostCollections(int userId, int postId) {
        QueryWrapper<Collectpost> queryWrapper = new QueryWrapper<Collectpost>();
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("postId", postId);
        if(!collectpostMapper.selectList(queryWrapper).isEmpty()){ // 说明用户对帖子是否关注
            return "true";
        }
        else {
            return "false";
        }
    }
}
