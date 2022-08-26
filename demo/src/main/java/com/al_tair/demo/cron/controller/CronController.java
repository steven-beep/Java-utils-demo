package com.al_tair.demo.cron.controller;

import com.al_tair.demo.cron.domain.Cron;
import com.al_tair.demo.cron.utils.CronUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


/**
 * @author Al_tair
 * @date 2022/8/26-16:59
 */

@Api(value = "Cron表达式生成和解析")
@RestController
@RequestMapping("/cron")
public class CronController {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CronController.class);

    /**
     *  讲解 cron 表达式
     *  Type：周期调度（0）
     *      需要填入参数：ExpressionType 作业调度表达式（0：被动 1：自定义 2：每年 3：每月 4：每周 5：每天 6：每小时 7: 每分钟）
     *      间隔时间是根据作业调度表达式来选择 dateOfMonth,dateOfWeek,hour,minute,second
     *      开始生效时间 StartTime 结束生效时间 EndTime
     *  Type：定时调度（1）
     *      需要填入参数：过期时间 executeTime
     *  最后返回的都是 cron 表达式：0 0/5 * * * ?  存放在 Expression
     *  格式：
     *     "cron": {
     *         "dateOfMonth": [ -- 月
     *             "0"
     *         ],
     *         "dateOfWeek": [ -- 周
     *             "0"
     *         ],
     *         "executeTime": "2022-08-26 16:12:40", -- 过期时间
     *         "Expression": "", -- corn 表达式
     *         "Type": 0, -- 调度方式
     *         "ExpressionType": 7, -- 作业调度表达式类型
     *         "StartTime": "2022-08-26 16:17:37", -- 开始生效时间
     *         "EndTime": "2022-09-02 16:12:37", -- 结束生效时间
     *         "hour": 16, -- 小时
     *         "minute": 5, -- 分钟
     *         "second": 0 -- 秒
     *     },
     */
    @ApiOperation("Cron表达式生成和解析")
    @PostMapping
    public void getCron(@RequestBody Cron cron) {
        // 生成 cron 表达式
        String expresion = CronUtils.cornGenerator(cron);
        Integer type = cron.getType();
        log.info(expresion);

        // 解析 cron 表达式
        // 传入调度类型 type 以及 cron 表达式 expresion 才可以解析
        cron = new Cron();
        cron.setExpression(expresion);
        cron.setType(type);
        CronUtils.parseCornExpression(cron);
        log.info(cron.toString());
    }
}
