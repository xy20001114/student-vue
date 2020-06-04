package com.xueyong.springbootapi.entity;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class AreaVo extends Area {
    private List<AreaVo> subList;
}
