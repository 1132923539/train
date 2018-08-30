package com.canway.train.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.canway.train.bean.ResultBean;
import com.canway.train.bean.vo.GroupUserVO;
import com.canway.train.entity.GroupDO;
import com.canway.train.entity.GroupUserDO;
import com.canway.train.service.GroupService;
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

    @Autowired
    private GroupService groupService;

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
        Boolean result = groupUserService.creatorGroup(groupUserDO);
        if (result){
            return ResultBean.success(groupUserDO,"用户关联分组创建成功");
        }else{
            return ResultBean.fail(null,"创建用户关联分组失败。",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 修改用户关联分组
     * @return
     */
    @PutMapping("/training/{trainingId}/user/{userId}/group/{groupId}")
    public ResultBean updateGroupUser(@PathVariable("trainingId") Long trainingId,
                                      @PathVariable("userId") Long userId,@PathVariable("groupId") Long groupId){
        if (trainingId == null || userId == null || groupId == null){
            return ResultBean.fail(null,"参数不能为空",HttpStatus.BAD_REQUEST);
        }
        GroupUserDO groupUserDO = new GroupUserDO();
        groupUserDO.setGroupId(groupId);
        Boolean result = groupUserService.update(groupUserDO,new EntityWrapper<GroupUserDO>()
                .eq("training_id",trainingId).eq("user_id",userId));
        if (result){
            return ResultBean.success(groupUserDO,"用户关联分组修改成功");
        }else{
            return ResultBean.fail(null,"创建用户关联分组修改失败。",HttpStatus.INTERNAL_SERVER_ERROR);
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
     * 根据培训id和分组id查询用户信息
     */
    @GetMapping("/training/{trainingId}/group/{groupId}")
    public ResultBean selectGroupUserByGroupId(@PathVariable("trainingId") Long trainingId,@PathVariable("groupId") Long groupId){
        if (trainingId == null && groupId == null){
            return ResultBean.fail(null,"参数不能为空。",HttpStatus.BAD_REQUEST);
        }
        GroupDO group = groupService.selectById(groupId);
        if (group == null ){
            return ResultBean.fail(null,"无效的分组id",HttpStatus.BAD_REQUEST);
        }


        GroupUserVO groupUserVO = groupUserService.selectGroupUserByGroupId(trainingId,group);
        return ResultBean.success(groupUserVO);
        }


    /**
     * 根据培训id分组获取用户信息
     */
    @GetMapping("/training/{trainingId}")
    public ResultBean selectGroupUserByTrainingId(@PathVariable("trainingId") Long trainingId){
        if (trainingId == null){
            return ResultBean.fail(null,"参数不能为空。",HttpStatus.BAD_REQUEST);
        }
        List<GroupDO> groupDOList= groupService.selectList(new EntityWrapper<GroupDO>()
                                                .eq("training_id",trainingId));

        List<GroupUserVO> groupUserVOList = groupUserService.selectGroupUserByTrainingId(trainingId,groupDOList);

        return ResultBean.success(groupUserVOList);
    }



}
