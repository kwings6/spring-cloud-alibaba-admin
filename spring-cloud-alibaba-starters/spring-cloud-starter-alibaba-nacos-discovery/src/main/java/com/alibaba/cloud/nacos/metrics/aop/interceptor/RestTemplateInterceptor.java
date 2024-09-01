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
package com.alibaba.cloud.nacos.metrics.aop.interceptor;

import com.alibaba.cloud.nacos.metrics.registry.RpcPrometheusConfig;
import com.alibaba.cloud.nacos.metrics.registry.RpcRegistryConfig;
import com.alibaba.cloud.nacos.metrics.registry.RpcStepMeterRegistry;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusCounter;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.micrometer.prometheus.PrometheusNamingConvention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus.PrometheusProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor{

//    @Autowired
//    private RpcStepMeterRegistry rpcStepMeterRegistry;
//    @Autowired
//     CompositeMeterRegistry compositeMeterRegistry;
    @Autowired
    private PrometheusMeterRegistry prometheusMeterRegistry;

    private Counter qpsCounterRes;
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//        this.traceRequest((ClientHttpRequest) request, body);
//            compositeMeterRegistry.add(rpcStepMeterRegistry);
//            compositeMeterRegistry.add(prometheusMeterRegistry);
//            List<Meter> a = compositeMeterRegistry.getMeters();
//        for (Meter meter:
//             a) {
//            System.out.println(meter.toString());
//        }

        // qps metrics.
//        Counter qpsCounterReq = Counter.builder("spring-cloud.rpc.reactive.qps.request")
//                .description("Spring Cloud Alibaba QPS metrics when use Reactive RPC Call.")
//                .baseUnit(TimeUnit.SECONDS.name())
//                .tag("sca.reactive.rpc.method", request.getMethod() + "")
//                .tag("sca.reactive.rpc.url", request.getURI() + "")
//                .tag("sca.reactive.rpc.headers", request.getHeaders() + "")
//                .tag("sca.reactive.rpc.request", request.g + "")
//                .register(rpcStepMeterRegistry);
//
//        qpsCounterReq.increment();
//
//        HttpMethod method = request.getMethod();
//        System.out.println(method);

        ClientHttpResponse response = execution.execute(request, body);


         qpsCounterRes = Counter.builder("spring-cloud.rpc.restTemplate.qps.response")
                .description("Spring Cloud Alibaba QPS metrics when use resTemplate RPC Call.")
                .baseUnit(TimeUnit.SECONDS.name())
//                .tag("sca.resTemplate.rpc.method", request.getMethod() + "")
                .tag("sca.resTemplate.rpc", "url: " + request.getURI() + "  method: " + request.getMethod() + "  status: " + response.getStatusCode())
//                .tag("sca.resTemplate.rpc.headers", request.getHeaders() + "")
//                .tag("sca.resTemplate.rpc.response", response.getStatusCode() + "")
                .register(prometheusMeterRegistry);


//        PrometheusCounter prometheusCounter =  (PrometheusCounter) Counter.builder("spring-cloud.rpc.restTemplate.qps.response")
//                .description("Spring Cloud Alibaba QPS metrics when use resTemplate RPC Call.")
//                .baseUnit(TimeUnit.SECONDS.name())
//                .tag("sca.resTemplate.rpc.method", request.getMethod() + "")
//                .tag("sca.resTemplate.rpc.url", request.getURI() + "")
//                .tag("sca.resTemplate.rpc.headers", request.getHeaders() + "")
//                .tag("sca.resTemplate.rpc.response", response.getStatusCode() + "")
//                .register(new PrometheusMeterRegistry(PrometheusConfig.DEFAULT));


        qpsCounterRes.increment();

        return response;

    }

    public Counter getQpsCounterRes() {
        return qpsCounterRes;
    }
    //    private void traceRequest(ClientHttpRequest request, byte[] body) throws UnsupportedEncodingException {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (requestAttributes == null) {
//            return;
//        }
//        HttpServletRequest req = ((ServletRequestAttributes) requestAttributes).getRequest();
//        HttpHeaders headers = new HttpHeaders();
//
//        String globalTraceId = req.getHeader("global-trace-id");
//        if (globalTraceId == null) {
//            headers.add("global-trace-id", TraceThreadLocal.getGlobalTraceId());
//        } else {
//            headers.add("global-trace-id", globalTraceId);
//        }
//        String parentTraceId = req.getHeader("local-trace-id");
//        if (parentTraceId == null) {
//            headers.add("parent-trace-id", TraceThreadLocal.getLocalTraceId());
//        } else {
//            headers.add("parent-trace-id", parentTraceId);
//        }
//        request.getHeaders().putAll(headers);
//    }


}
