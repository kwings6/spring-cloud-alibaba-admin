/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cloud.nacos.metrics.Prometheus;

import com.alibaba.cloud.nacos.metrics.registry.RpcStepMeterRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 描述：Gauge实现的自定义标签、指标
 *
 * @author CaiTianXin
 * @date 2021/1/21 10:52
 */
@Slf4j
public class MyMetrics {

    /**
     * 项目名，采用动态获取避免写死在一个系统
     * 格式：xxx_tps
     */
    public static String projectName;

    /**
     * 通过自定义工具类获取到application.yml上的service.code
     */
//    static {
//        projectName = GetYmlPropertiesUtil.getCommonYml("service.code") + "_tps";
//    }
    @Autowired
    private static RpcStepMeterRegistry rpcStepMeterRegistry;
    /**
     * 指标：{projectName}_tps
     * 标签：ip、time、localIp、path、method、code
     * 格式：antispam_v2_tps{ip="127.0.0.1",time="202101221406",localIp="10.10.10.234",path="/tq-anti-spam-admin/js/public/constant.js",method="GET",code="200",} 1.0
     */
    public static Gauge baseGauge = Gauge.build()
            .name(projectName)
            .labelNames("ip", "time", "localIp", "path", "method")
            .help("Five metrics for this label.")
            .register();

    public static Counter baseCounter = Counter.build()
            .name(projectName + "_total")
            .labelNames("ip", "time", "localIp", "path", "method", "code")
            .help("Six metrics for this label.")
            .register();

    /**
     * 累计一次请求
     * @param time 时间戳
     * @return boolean
     */
    public static boolean submitGaugeInc(String ip, String time, String path, String method) {
        // 获取到当前ip，如果ip获取失败，该方法直接当成成功返回。
        String localIp = getLocalIp();
        if (localIp != null) {
            baseGauge.labels(ip, time, localIp, path, method).inc();
        }
        return true;
    }

    public static boolean submitGaugeDec(String ip, String time, String path, String method) {
        // 获取到当前ip，如果ip获取失败，该方法直接当成成功返回。
        String localIp = getLocalIp();
        if (localIp != null) {
            baseGauge.labels(ip, time, localIp, path, method).dec();
        }
        return true;
    }

    public static boolean submitCounterInc(String ip, String time, String path, String method, String code) {
        // 获取到当前ip，如果ip获取失败，该方法直接当成成功返回。
        String localIp = getLocalIp();
        if (localIp != null) {
            baseCounter.labels(ip, time, localIp, path, method, code).inc();
        }
        return true;
    }

    /**
     * 通过java.net包获取当前ip
     * @return String
     */
    private static String getLocalIp() {
        String localIp = null;

        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException var2) {
            log.error("localip error");
        }

        return localIp;
    }
}


