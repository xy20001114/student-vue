package com.xueyong.springbootapi.service.impl;

import com.xueyong.springbootapi.entity.Classes;
import com.xueyong.springbootapi.mapper.ClassesMapper;
import com.xueyong.springbootapi.service.IClassesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 班级表 服务实现类
 * </p>
 *
 * @author xueyong
 * @since 2020-05-27
 */
@Service
public class ClassesServiceImpl extends ServiceImpl<ClassesMapper, Classes> implements IClassesService {

}
