package com.al_tair.demo.cron.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Al_tair
 * @date 2022/8/12-13:53
 */
@ApiModel("Cron表达式")
@Getter
@Setter
@ToString
public class Cron{

    @ApiModelProperty(value = "调度方式[0： 周期调度 1： 定时调度 ]", required = true)
    private Integer type;

    /** 作业调度表达式类型	0：被动 1：自定义 2：每年 3：每月 4：每周 5：每天 6：每小时 7: 每分钟*/
    @ApiModelProperty(value = "作业调度表达式类型[0：被动 1：自定义 2：每年 3：每月 4：每周 5：每天 6：每小时 7: 每分钟]", required = true)
    private Integer expressionType;

    @ApiModelProperty(value = "秒, 可用值0-59")
    private Integer second;

    @ApiModelProperty(value = "分钟, 可用值0-59")
    private Integer minute;

    @ApiModelProperty(value = "小时, 可用值0-23")
    private Integer hour;

    @ApiModelProperty(value = "每周几, 多个逗号分割,周一至周日 MON,TUE,WED,THU,FRI,SAT,SUN或者1-7, 1表示周日")
    private List<String> dateOfWeek;

    @ApiModelProperty(value = "每月几日, 多个逗号分割, 可用值1-31")
    private List<String> dateOfMonth;

    @ApiModelProperty(value = "自定义的时候必须填写")
    private String expression;

    @ApiModelProperty(value = "定时调度时间点, 格式: YYYY-MM-DD hh:mm:ss")
    private String executeTime;

    /** 作业开始执行时间 */
    @ApiModelProperty(value = "作业开始执行时间, 格式: YYYY-MM-DD hh:mm:ss", required = true)
    private String startTime;

    /** 作业结束执行时间 */
    @ApiModelProperty(value = "作业结束执行时间, 格式: YYYY-MM-DD hh:mm:ss", required = true)
    private String endTime;
}
