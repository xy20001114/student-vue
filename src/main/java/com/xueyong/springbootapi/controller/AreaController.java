package com.xueyong.springbootapi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xueyong.springbootapi.entity.Area;
import com.xueyong.springbootapi.entity.AreaVo;
import com.xueyong.springbootapi.service.IAreaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xueyong
 * @since 2020-05-27
 */
@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    IAreaService iAreaService;
    @GetMapping("selectProvince")
    public List<AreaVo> selectProvince(){
        //实例化实体类，返回的结果集
        ArrayList<AreaVo> areaVos = new ArrayList<>();
        QueryWrapper<Area> objectQueryWrapper = new QueryWrapper<>();
        //指定字段，提高查询效率
        objectQueryWrapper.select("id","name","pid");
        //查到所有省的结果集
        List<Area> provinceList = iAreaService.getBaseMapper().selectList(objectQueryWrapper);
       provinceList.forEach(province->{
           if (province.getPid()==1) {
               //实例化Areavo的实体类，因为要copy属性
               AreaVo areavo = new AreaVo();
               //把属性赋值到areavo里面，然后存到集合里面
               BeanUtils.copyProperties(province, areavo);
               areaVos.add(areavo);
           }
       });
       //查找市的数据
        areaVos.forEach(province->{
            ArrayList<AreaVo> cityList = new ArrayList<>();
            provinceList.forEach(city->{
                //市级的pid如果等于省级的id
                if(city.getPid().intValue()==province.getId().intValue()){
                    AreaVo cityVo = new AreaVo();
                    //将查到市级的属性copy
                    BeanUtils.copyProperties(city,cityVo);
                    cityList.add(cityVo);
                    //查询区的数据
                    List<AreaVo> areaList = new ArrayList<>();
                    provinceList.forEach(qu->{
                        //区的pid等于市级的id
                        if(qu.getPid().intValue()==city.getId().intValue()){
                            AreaVo quVo = new AreaVo();
                            BeanUtils.copyProperties(qu,quVo);
                            areaList.add(quVo);
                        };
                    });
                    //赋给children子类
                    cityVo.setSubList(areaList);
                };
            });
            //给赋值子
            province.setSubList(cityList);
        });
        return areaVos;
    }
}
