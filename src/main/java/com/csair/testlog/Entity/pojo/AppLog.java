package com.csair.testlog.Entity.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 日志实体类
 *
 * @author LW_Fung
 */
@Table(name="app_log")
public class AppLog extends BasePojo {

    @Id
    private Integer logId;
    private Date logTime;
    private String threadName;
    private String logLevel;
    private String className;
    private String  logInfo;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    @Override
    public String toString() {
        return "AppLog{" +
                "logId=" + logId +
                ", logTime=" + logTime +
                ", threadName='" + threadName + '\'' +
                ", logLevel='" + logLevel + '\'' +
                ", className='" + className + '\'' +
                ", logInfo='" + logInfo + '\'' +
                '}';
    }
}
