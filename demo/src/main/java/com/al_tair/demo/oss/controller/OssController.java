package com.al_tair.demo.oss.controller;

import com.al_tair.demo.oss.utils.AliyunOSSUtil;
import com.al_tair.demo.oss.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Al_tair
 * @date 2022/7/28-9:53
 */
@Api("oss")
@RestController
@RequestMapping("/oss")
public class OssController {

    @ResponseBody
    @PostMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "上传图片", notes = "上传图片", httpMethod = "POST")
//    @PostMapping(value = "/upload")
    public JsonResult upload(@RequestParam("file")MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            try {
                String filename = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/" +
                        System.currentTimeMillis() + "-" + file.getName();
                //上传原始图片到阿里云
                String uploadPath = AliyunOSSUtil.uploadAliyun(file, filename);
                return new JsonResult(true, "上传成功", uploadPath);
            } catch (Exception e) {
                return new JsonResult(false, "系统未知错误");
            }
        } else {
            return new JsonResult(false, "文件不能为空");
        }
    }
}


