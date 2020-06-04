package com.xueyong.springbootapi.controller;


import com.xueyong.springbootapi.entity.Classes;
import com.xueyong.springbootapi.service.IClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 班级表 前端控制器
 * </p>
 *
 * @author xueyong
 * @since 2020-05-27
 */
@RestController
@RequestMapping("/classes")
//跨域
public class ClassesController {

    @Autowired
    IClassesService iclassesService;

    @GetMapping("selectclasses")
    public List<Classes> selectclasses(){
        return iclassesService.getBaseMapper().selectList(null);
    }
    
    @PostMapping("AddName")
    public Boolean show(@RequestBody Classes classes){
        System.out.println(classes);
        return iclassesService.save(classes);
    }
}
