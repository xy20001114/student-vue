package com.xueyong.springbootapi.service.impl;

import com.xueyong.springbootapi.entity.User;
import com.xueyong.springbootapi.mapper.UserMapper;
import com.xueyong.springbootapi.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xueyong
 * @since 2020-05-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
