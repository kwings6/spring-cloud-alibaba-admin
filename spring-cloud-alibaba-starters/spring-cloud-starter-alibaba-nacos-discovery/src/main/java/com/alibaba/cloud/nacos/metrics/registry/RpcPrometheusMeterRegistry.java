///*
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements.  See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.alibaba.cloud.nacos.metrics.registry;
//
//import io.micrometer.core.instrument.Clock;
//import io.micrometer.prometheus.PrometheusConfig;
//import io.micrometer.prometheus.PrometheusMeterRegistry;
//import io.prometheus.client.CollectorRegistry;
//import io.prometheus.client.exemplars.ExemplarSampler;
//
//public class RpcPrometheusMeterRegistry extends PrometheusMeterRegistry {
//    public RpcPrometheusMeterRegistry(RpcPrometheusConfig config) {
//        super(config);
//    }
//
//    public RpcPrometheusMeterRegistry(PrometheusConfig config, CollectorRegistry registry, Clock clock) {
//        super(config, registry, clock);
//    }
//
//    public RpcPrometheusMeterRegistry(PrometheusConfig config, CollectorRegistry registry, Clock clock, ExemplarSampler exemplarSampler) {
//        super(config, registry, clock, exemplarSampler);
//    }
//}
