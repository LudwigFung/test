package com.csair.testlog.mapper;

import com.csair.testlog.Entity.pojo.AppLog;
import com.github.abel533.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 日志业务持久层接口
 *
 * @author LW_Fung
 */
@Repository
public interface LogMapper extends Mapper<AppLog> {


    /**
     * 根据条件查找数据
     *
     * @param timeStart：起始时间
     * @param timeEnd：截止时间
     * @param className：类名
     * @param level：日志级别
     * @param info：日志内容
     * @param page：第几页
     * @return 结果集合
     */
    List<AppLog> queryLog(@Param("timeStart") Date timeStart,
                          @Param("timeEnd") Date timeEnd,
                          @Param("className") String className,
                          @Param("level") String level,
                          @Param("info") String info,
                          @Param("page") int page);

    /**
     * 计算条件查找结果的总条目数
     *
     * @param timeStart：起始时间
     * @param timeEnd：截止时间
     * @param className：类名
     * @param level：日志级别
     * @param info：日志内容
     * @return 结果的总条目数
     */
    int queryCount(@Param("timeStart") Date timeStart,
                   @Param("timeEnd") Date timeEnd,
                   @Param("className") String className,
                   @Param("level") String level,
                   @Param("info") String info);
}
