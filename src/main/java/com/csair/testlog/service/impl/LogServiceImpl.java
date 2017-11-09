package com.csair.testlog.service.impl;

import com.csair.testlog.Entity.pojo.AppLog;
import com.csair.testlog.Entity.vo.QueryVo;
import com.csair.testlog.controller.LogController;
import com.csair.testlog.mapper.LogMapper;
import com.csair.testlog.service.LogService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 日志操作业务层实现类
 *
 * @author LW_Fung
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;
    private static final Logger LOGGER = Logger.getLogger(LogController.class);

    /**
     * 向数据库写入一条记录
     *
     * @param appLog：一条记录的实体对象
     */
    public void saveLog(AppLog appLog) {
        logMapper.insert(appLog);
    }

    /**
     * 条件查询
     *
     * @param queryVo：查询条件实体对象
     * @return 结果集合
     */
    public List<AppLog> queryLog(QueryVo queryVo) {
        try {
            String timeStart = queryVo.getTimeStart();
            String timeEnd = queryVo.getTimeEnd();
            String className = queryVo.getClassName();
            String level = queryVo.getLevel();
            String info = queryVo.getLogInfo();
            String page = queryVo.getPage();

            Date _timeStart;
            if (StringUtils.isEmpty(timeStart))
                _timeStart = new Date();
            else
                _timeStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStart);

            Date _timeEnd;
            if (StringUtils.isEmpty(timeEnd))
                _timeEnd = new Date();
            else
                _timeEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeEnd);

            if (_timeStart.getTime() > _timeEnd.getTime())
                _timeEnd = _timeStart;

            if (StringUtils.isEmpty(className))
                className = "";
            if (StringUtils.isEmpty(level))
                level = "INFO";
            if (StringUtils.isEmpty(info))
                info = "";
            if (StringUtils.isEmpty(page))
                page = "1";

            return logMapper.queryLog(_timeStart, _timeEnd, className, level, "%" + info + "%", (Integer.parseInt(page)-1)*10);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return null;

    }

    /**
     * 获取条件查询结果条目数
     *
     * @param queryVo：查询条件实体对象
     * @return 结果条目数
     */
    public int queryCount(QueryVo queryVo) {
        try {
            String timeStart = queryVo.getTimeStart();
            String timeEnd = queryVo.getTimeEnd();
            String className = queryVo.getClassName();
            String level = queryVo.getLevel();
            String info = queryVo.getLogInfo();

            Date _timeStart;
            if (StringUtils.isEmpty(timeStart))
                _timeStart = new Date();
            else
                _timeStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStart);

            Date _timeEnd;
            if (StringUtils.isEmpty(timeEnd))
                _timeEnd = new Date();
            else
                _timeEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeEnd);

            if (_timeStart.getTime() > _timeEnd.getTime())
                _timeEnd = _timeStart;

            if (StringUtils.isEmpty(className))
                className = "";
            if (StringUtils.isEmpty(level))
                level = "INFO";
            if (StringUtils.isEmpty(info))
                info = "";
            return logMapper.queryCount(_timeStart, _timeEnd, className, level, "%" + info + "%");
        } catch (ParseException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    /**
     * 查找全部数据
     *
     * @return 结果集合
     */
    public List<AppLog> queryAll() {
        return logMapper.select(null);
    }
}
