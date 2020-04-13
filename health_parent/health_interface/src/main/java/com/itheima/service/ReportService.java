package com.itheima.service;

import java.util.Map;

/**
 * 运营数据统计报表接口层
 * @author wangxin
 * @version 1.0
 */
public interface ReportService {
    /**
     * 运营数据统计报表
     * @return
     */
    Map<String,Object> getBusinessReportData() throws Exception;
}
