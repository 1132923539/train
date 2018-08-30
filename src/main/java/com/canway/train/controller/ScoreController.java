package com.canway.train.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.canway.train.bean.ResultBean;
import com.canway.train.bean.vo.GroupScoreVO;
import com.canway.train.bean.vo.ScoreVO;
import com.canway.train.entity.ScoreDO;
import com.canway.train.service.GroupService;
import com.canway.train.service.GroupUserService;
import com.canway.train.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/api/score")
@RestController
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupUserService groupUserService;

    /**
     * 创建评分
     */
    @PostMapping("/")
    public ResultBean selectScoreList(@RequestBody ScoreDO score){
        if (score != null && score.getTrainingId() != null && score.getUserId() != null && score.getGroupId() != null){
            Boolean result = scoreService.insert(score);
            if (result){
                return ResultBean.success(score);
            }
            return ResultBean.fail(null,"创建培训评分失败。",HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            return ResultBean.fail(null,"参数不能为空。",HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * 根据id修改评分
     * @param id
     * @param score
     * @return
     */
    @PutMapping("/{id}")
    public ResultBean updateScore(@PathVariable("id") Long id,@RequestBody ScoreDO score){
        if (id == null ){
            return ResultBean.fail(null,"参数不能为空",HttpStatus.BAD_REQUEST);
        }
        score.setId(id);
        Boolean result = scoreService.updateById(score);
        if (result){
            return ResultBean.success();
        }
        return ResultBean.fail(null,"培训评分修改失败。",HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /**
     * 根据id删除评分
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResultBean deleteScore(@PathVariable("id") Long id){
        if (id == null ){
            return ResultBean.fail(null,"参数不能为空",HttpStatus.BAD_REQUEST);
        }
        Boolean result = scoreService.deleteById(id);
        if (result){
            return ResultBean.success();
        }
        return ResultBean.fail(null,"培训评分删除失败",HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /**
     * 根据培训id，分组id，用户id查询评分
     * @param trainingId
     * @param groupId
     * @param userId
     * @return
     */
    @GetMapping("/training/{trainingId}/group/{groupId}/user/{userId}")
    public ResultBean selectScore(@PathVariable("trainingId") Long trainingId,@PathVariable("groupId") Long groupId,@PathVariable("userId") Long userId){
        if(trainingId != null && groupId != null && userId != null){
            List<ScoreDO> list = scoreService.selectList(new EntityWrapper<ScoreDO>().eq("training_id",trainingId)
                    .eq("user_id",userId).eq("group_id",groupId));
            if (list != null && list.size() >0 ){
                return ResultBean.success(list.get(0));
            }else{
                return ResultBean.fail(null,"没有对应的分组评分",HttpStatus.NOT_FOUND);
            }
        }else{
            return ResultBean.fail(null,"参数不能为空",HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 根据id获取评分
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResultBean selectScoreById(@PathVariable("id") Long id){
        if (id == null){
            return ResultBean.fail(null,"参数不能为空",HttpStatus.BAD_REQUEST);
        }
        ScoreDO scoreDO = scoreService.selectById(id);
        return ResultBean.success(scoreDO);
    }

    /**
     * 根据培训id获取所有用户给不同组评分信息
     * @param trainingId
     * @return
     */
    @GetMapping("/training/{trainingId}")
    public ResultBean selectScoreVOList(@PathVariable("trainingId") Long trainingId){
        if(trainingId == null){
            return ResultBean.fail(null,"参数不能为空",HttpStatus.BAD_REQUEST);
        }
        List<ScoreVO> list = scoreService.selectScoreVOList(trainingId);
        return ResultBean.success(list);
    }


    //获取用户可以评分的分组信息
    @GetMapping("/training/{trainingId}/user/{userId}")
    public ResultBean selectGroupList(@PathVariable("trainingId") Long trainingId,@PathVariable("userId") Long userId){

        List<GroupScoreVO> groupScoreVOList = scoreService.selectGroupList(trainingId,userId);

        return ResultBean.success(groupScoreVOList);
    }

}
