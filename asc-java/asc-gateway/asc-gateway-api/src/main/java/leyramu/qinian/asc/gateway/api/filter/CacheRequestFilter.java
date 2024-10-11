/*
 * Copyright (c) 2024 Leyramu. All rights reserved.
 * This project (Digitalization Education), including its source code, documentation, and any associated materials, is the intellectual property of Leyramu. No part of this software may be reproduced, distributed, or transmitted in any form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the copyright owner, Miraitowa_zcx, except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
 * For inquiries related to licensing or usage outside the scope of this notice, please contact the copyright holder at 2038322151@qq.com.
 * The author disclaims all warranties, express or implied, including but not limited to the warranties of merchantability and fitness for a particular purpose. Under no circumstances shall the author be liable for any special, incidental, indirect, or consequential damages arising from the use of this software.
 * By using this project, users acknowledge and agree to abide by these terms and conditions.
 */

package leyramu.qinian.asc.gateway.api.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * 获取body请求数据（解决流不能重复读取问题）
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.0.0
 * @since 2024/7/23
 */
@Component
public class CacheRequestFilter extends AbstractGatewayFilterFactory<CacheRequestFilter.Config> {

    /**
     * 构造方法
     */
    public CacheRequestFilter() {
        super(Config.class);
    }

    /**
     * 获取过滤器名称
     *
     * @return 过滤器名称
     */
    @Override
    public String name() {
        return "CacheRequestFilter";
    }

    /**
     * 获取过滤器
     *
     * @param config 过滤器配置
     * @return 过滤器
     */
    @Override
    public GatewayFilter apply(Config config) {
        CacheRequestGatewayFilter cacheRequestGatewayFilter = new CacheRequestGatewayFilter();
        Integer order = config.getOrder();
        if (order == null) {
            return cacheRequestGatewayFilter;
        }
        return new OrderedGatewayFilter(cacheRequestGatewayFilter, order);
    }

    /**
     * 过滤器类
     */
    public static class CacheRequestGatewayFilter implements GatewayFilter {

        /**
         * 过滤器
         *
         * @param exchange 交换机
         * @param chain    链
         * @return Mono<Void>
         */
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            // GET DELETE 不过滤
            HttpMethod method = exchange.getRequest().getMethod();
            if (method == HttpMethod.GET || method == HttpMethod.DELETE) {
                return chain.filter(exchange);
            }
            return ServerWebExchangeUtils.cacheRequestBodyAndRequest(exchange, (serverHttpRequest) -> {
                if (serverHttpRequest == exchange.getRequest()) {
                    return chain.filter(exchange);
                }
                return chain.filter(exchange.mutate().request(serverHttpRequest).build());
            });
        }
    }

    /**
     * 获取过滤器顺序
     *
     * @return 过滤器顺序
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("order");
    }

    /**
     * 过滤器配置类
     */
    @Data
    public static class Config {
        private Integer order;
    }
}
