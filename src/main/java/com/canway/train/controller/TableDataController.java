package com.canway.train.controller;

import com.canway.train.bean.ResultBean;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/tableData")
@RestController
public class TableDataController {

    @GetMapping("/result/train/{trainingId}")
    public ResultBean getResult(@PathVariable Long trainingId){
        return ResultBean.success();
    }

    @GetMapping("/groupScore/train/{trainingId}")
    public ResultBean getGroupScore(@PathVariable Long trainingId){
        return ResultBean.success();
    }

}
