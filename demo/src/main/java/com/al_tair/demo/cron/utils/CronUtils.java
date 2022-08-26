package com.al_tair.demo.cron.utils;

import com.al_tair.demo.cron.domain.Cron;
import com.alibaba.druid.util.StringUtils;
import io.jsonwebtoken.lang.Assert;
import org.springframework.scheduling.support.CronSequenceGenerator;

import java.util.*;

/**
 * @author Al_tair
 * @date 2022/8/12-14:46
 */
public class CronUtils {
    /**
     * Cron 表达式生成器
     * @param cron
     * @return
     */
    public static String cornGenerator(Cron cron) {
        String expression = cron.getExpression();
        // jType 调度方式[0： 周期调度 1： 定时调度 2： 被动采集]

        String template = "";
        List<Object> args = new ArrayList<>();

        if(StringUtils.equals(String.valueOf(cron.getType()), "1")){

            Calendar cal = Calendar.getInstance();
            cal.setTime(DateUtils.parseDate(cron.getExecuteTime(), DateUtils.DATE_FORMAT_DATETIME));
            template = "%s %s %s %s %s ? %s";
            args.addAll(Arrays.asList(cal.get(Calendar.SECOND),
                    cal.get(Calendar.MINUTE),
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.YEAR)));
            expression = String.format(template, args.toArray());

        } else if(StringUtils.equals(String.valueOf(cron.getType()), "0")){
            // jExpressionType 作业调度表达式类型[0：被动 1：自定义 2：每年 3：每月 4：每周 5：每天 6：每小时 7: 每分钟]
            String jExpressionType = String.valueOf(cron.getExpressionType());
            if(Arrays.asList("1","3", "4", "5", "6", "7").contains(jExpressionType)){
                if (StringUtils.equals(jExpressionType, "7")) {
                    template = "%s 0/%s * * * ?";
                    args.addAll(Arrays.asList(cron.getSecond(),
                            cron.getMinute()));
                } else if (StringUtils.equals(jExpressionType, "6")) {
                    template = "%s %s 0/%s * * ?";
                    args.addAll(Arrays.asList(cron.getSecond(),
                            cron.getMinute(),
                            cron.getHour()));
                } else if (StringUtils.equals(jExpressionType, "5")) {
                    template = "0 %s %s * * ?";
                    args.addAll(Arrays.asList(cron.getMinute(),
                            cron.getHour()));
                } else if (StringUtils.equals(jExpressionType, "4")) {
                    template = "0 %s %s ? * %s";

                    args.addAll(Arrays.asList(cron.getMinute(),
                            cron.getHour(),
                            StringUtil.join(cron.getDateOfWeek(), ",")));
                } else if (StringUtils.equals(jExpressionType, "3")) {
                    template = "0 %s %s %s * ?";
                    args.addAll(Arrays.asList(cron.getMinute(),
                            cron.getHour(),
                            StringUtil.join(cron.getDateOfMonth(), ",")));
                }
                expression = StringUtils.equals("1", jExpressionType) ? expression : String.format(template, args.toArray());
                boolean isValid = CronSequenceGenerator.isValidExpression(expression);
                Assert.isTrue(isValid, "cron表达式不合规范");
                cron.setExpression(expression);
            }
        }
        return expression;
    }

    /**
     * Cron 表达式解析器
     * @param cron
     */
    public static void parseCornExpression(Cron cron){
        // jType 调度方式[0： 周期调度 1： 定时调度 2： 被动采集]
        // jExpressionType 作业调度表达式类型[0：被动 1：自定义 2：每年 3：每月 4：每周 5：每天 6：每小时 7: 每分钟]
        if(StringUtils.equals(String.valueOf(cron.getType()), "1")){

            String[] fields = org.springframework.util.StringUtils.tokenizeToStringArray(cron.getExpression(), " ");
            Date executeDate = DateUtils.parseDate(
                    fields[6] +
                            String.format("%02d", Integer.parseInt(fields[4])) +
                            String.format("%02d", Integer.parseInt(fields[3])) +
                            String.format("%02d", Integer.parseInt(fields[2])) +
                            String.format("%02d", Integer.parseInt(fields[1])) +
                            String.format("%02d", Integer.parseInt(fields[0])),
                    "yyyyMMddHHmmss");
            cron.setExecuteTime(DateUtils.getTimeStampStr(executeDate));
        } else if(StringUtils.equals(String.valueOf(cron.getType()), "0")) {
            String jExpressionType = String.valueOf(cron.getExpressionType());
            if(Arrays.asList("3", "4", "5", "6", "7").contains(jExpressionType)) {
                // 0 0 0 * * ?
                String[] fields = org.springframework.util.StringUtils.tokenizeToStringArray(cron.getExpression(), " ");
                if(StringUtils.equals(jExpressionType, "3")) {
                    cron.setDateOfMonth(Arrays.asList(fields[3].split(",")));
                    cron.setHour(Integer.parseInt(fields[2]));
                    cron.setMinute(Integer.parseInt(fields[1]));
                } else if (StringUtils.equals(jExpressionType, "4")) {
                    cron.setDateOfWeek(Arrays.asList(fields[5].split(",")));
                    cron.setHour(Integer.parseInt(fields[2]));
                    cron.setMinute(Integer.parseInt(fields[1]));
                } else if (StringUtils.equals(jExpressionType, "5")) {
                    cron.setHour(Integer.parseInt(fields[2]));
                    cron.setMinute(Integer.parseInt(fields[1]));
                } else if (StringUtils.equals(jExpressionType, "6")) {
                    cron.setHour(Integer.parseInt(fields[2].replace("0/", "")));
                    cron.setMinute(Integer.parseInt(fields[1]));
                    cron.setSecond(Integer.parseInt(fields[0]));
                } else if (StringUtils.equals(jExpressionType, "7")) {
                    cron.setMinute(Integer.parseInt(fields[1].replace("0/", "")));
                    cron.setSecond(Integer.parseInt(fields[0]));
                }
            }
        }
    }
}
