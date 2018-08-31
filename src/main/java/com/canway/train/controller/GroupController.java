package com.canway.train.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.canway.train.bean.ResultBean;
import com.canway.train.bean.vo.GroupCreatorInfo;
import com.canway.train.bean.vo.GroupVO;
import com.canway.train.entity.GroupDO;
import com.canway.train.entity.TrainingDO;
import com.canway.train.service.GroupService;
import com.canway.train.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/group")
@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private TrainingService trainingService;

    /**
     * 创建分组
     * @param trainingId
     * @param groupCreatorInfo
     * @return
     */
    @PostMapping("/training/{trainingId}")
    public ResultBean creatorGroup(@PathVariable Long trainingId, @RequestBody GroupCreatorInfo groupCreatorInfo){
        if (trainingId == null){
            return ResultBean.fail(null,"培训id不能为空。",HttpStatus.BAD_REQUEST);
        }
        groupCreatorInfo.setTrainingId(trainingId);
        return groupService.creatorGroup(groupCreatorInfo);
    }

    /**
     * 修改分组
     * @param trainingId
     * @param id
     * @param groupDO
     * @return
     */
    @PutMapping("/training/{trainingId}/group/{id}")
    public ResultBean updateGroup(@PathVariable("trainingId") Long trainingId, @PathVariable("id") Long id,@RequestBody GroupDO groupDO){
        if (trainingId == null || id == null){
            return ResultBean.fail(null,"参数不能为空。",HttpStatus.BAD_REQUEST);
        }
        GroupDO selectById = groupService.selectById(id);
        if (selectById == null ){
            return ResultBean.fail(null,"没有对应的分组。",HttpStatus.NOT_FOUND);
        }
        groupDO.setTrainingId(trainingId);
        groupDO.setId(id);
        Boolean result = groupService.updateById(groupDO);
        if (result){
            return ResultBean.success(null,"分组修改成功");
        }else{
            return ResultBean.fail(null,"分组修改失败。",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //删除分组
    @DeleteMapping("/{id}")
    public ResultBean deleteGroup( @PathVariable("id") Long id){
        if (id == null){
            return ResultBean.fail(null,"参数不能为空。",HttpStatus.BAD_REQUEST);
        }
        GroupDO selectById = groupService.selectById(id);
        if (selectById == null ){
            return ResultBean.fail(null,"没有对应的分组。",HttpStatus.NOT_FOUND);
        }
        Boolean result =groupService.deleteGroup(id);
        if (result){
            return ResultBean.success(null,"分组删除成功");
        }else{
            return ResultBean.fail(null,"分组删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据分组id查询分组信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResultBean selectGroup(@PathVariable("id") Long id){
        if (id == null){
            return ResultBean.fail(null,"参数不能为空。",HttpStatus.BAD_REQUEST);
        }
        GroupDO selectById = groupService.selectById(id);
        if (selectById == null ){
            return ResultBean.fail(null,"没有对应的分组。",HttpStatus.NOT_FOUND);
        }
        return ResultBean.success(selectById);
    }

    /**
     * 根据培训id查询分组列表
     */
    @GetMapping("/training/{trainingId}")
    public ResultBean selectGroupByTrainingId(@PathVariable Long trainingId){
        if (trainingId == null){
            return ResultBean.fail(null,"参数不能为空。",HttpStatus.BAD_REQUEST);
        }
        List<GroupDO> list = groupService.selectList(new EntityWrapper<GroupDO>().eq("training_id",trainingId));
        if (list == null ){
            return ResultBean.fail(null,"没有对应的分组。",HttpStatus.NOT_FOUND);
        }
        return ResultBean.success(list);
    }

    @GetMapping("/list")
    public ResultBean selectGroupList(){
        List<TrainingDO> trainingDOList = trainingService.selectList(new EntityWrapper<TrainingDO>());
        List<GroupVO> list = groupService.selectGroupList(trainingDOList);
        return ResultBean.success(list);
    }





}
