package com.canway.train;

import com.canway.train.controller.RuleScoreController;
import com.canway.train.controller.TrainingController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainApplicationTests {

    @Autowired
    private TrainingController trainingController;

    @Autowired
    private RuleScoreController ruleScoreController;

    @Test
    public void contextLoads() {
        System.out.println("--------------------------------" + trainingController.listAllTraining());
    }

    @Test
    public void ruleScoreTest() {
        System.out.println(ruleScoreController.getScoreList(1L));
    }

}
