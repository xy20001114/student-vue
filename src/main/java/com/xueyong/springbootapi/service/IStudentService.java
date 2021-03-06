package com.xueyong.springbootapi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyong.springbootapi.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyong.springbootapi.entity.StudentVo;

import java.util.List;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author xueyong
 * @since 2020-05-27
 */
public interface IStudentService extends IService<Student> {

    IPage<StudentVo> getPageInfo(Integer pageNum, Integer pageSize, StudentVo studentvo);
}
