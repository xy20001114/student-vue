package com.xueyong.springbootapi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyong.springbootapi.entity.Student;
import com.xueyong.springbootapi.entity.StudentVo;
import com.xueyong.springbootapi.mapper.StudentMapper;
import com.xueyong.springbootapi.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author xueyong
 * @since 2020-05-27
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Override
    public IPage<StudentVo> getPageInfo(Integer pageNum, Integer pageSize, StudentVo studentvo) {
        return this.getBaseMapper().getPageInfo(new Page(pageNum,pageSize),studentvo);
    }
}
