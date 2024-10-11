/*
 * Copyright (c) 2024 Leyramu. All rights reserved.
 * This project (Digitalization Education), including its source code, documentation, and any associated materials, is the intellectual property of Leyramu. No part of this software may be reproduced, distributed, or transmitted in any form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the copyright owner, Miraitowa_zcx, except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
 * For inquiries related to licensing or usage outside the scope of this notice, please contact the copyright holder at 2038322151@qq.com.
 * The author disclaims all warranties, express or implied, including but not limited to the warranties of merchantability and fitness for a particular purpose. Under no circumstances shall the author be liable for any special, incidental, indirect, or consequential damages arising from the use of this software.
 * By using this project, users acknowledge and agree to abide by these terms and conditions.
 */

package leyramu.qinian.asc.gateway.api.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

import java.util.Objects;
import java.util.Optional;

/**
 * Swagger 配置
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.0.0
 * @since 2024/7/23
 */
@RestController
@RequestMapping("/swagger-resources")
public class SwaggerHandler {

    /**
     * 安全配置
     */
    private final SecurityConfiguration securityConfiguration;

    /**
     * UI 配置
     */
    private final UiConfiguration uiConfiguration;

    /**
     * 资源提供者
     */
    private final SwaggerResourcesProvider swaggerResources;

    /**
     * 构造方法
     *
     * @param securityConfiguration 安全配置
     * @param uiConfiguration       UI 配置
     * @param swaggerResources      资源提供者
     */
    @Autowired
    public SwaggerHandler(
            @Autowired(required = false) SecurityConfiguration securityConfiguration,
            @Autowired(required = false) UiConfiguration uiConfiguration,
            SwaggerResourcesProvider swaggerResources) {
        this.securityConfiguration = securityConfiguration;
        this.uiConfiguration = uiConfiguration;
        this.swaggerResources = swaggerResources;
    }

    /**
     * 安全配置
     *
     * @return ResponseEntity
     */
    @GetMapping("/configuration/security")
    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
        return Mono.just(new ResponseEntity<>(
                Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()),
                HttpStatus.OK));
    }

    /**
     * UI 配置
     *
     * @return ResponseEntity
     */
    @GetMapping("/configuration/ui")
    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
        return Mono.just(new ResponseEntity<>(
                Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    /**
     * 资源提供者
     *
     * @return ResponseEntity
     */
    @SuppressWarnings("rawtypes")
    @GetMapping("")
    public Mono<ResponseEntity> swaggerResources() {
        SwaggerResourcesProvider swaggerResources = Objects.requireNonNull(this.swaggerResources);
        return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
    }
}
