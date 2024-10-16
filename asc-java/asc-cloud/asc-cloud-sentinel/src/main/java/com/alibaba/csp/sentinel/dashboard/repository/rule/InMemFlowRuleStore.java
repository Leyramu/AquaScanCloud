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

package com.alibaba.csp.sentinel.dashboard.repository.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.slots.block.flow.ClusterFlowConfig;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 将 {@link FlowRuleEntity} 存储在内存中
 *
 * @author leyou
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.0.0
 * @since 2024/9/3
 */
@Component
public class InMemFlowRuleStore extends InMemoryRuleRepositoryAdapter<FlowRuleEntity> {

    /**
     * 唯一标识
     */
    private static final AtomicLong IDS = new AtomicLong(0);

    /**
     * 获取下一个唯一标识
     *
     * @return 下一个唯一标识
     */
    @Override
    protected long nextId() {
        return IDS.incrementAndGet();
    }

    /**
     * 预处理
     *
     * @param entity {@link FlowRuleEntity}
     * @return {@link FlowRuleEntity}
     */
    @Override
    protected FlowRuleEntity preProcess(FlowRuleEntity entity) {
        if (entity != null && entity.isClusterMode()) {
            ClusterFlowConfig config = entity.getClusterConfig();
            if (config == null) {
                config = new ClusterFlowConfig();
                entity.setClusterConfig(config);
            }
            config.setFlowId(entity.getId());
        }
        return entity;
    }
}
