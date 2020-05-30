package com.xueyong.springbootapi.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyong.springbootapi.entity.Student;
import com.xueyong.springbootapi.entity.StudentVo;
import com.xueyong.springbootapi.entity.SudentHobbyRelation;
import com.xueyong.springbootapi.service.IStudentService;
import com.xueyong.springbootapi.service.ISudentHobbyRelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author xueyong
 * @since 2020-05-27
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    IStudentService iStudentService;
    @Autowired
    ISudentHobbyRelationService iSudentHobbyRelationService;
    //进行列表
    @GetMapping("list")
    public IPage<Student> list(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "2") Integer pageSize,Student student){
        System.out.println("sdaaaaaaaaaaa");

        return iStudentService.getPageInfo(pageNum,pageSize,student);
    }
    //进行单删
    @PostMapping("delete")
    public Integer delete(Integer id){
        System.out.println("删除的id是+"+id);
        int delete = iStudentService.getBaseMapper().deleteById(id);
        return delete;
    }
    //进行添加
    @PostMapping("add")
    public Boolean add(@RequestBody StudentVo studentVo){
        studentVo.setCreateTime(LocalDateTime.now());
        //先保存到student表里面
        boolean save = iStudentService.save(studentVo);
        //保存兴趣
        List<Integer> hobbyIdList = studentVo.getHobbyIdList();
        //实例化爱好表
        List<SudentHobbyRelation> relationlist =  new ArrayList<>();
        //根据兴趣的集合，把hobbyId循环
        hobbyIdList.forEach(hobbyId->{
            SudentHobbyRelation sudentHobbyRelation = new SudentHobbyRelation(null,studentVo.getId(),hobbyId);
           //把每条数据保存到实例化爱好表里
            relationlist.add(sudentHobbyRelation);
        });
        //保存进去以后直接保存到兴趣表的数据库里
        return iSudentHobbyRelationService.saveBatch(relationlist);
    }
    //查询单条数据
    @GetMapping("selectId")
    public StudentVo selectId(Integer id){
        StudentVo studentVo = new StudentVo();
        ArrayList<Integer> list = new ArrayList<>();
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("student_id",id);
        List<SudentHobbyRelation> sudentHobbyRelations = iSudentHobbyRelationService.getBaseMapper().selectByMap(objectObjectHashMap);
        for (SudentHobbyRelation su:sudentHobbyRelations) {
            list.add(su.getHobbyId());
            System.out.println(su);
            studentVo.setHobbyIdList(list);
        }
        Student student = iStudentService.getBaseMapper().selectById(id);
        studentVo.setStudent(student);
        BeanUtils.copyProperties(student,studentVo);
        return studentVo;
    }
    @PostMapping("update")
    public Integer update(@RequestBody StudentVo studentVo){
        System.out.println(studentVo);
            //修改主表
        int i = iStudentService.getBaseMapper().updateById(studentVo);
        //找到中间表删掉在添加
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("student_id",studentVo.getId());
        int i1 = iSudentHobbyRelationService.getBaseMapper().deleteByMap(objectObjectHashMap);
        List<Integer> hobbyIdList = studentVo.getHobbyIdList();
        //实例化爱好表
        List<SudentHobbyRelation> relationlist =  new ArrayList<>();
        hobbyIdList.forEach(hobbyId->{
            System.out.println("传过来的id是"+hobbyId);
            SudentHobbyRelation sudentHobbyRelation = new SudentHobbyRelation(null,studentVo.getId(),hobbyId);
            //把每条数据保存到实例化爱好表里
            relationlist.add(sudentHobbyRelation);
        });
        iSudentHobbyRelationService.saveBatch(relationlist);
        return i;
    }
    @PostMapping("deletebath")
    public boolean deleteBath(@RequestBody StudentVo studentVo){
        System.out.println(studentVo);
        boolean b = iStudentService.removeByIds(studentVo.getDeleteList());
        return b;
    }
}
