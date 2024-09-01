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

import com.alibaba.cloud.nacos.metrics.registry.RpcStepMeterRegistry;
import feign.*;
import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

public class OpenFeignInterceptor implements ResponseInterceptor, RequestInterceptor {
    @Autowired
    private RpcStepMeterRegistry rpcStepMeterRegistry;

    RequestTemplate request;
    @Override
    public Object intercept(InvocationContext invocationContext, Chain chain) throws Exception {

        Response response = invocationContext.response();

        Counter qpsCounterRes = Counter.builder("spring-cloud.rpc.openfeign.qps.response")
                .description("Spring Cloud Alibaba QPS metrics when use OpenFeign RPC Call.")
                .baseUnit(TimeUnit.SECONDS.name())
                .tag("sca.openfeign.rpc.method", request.method().toString())
                .tag("sca.openfeign.rpc.url", request.url())
//                .tag("sca.openfeign.rpc.body", request.bodyTemplate())
                .tag("sca.openfeign.status", response.status() + "")
                .register(rpcStepMeterRegistry);

        qpsCounterRes.increment();
        return null;
    }

    @Override
    public ResponseInterceptor andThen(ResponseInterceptor nextInterceptor) {
        return ResponseInterceptor.super.andThen(nextInterceptor);
    }

    @Override
    public Chain apply(Chain chain) {
        return ResponseInterceptor.super.apply(chain);
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        this.request = requestTemplate;
    }
}
