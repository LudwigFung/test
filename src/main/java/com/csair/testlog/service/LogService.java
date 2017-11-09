package com.csair.testlog.service;

import com.csair.testlog.Entity.pojo.AppLog;
import com.csair.testlog.Entity.vo.QueryVo;

import java.util.List;

/**
 * 日志操作业务层接口
 *
 * @author LW_Fung
 */
public interface LogService {

    /**
     * 向数据库写入一条记录
     *
     * @param appLog：一条记录的实体对象
     */
    void saveLog(AppLog appLog);

    /**
     * 条件查询
     *
     * @param queryVo：查询条件实体对象
     * @return 结果集合
     */
    List<AppLog> queryLog(QueryVo queryVo);

    /**
     * 获取条件查询结果条目数
     *
     * @param queryVo：查询条件实体对象
     * @return 结果条目数
     */
    int queryCount(QueryVo queryVo);

    /**
     * 查找全部数据
     *
     * @return 结果集合
     */
    List<AppLog> queryAll();
}
