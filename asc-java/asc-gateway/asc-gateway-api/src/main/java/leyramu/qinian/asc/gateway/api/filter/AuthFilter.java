/*
 * Copyright (c) 2024 Leyramu. All rights reserved.
 * This project (Digitalization Education), including its source code, documentation, and any associated materials, is the intellectual property of Leyramu. No part of this software may be reproduced, distributed, or transmitted in any form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the copyright owner, Miraitowa_zcx, except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
 * For inquiries related to licensing or usage outside the scope of this notice, please contact the copyright holder at 2038322151@qq.com.
 * The author disclaims all warranties, express or implied, including but not limited to the warranties of merchantability and fitness for a particular purpose. Under no circumstances shall the author be liable for any special, incidental, indirect, or consequential damages arising from the use of this software.
 * By using this project, users acknowledge and agree to abide by these terms and conditions.
 */

package leyramu.qinian.asc.gateway.api.filter;

import leyramu.qinian.asc.gateway.api.config.properties.IgnoreWhiteProperties;
import leyramu.qinian.asc.common.core.constant.CacheConstants;
import leyramu.qinian.asc.common.core.constant.HttpStatus;
import leyramu.qinian.asc.common.core.constant.SecurityConstants;
import leyramu.qinian.asc.common.core.constant.TokenConstants;
import leyramu.qinian.asc.common.core.utils.JwtUtils;
import leyramu.qinian.asc.common.core.utils.ServletUtils;
import leyramu.qinian.asc.common.core.utils.StringUtils;
import leyramu.qinian.asc.common.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

/**
 * 网关鉴权
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.0.0
 * @since 2024/7/23
 */
@Component
@RequiredArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {

    /**
     * 日志记录
     */
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    /**
     * 排除过滤的 uri 地址，nacos自行添加
     */
    private final IgnoreWhiteProperties ignoreWhite;

    /**
     * Redis 操作
     */
    private final RedisService redisService;

    /**
     * 全局过滤
     *
     * @param exchange 封装了请求和响应对象
     * @param chain    过滤器链
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();

        String url = request.getURI().getPath();
        // 跳过不需要验证的路径
        if (StringUtils.matches(url, ignoreWhite.getWhites())) {
            return chain.filter(exchange);
        }
        String token = getToken(request);
        if (StringUtils.isEmpty(token)) {
            return unauthorizedResponse(exchange, "令牌不能为空");
        }
        Claims claims = JwtUtils.parseToken(token);
        if (claims == null) {
            return unauthorizedResponse(exchange, "令牌已过期或验证不正确！");
        }
        String userKey = JwtUtils.getUserKey(claims);
        boolean isLogin = redisService.hasKey(getTokenKey(userKey));
        if (!isLogin) {
            return unauthorizedResponse(exchange, "登录状态已过期");
        }
        String userid = JwtUtils.getUserId(claims);
        String username = JwtUtils.getUserName(claims);
        if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(username)) {
            return unauthorizedResponse(exchange, "令牌验证失败");
        }

        // 设置用户信息到请求
        addHeader(mutate, SecurityConstants.USER_KEY, userKey);
        addHeader(mutate, SecurityConstants.DETAILS_USER_ID, userid);
        addHeader(mutate, SecurityConstants.DETAILS_USERNAME, username);
        // 内部请求来源参数清除
        removeHeader(mutate);
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    /**
     * 添加 header
     *
     * @param mutate 请求
     * @param name   名称
     * @param value  值
     */
    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value) {
        if (value == null) {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = ServletUtils.urlEncode(valueStr);
        mutate.header(name, valueEncode);
    }

    /**
     * 移除 header
     *
     * @param mutate 请求
     */
    private void removeHeader(ServerHttpRequest.Builder mutate) {
        mutate.headers(httpHeaders -> httpHeaders.remove(SecurityConstants.FROM_SOURCE)).build();
    }

    /**
     * 无权访问返回结果
     *
     * @param exchange 请求
     * @param msg      信息
     * @return Mono<Void>
     */
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String msg) {
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        return ServletUtils.webFluxResponseWriter(exchange.getResponse(), msg, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 获取缓存key
     */
    private String getTokenKey(String token) {
        return CacheConstants.LOGIN_TOKEN_KEY + token;
    }

    /**
     * 获取请求token
     */
    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(TokenConstants.AUTHENTICATION);
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (token != null && StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX)) {
            token = token.replaceFirst(TokenConstants.PREFIX, StringUtils.EMPTY);
        }
        return token;
    }

    /**
     * 获取排序
     *
     * @return int
     */
    @Override
    public int getOrder() {
        return -200;
    }
}
