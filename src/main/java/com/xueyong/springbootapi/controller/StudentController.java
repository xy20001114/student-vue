package com.xueyong.springbootapi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xueyong.springbootapi.common.FileUtils;
import com.xueyong.springbootapi.entity.*;
import com.xueyong.springbootapi.service.IAreaService;
import com.xueyong.springbootapi.service.IHobbyService;
import com.xueyong.springbootapi.service.IStudentService;
import com.xueyong.springbootapi.service.ISudentHobbyRelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    IHobbyService iHobbyService;
    @Autowired
    IAreaService iAreaService;
    @Value("${upload.url}")
    private String uploadUrl;
    @Value("${upload.path}")
    private String uploadPath;
    //进行列表
    @GetMapping("list")
    public IPage<StudentVo> list(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "2") Integer pageSize,StudentVo studentvo){
        IPage<StudentVo> pageInfo = iStudentService.getPageInfo(pageNum, pageSize, studentvo);
        List<StudentVo> records = pageInfo.getRecords();
        //循环的变量是ss
        records.forEach(ss->{
            ss.setHobbyNames(getHoppyNamesId(ss.getId()));
            //查找省的姓名
            Area province = iAreaService.getBaseMapper().selectById(ss.getProviceId());
            ss.setProvinceName(province.getName());
            //查找市的姓名
            Area city = iAreaService.getBaseMapper().selectById(ss.getCityId());
            ss.setCityName(city.getName());
            //查找区的姓名
            Area area = iAreaService.getBaseMapper().selectById(ss.getAreaId());
            ss.setAeaName(area.getName());
        });
        return pageInfo;
    }
    //进行单删
    @GetMapping("delete")
    public Integer delete(Integer id){
        System.out.println("删除的id是+"+id);
        int delete = iStudentService.getBaseMapper().deleteById(id);
        return delete;
    }
    //进行添加
    @PostMapping("add")
    public Boolean add(@RequestBody StudentVo studentVo){
        System.out.println(studentVo);
        studentVo.setCreateTime(LocalDateTime.now());
        List<Integer> selectAreaIdList = studentVo.getSelectAreaIdList();
        //给省赋值
        studentVo.setProviceId(selectAreaIdList.get(0));
        //给市赋值
        studentVo.setCityId(selectAreaIdList.get(1));
        //给区赋值
        studentVo.setAreaId(selectAreaIdList.get(2));
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
        //省，市，区的id


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
        List<Integer> selectAreaIdList = studentVo.getSelectAreaIdList();
        //给省赋值
        studentVo.setProviceId(selectAreaIdList.get(0));
        //给市赋值
        studentVo.setCityId(selectAreaIdList.get(1));
        //给区赋值
        studentVo.setAreaId(selectAreaIdList.get(2));
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
    //根据id查询兴趣的方法
    private String getHoppyNamesId(Integer stuid){
        final StringBuffer habbyNames=new StringBuffer();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("student_id",stuid);
        //查询关系表
        List<SudentHobbyRelation> list = iSudentHobbyRelationService.list(queryWrapper);
        //根据爱好的id查询爱好表
        //根据stream流读取，里面是循环的变量hobbyId存在map里，然后.collect转成list还可以转换成set集合
        List<Integer> collect = list.stream().map(relation -> relation.getHobbyId()).collect(Collectors.toList());

        List<Hobby> hobbies = iHobbyService.listByIds(collect);

        hobbies.forEach(hobby -> {
             habbyNames.append(hobby.getName()).append("/");
        });
        habbyNames.deleteCharAt(habbyNames.length()-1);
        return habbyNames.toString();
    }
    @PostMapping("file")
    public Object upload(@RequestParam("file")MultipartFile file){
        //返回的结果
        Map<String,Object> result = new HashMap<>();
        result.put("result",false);
        //文件名称
        String originalFilename = file.getOriginalFilename();
        //截取后缀名
        String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
        //拼接
        String fileName = UUID.randomUUID()+extName;
        //保存图片
        File saveFile = new File(uploadPath,fileName);
        try {
            file.transferTo(saveFile);
            result.put("result",true);
            result.put("imgUrl",uploadUrl+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
