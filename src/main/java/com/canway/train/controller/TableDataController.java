package com.canway.train.controller;

import com.canway.train.bean.GroupScore;
import com.canway.train.bean.ResultBean;
import com.canway.train.bean.TotalRanking;
import com.canway.train.service.TableDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/tableData")
@RestController
public class TableDataController {

    @Autowired
    private TableDataService tableDataService;

    @GetMapping("/result/train/{trainingId}")
    public ResultBean getResult(@PathVariable Long trainingId){
        //获取分组
        //获取分组成员
        //获取成员的评分
        //计算分组的演讲得分
        //计算规则得分
        //计算规则减分
        //计算总分
        //排序，然后计算排名
        TotalRanking totalRanking = new TotalRanking();
        return ResultBean.success(totalRanking);
    }

    @GetMapping("/groupScore/train/{trainingId}")
    public ResultBean getGroupScore(@PathVariable Long trainingId){
        //获取分组
        //获取分组成员
        //获取成员的评分
        //计算每一组的总分
        //计算每一组的平均分
        //计算每一组的排名
        GroupScore groupScore = new GroupScore();
        return ResultBean.success(groupScore);
    }

}
