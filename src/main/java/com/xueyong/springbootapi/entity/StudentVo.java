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
}
