package com.canway.train.controller;

import com.canway.train.bean.ResultBean;
import com.canway.train.entity.TrainingDO;
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
@RequestMapping("/api/training")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    /**
     * 查出所有培训
     */
    @GetMapping("/trainingList")
    public ResultBean listAllTraining() {
        List<TrainingDO> trainingList = trainingService.selectList(null);
        if (trainingList == null) {
            trainingList = new ArrayList<>();
        }
        return ResultBean.success(trainingList, "success", HttpStatus.OK);
    }

    /**
     * 根据id查询
     *
     * @param id
     */
    @GetMapping(value = "/{id}")
    public ResultBean getTrainingById(@PathVariable Long id) {
        if (null == id) {
            return ResultBean.fail(null, "id不能为空", HttpStatus.BAD_REQUEST);
        }
        TrainingDO trainingDO = trainingService.selectById(id);
        if (null == trainingDO) {
            return ResultBean.fail(null, "没有对应的项", HttpStatus.NOT_FOUND);
        }
        return ResultBean.success(trainingDO, "success", HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResultBean insertTraining(@RequestBody TrainingDO trainingDO) {
        if (null == trainingDO) {
            return ResultBean.fail(null, "参数不能为空", HttpStatus.NOT_FOUND);
        }
        if (null == trainingDO.getSubject()) {
            return ResultBean.fail(null, "培训主题不能为空", HttpStatus.NOT_FOUND);
        }
        boolean insert = trainingService.insert(trainingDO);
        return ResultBean.success(insert, "success", HttpStatus.OK);
    }

    /**
     * 更新
     *
     * @param trainingDO
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResultBean updateTraining(@PathVariable Long id, @RequestBody TrainingDO trainingDO) {
        if (null == id) {
            return ResultBean.fail(null, "ID类型错误", HttpStatus.NOT_FOUND);
        }
        if (null == trainingDO.getSubject()) {
            return ResultBean.fail(null, "培训主题不能为空", HttpStatus.NOT_FOUND);
        }
        trainingDO.setId(id);
        boolean b = trainingService.updateById(trainingDO);
        return ResultBean.success(b, "success", HttpStatus.OK);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResultBean deleteTraining(@PathVariable Long id) {
        if (null == id) {
            return ResultBean.fail(null, "参数ID不能为空", HttpStatus.NOT_FOUND);
        }
        TrainingDO trainingDO = trainingService.selectById(id);
        if (null == trainingDO) {
            return ResultBean.fail(null, "培训不存在", HttpStatus.NOT_FOUND);
        }
        boolean b = trainingService.deleteById(id);
        return ResultBean.success(b, "success", HttpStatus.OK);
    }
}
