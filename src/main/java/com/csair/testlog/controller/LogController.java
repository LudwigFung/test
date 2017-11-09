package com.csair.testlog.controller;

import com.csair.testlog.Entity.pojo.AppLog;
import com.csair.testlog.Entity.vo.QueryVo;
import com.csair.testlog.service.LogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LW_Fung
 */
@Controller
public class LogController {

    @Autowired
    private LogService logService;
    private static final String regEx = "(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3}) (\\[[\\w-]*])(\\[\\w* ]) ((\\w*\\.)+\\w+) - (.+)";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(LogController.class);

    /**
     * 页面跳转控制器
     *
     * @param page：视图名称
     * @return 根据视图跳转页面
     */
    @RequestMapping("{page}")
    public String toPage(@PathVariable String page) {
        return page;
    }

    /**
     * 将一条日志写入数据库
     *
     * @param appLogJson：AppLog对象的json串
     * @return
     */
    @RequestMapping(value = "/log/saveLog", method = RequestMethod.POST)
    @ResponseBody
    public String saveLog(@RequestBody String appLogJson){
        if (StringUtils.isEmpty(appLogJson)){
            LOGGER.error("~~~参数有误！");
            return "ERRP";
        }
        try {
            AppLog appLog = MAPPER.readValue(appLogJson, AppLog.class);
            logService.saveLog(appLog);
            return "OK";
        } catch (Exception e) {
            LOGGER.error("~~~参数有误！");
            return "ERRJ";
        }
    }

    /**
     * 解析日志文件，并将数据全部写到数据库中
     *
     * @return 写入成功跳转到查询页面，写入失败跳转到错误页面
     */
    @RequestMapping("/log/readLog")
    public String readLogs() {
        Stack<String> logs = readLog();
        try {
            while (!logs.isEmpty()) {
                String log = logs.pop();
                Matcher matcher = Pattern.compile(regEx).matcher(log);
                if (matcher.matches()) {
                    AppLog appLog = new AppLog();
                    appLog.setLogTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").parse(matcher.group(1)));
                    appLog.setThreadName(matcher.group(2).substring(1, matcher.group(2).indexOf("]")));
                    appLog.setLogLevel(matcher.group(3).substring(1, matcher.group(3).indexOf(" ]")));
                    appLog.setClassName(matcher.group(4));
                    appLog.setLogInfo(matcher.group(6));
                    logService.saveLog(appLog);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return "error";
        }
        return "redirect:/home";
    }

    /**
     * 解析日志文件
     *
     * @return 日志字符串集合
     */
    private Stack<String> readLog() {
        Stack<String> stack = new Stack<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(
                    LogController.class.getResourceAsStream("/fmm-qplatform.log"), "GBK"));
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = Pattern.compile(regEx).matcher(line);
                if (matcher.matches()) {
                    stack.push(line);
                } else {
                    String pop = stack.pop();
                    pop += line;
                    stack.push(pop);
                }
            }
            Stack<String> _stack = new Stack<String>();
            while (!stack.isEmpty())
                _stack.push(stack.pop());
            return _stack;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    LOGGER.error("~~~关闭IO流异常！" + e.getMessage());
                } finally {
                    br = null;
                }
            }
        }
        return null;
    }

    /**
     * 条件查询
     *
     * @param queryVo：查询条件实体对象
     * @return 跳转到结果展示页面
     */
    @RequestMapping(value = "/log/queryLog", method = RequestMethod.POST)
    public String query(QueryVo queryVo, Model model) {
        List<AppLog> appLogs = logService.queryLog(queryVo);
        int total = logService.queryCount(queryVo);
        model.addAttribute("appLogs", appLogs);
        model.addAttribute("queryVo", queryVo);
        model.addAttribute("total", total);
        model.addAttribute("totalPages", (total - 1) / 10 + 1);
        return "result";
    }

    /**
     * 查找数据库中的全部数据
     *
     * @return 结果集合
     */
    @RequestMapping("/log/queryAll")
    @ResponseBody
    public List<AppLog> queryAll() {
        return logService.queryAll();
    }


}
