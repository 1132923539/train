package com.canway.train.controller;

import com.baomidou.mybatisplus.mapper.Condition;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.canway.train.service.KanBanService;
import com.canway.train.bean.ResultBean;
import com.canway.train.entity.KanBanDO;


@RequestMapping("/api/kanBan")
@RestController
public class KanBanController {

    @Autowired
    private KanBanService kanBanService;
    /**
     * 看板列表
     * @return
     */
    @PostMapping(value="/kanBanList")
    public ResultBean listAllKan(HttpServletRequest request){
        ResultBean resultBean = ResultBean.success();
        List<KanBanDO> kanBanList = kanBanService.selectList(Condition.create().eq("project_id",1));
        resultBean.setData(kanBanList);
        return resultBean;
    }





}
