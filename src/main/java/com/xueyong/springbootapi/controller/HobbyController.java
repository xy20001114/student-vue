package com.xueyong.springbootapi.controller;


import com.xueyong.springbootapi.entity.Hobby;
import com.xueyong.springbootapi.service.IHobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 爱好表 前端控制器
 * </p>
 *
 * @author xueyong
 * @since 2020-05-27
 */
@RestController
@RequestMapping("/hobby")
public class HobbyController {

    @Autowired
    IHobbyService iHobbyService;
    @GetMapping("select")
    public List<Hobby> select(){
        return iHobbyService.getBaseMapper().selectList(null);
    }
}
