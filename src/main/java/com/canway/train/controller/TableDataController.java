package com.canway.train.controller;

import com.canway.train.bean.RankVO;
import com.canway.train.bean.ResultBean;
import com.canway.train.service.TableDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/tableData")
@RestController
public class TableDataController {

    @Autowired
    private TableDataService tableDataService;

    @GetMapping("/rank/train/{trainingId}")
    public ResultBean getResult(@PathVariable Long trainingId){
        //获取分组
        Map<Long,RankVO> groupDOS =  tableDataService.getGroup(trainingId);
        //获取分组成员
        //获取成员的评分
        //计算分组的演讲得分
        //计算规则得分
        //计算规则减分
        //计算总分
        List<RankVO> rankVOS = new ArrayList<>();

        //排序，然后计算排名
        rankVOS = tableDataService.getSpeechTotal(groupDOS,trainingId);
        rankVOS = tableDataService.solveRuleScore(rankVOS,trainingId);
        return ResultBean.success(rankVOS);
    }

    @GetMapping("/speechRank/train/{trainingId}")
    public ResultBean getGroupScore(@PathVariable Long trainingId){
        //获取分组
        //获取分组成员
        Map<Long,RankVO> groupDOS =  tableDataService.getGroup(trainingId);
        //获取成员的评分
        //计算每一组的总分
        //计算每一组的平均分
        //计算每一组的排名
        List<RankVO> rankVOS = new ArrayList<>();

        rankVOS =  tableDataService.getSpeechTotal(groupDOS,trainingId);
        return ResultBean.success(rankVOS);
    }

}
