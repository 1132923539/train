package com.canway.train.controller;

import com.canway.train.bean.ResultBean;
import com.canway.train.entity.RuleScoreDO;
import com.canway.train.entity.TrainingDO;
import com.canway.train.service.RuleScoreService;
import com.canway.train.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eltons,  Date on 2018-08-29.
 */
@RestController
@RequestMapping("/api/ruleScore")
public class RuleScoreController {

    @Autowired
    private RuleScoreService ruleScoreService;

    /**
     * 更新
     *
     * @param ruleScoreDO
     */
    @PutMapping(value = "/{id}")
    public ResultBean update(@PathVariable Long id, RuleScoreDO ruleScoreDO) {
        if (null == id) {
            return ResultBean.fail(null, "ID类型错误", HttpStatus.NOT_FOUND);
        }
        if (null == ruleScoreDO.getGroupId()) {
            return ResultBean.fail(null, "分组ID不能为空", HttpStatus.NOT_FOUND);
        }
        ruleScoreDO.setId(id);
        boolean b = ruleScoreService.updateById(ruleScoreDO);
        return ResultBean.success(b, "success", HttpStatus.OK);
    }


    public ResultBean list() {
        List<RuleScoreDO> ruleScoreList = ruleScoreService.selectList(null);
        if (ruleScoreList == null) {
            ruleScoreList = new ArrayList<>();
        }
        return ResultBean.success(ruleScoreList, "success", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResultBean delete(@PathVariable Long id) {
        if (null == id) {
            return ResultBean.fail(null, "参数ID不能为空", HttpStatus.NOT_FOUND);
        }
        RuleScoreDO ruleScoreDO = ruleScoreService.selectById(id);
        if (null == ruleScoreDO) {
            return ResultBean.fail(null, "条目不存在", HttpStatus.NOT_FOUND);
        }
        boolean b = ruleScoreService.deleteById(id);
        return ResultBean.success(b, "success", HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResultBean getById(@PathVariable Long id) {
        if (null == id) {
            return ResultBean.fail(null, "id不能为空", HttpStatus.BAD_REQUEST);
        }
        RuleScoreDO ruleScoreDO = ruleScoreService.selectById(id);
        if (null == ruleScoreDO) {
            return ResultBean.fail(null, "没有对应的项", HttpStatus.NOT_FOUND);
        }
        return ResultBean.success(ruleScoreDO, "success", HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResultBean insert(@RequestBody RuleScoreDO ruleScoreDO) {
        if (null == ruleScoreDO) {
            return ResultBean.fail(null, "参数不能为空", HttpStatus.NOT_FOUND);
        }
        if (null == ruleScoreDO.getGroupId()) {
            return ResultBean.fail(null, "分组ID不能为空不能为空", HttpStatus.NOT_FOUND);
        }
        boolean insert = ruleScoreService.insert(ruleScoreDO);
        return ResultBean.success(insert, "success", HttpStatus.OK);
    }
}
