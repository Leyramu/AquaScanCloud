/*
 * Copyright (c) 2022-2024 KCloud-Platform-IoT Author or Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package leyramu.softwarecup.digEdu.monitor.config;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import io.micrometer.common.lang.NonNullApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 通知配置
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.0.0
 * @since 2024/8/2
 */
@Slf4j
@Component
@NonNullApi
public class StatusChangeNotifier extends AbstractStatusChangeNotifier {

    /**
     * 构造方法
     *
     * @param instanceRepository 实例仓库
     */
    public StatusChangeNotifier(InstanceRepository instanceRepository) {
        super(instanceRepository);
    }

    /**
     * 通知消息.
     *
     * @param event    事件
     * @param instance 实例
     * @return 消息
     */
    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent eventStatus) {
                String instanceId = eventStatus.getInstance().getValue();
                String status = eventStatus.getStatusInfo().getStatus();
                switch (status) {
                    case "DOWN" -> log.info("业务实例 {} 未通过健康检查", instanceId);
                    case "OFFLINE" -> log.info("业务实例 {} 离线", instanceId);
                    case "UP" -> log.info("业务实例 {} 上线", instanceId);
                    case "UNKNOWN" -> log.error("业务实例 {} 未知异常", instanceId);
                    default -> log.error("业务实例 {} 缺省信息", instanceId);
                }
            }
        });
    }
}
