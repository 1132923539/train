package com.canway.train.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.canway.train.bean.ResultBean;
import com.canway.train.entity.GroupUserDO;
import com.canway.train.service.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/groupUser")
@RestController
public class GroupUserController {

    @Autowired
    private GroupUserService groupUserService;

    /**
     * 创建用户关联分组
     * @param trainingId
     * @param groupUserDO
     * @return
     */
    @PostMapping("/training/{trainingId}")
    public ResultBean creatorGroup(@PathVariable Long trainingId, @RequestBody GroupUserDO groupUserDO){
        if (trainingId == null){
            return ResultBean.fail(null,"培训id不能为空。",HttpStatus.BAD_REQUEST);
        }
        groupUserDO.setTrainingId(trainingId);
        Boolean result = groupUserService.insert(groupUserDO);
        if (result){
            return ResultBean.success(groupUserDO,"用户关联分组创建成功");
        }else{
            return ResultBean.fail(null,"创建用户关联分组失败。",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //删除用户关联分组
    @DeleteMapping("/{id}")
    public ResultBean deleteGroup( @PathVariable("id") Long id){
        if (id == null){
            return ResultBean.fail(null,"参数不能为空。",HttpStatus.BAD_REQUEST);
        }
        GroupUserDO selectById = groupUserService.selectById(id);
        if (selectById == null ){
            return ResultBean.fail(null,"没有对应的用户关联分组。",HttpStatus.NOT_FOUND);
        }
        Boolean result =groupUserService.deleteById(id);
        if (result){
            return ResultBean.success(null,"用户关联分组删除成功");
        }else{
            return ResultBean.fail(null,"用户关联分组删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据培训id和分组id查询用户关联分组列表
     */
    @GetMapping("/training/{trainingId}/group/{groupId}")
    public ResultBean selectGroupByTrainingId(@PathVariable("trainingId") Long trainingId,@PathVariable("groupId") Long groupId){
        if (trainingId == null && groupId == null){
            return ResultBean.fail(null,"参数不能为空。",HttpStatus.BAD_REQUEST);
        }
        List<GroupUserDO> list = groupUserService.selectList(new EntityWrapper<GroupUserDO>()
                .eq("training_id",trainingId).eq("group_id",groupId));
        if (list == null ){
            return ResultBean.fail(null,"没有对应的分组用户信息。",HttpStatus.NOT_FOUND);
        }
        return ResultBean.success(list);
    }



}
