/*
 * Copyright (c) 2024 Leyramu. All rights reserved.
 * This project (Digitalization Education), including its source code, documentation, and any associated materials, is the intellectual property of Leyramu. No part of this software may be reproduced, distributed, or transmitted in any form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the copyright owner, Miraitowa_zcx, except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
 * For inquiries related to licensing or usage outside the scope of this notice, please contact the copyright holder at 2038322151@qq.com.
 * The author disclaims all warranties, express or implied, including but not limited to the warranties of merchantability and fitness for a particular purpose. Under no circumstances shall the author be liable for any special, incidental, indirect, or consequential damages arising from the use of this software.
 * By using this project, users acknowledge and agree to abide by these terms and conditions.
 */

package leyramu.qinian.asc.gateway.api.filter;

import lombok.RequiredArgsConstructor;
import leyramu.qinian.asc.gateway.api.config.properties.CaptchaProperties;
import leyramu.qinian.asc.gateway.api.service.ValidateCodeService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 验证码过滤器
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.0.0
 * @since 2024/7/23
 */
@Component
@RequiredArgsConstructor
public class ValidateCodeFilter extends AbstractGatewayFilterFactory<Object> {

    /**
     * 验证 URL
     */
    private final static String[] VALIDATE_URL = new String[]{"/auth/login", "/auth/register"};

    /**
     * 验证码服务
     */
    private final ValidateCodeService validateCodeService;

    /**
     * Captcha 配置
     */
    private final CaptchaProperties captchaProperties;

    /**
     * 验证码
     */
    private static final String CODE = "code";

    /**
     * 请求体 UUID
     */
    private static final String UUID = "uuid";

    /**
     * 网关验证码过滤器
     *
     * @param config 配置
     * @return GatewayFilter
     */
    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // TODO 测试
            return chain.filter(exchange);

            // 非登录/注册请求或验证码关闭，不处理
//            if (!StringUtils.equalsAnyIgnoreCase(request.getURI().getPath(), VALIDATE_URL) || !captchaProperties.getEnabled()) {
//                return chain.filter(exchange);
//            }
//
//            try {
//                String rspStr = resolveBodyFromRequest(request);
//                JSONObject obj = JSON.parseObject(rspStr);
//                validateCodeService.checkCaptcha(obj.getString(CODE), obj.getString(UUID));
//            } catch (Exception e) {
//                return ServletUtils.webFluxResponseWriter(exchange.getResponse(), e.getMessage());
//            }
//            return chain.filter(exchange);
        };
    }

    /**
     * 获取请求体
     *
     * @param serverHttpRequest 请求
     * @return 请求体
     */
    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        // 获取请求体
        AtomicReference<String> bodyRef = new AtomicReference<>();
        serverHttpRequest.getBody()
                .map(buffer -> {
                    byte[] bytes = new byte[buffer.readableByteCount()];
                    buffer.read(bytes);
                    DataBufferUtils.release(buffer);
                    return new String(bytes, StandardCharsets.UTF_8);
                })
                .collect(Collectors.joining())
                .subscribe(bodyRef::set);
        return bodyRef.get();
    }
}
