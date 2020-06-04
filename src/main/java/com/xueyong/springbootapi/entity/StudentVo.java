package com.xueyong.springbootapi.entity;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class StudentVo extends Student{
    private List<Integer> hobbyIdList;
    private Student student;
   private List<Integer> deleteList;
   private String hobbyNames;
   //添加时用到的id
   private List<Integer> selectAreaIdList;
   //省的姓名
   private String provinceName;
   //市的姓名
   private String cityName;
   //区的姓名
   private String aeaName;
}
