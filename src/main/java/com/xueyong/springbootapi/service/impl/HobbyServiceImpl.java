package com.xueyong.springbootapi.service.impl;

import com.xueyong.springbootapi.entity.Hobby;
import com.xueyong.springbootapi.mapper.HobbyMapper;
import com.xueyong.springbootapi.service.IHobbyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 爱好表 服务实现类
 * </p>
 *
 * @author xueyong
 * @since 2020-05-27
 */
@Service
public class HobbyServiceImpl extends ServiceImpl<HobbyMapper, Hobby> implements IHobbyService {

}
