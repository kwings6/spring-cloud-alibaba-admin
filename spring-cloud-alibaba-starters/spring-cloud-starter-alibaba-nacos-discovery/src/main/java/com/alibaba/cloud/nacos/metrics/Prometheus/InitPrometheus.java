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

import com.alibaba.cloud.nacos.metrics.aop.interceptor.RestTemplateInterceptor;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitPrometheus implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    PrometheusMeterRegistry meterRegistry;

    @Autowired
    RestTemplateInterceptor restTemplateInterceptor;
    /**
     * 将Metrics标签注册到暴露给Prometheus监听的端口上
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        CollectorRegistry prometheusRegistry = meterRegistry.getPrometheusRegistry();

        prometheusRegistry.register(MyMetrics.baseCounter);
        prometheusRegistry.register(MyMetrics.baseGauge);
    }
}
