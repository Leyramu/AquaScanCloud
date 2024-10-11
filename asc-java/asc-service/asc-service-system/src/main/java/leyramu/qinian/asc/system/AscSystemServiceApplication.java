package leyramu.qinian.asc.system;

import leyramu.softwarecup.digEdu.common.security.annotation.EnableAscFeignClients;
import leyramu.softwarecup.digEdu.common.security.annotation.EnableCustomConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统模块
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.0.0
 * @since 2024/9/14
 */
@Slf4j
@EnableCustomConfig
@EnableAscFeignClients
@SpringBootApplication
public class AscSystemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AscSystemServiceApplication.class, args);
    }
}
