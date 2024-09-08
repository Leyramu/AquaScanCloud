/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.csp.sentinel.dashboard.domain.vo.gateway.api;

import lombok.Data;

import java.util.List;

/**
 * 更新网关 API 的 Value 对象
 *
 * @author cdfive
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.7.0
 * @since 2024/9/3
 */
@Data
public class UpdateApiReqVo {

    /**
     * API ID
     */
    private Long id;

    /**
     * 应用名称
     */
    private String app;

    /**
     * API 名称
     */
    private List<ApiPredicateItemVo> predicateItems;
}
