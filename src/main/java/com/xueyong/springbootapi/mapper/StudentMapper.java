package com.xueyong.springbootapi.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyong.springbootapi.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author xueyong
 * @since 2020-05-27
 */
public interface StudentMapper extends BaseMapper<Student> {

    IPage<Student> getPageInfo(@Param("page") Page page, @Param("student") Student student);
}
