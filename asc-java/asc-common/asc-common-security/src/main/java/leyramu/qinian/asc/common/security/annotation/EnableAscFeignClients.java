/*
 * Copyright (c) 2024 Leyramu. All rights reserved.
 * This project (Digitalization Education), including its source code, documentation, and any associated materials, is the intellectual property of Leyramu. No part of this software may be reproduced, distributed, or transmitted in any form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the copyright owner, Miraitowa_zcx, except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
 * For inquiries related to licensing or usage outside the scope of this notice, please contact the copyright holder at 2038322151@qq.com.
 * The author disclaims all warranties, express or implied, including but not limited to the warranties of merchantability and fitness for a particular purpose. Under no circumstances shall the author be liable for any special, incidental, indirect, or consequential damages arising from the use of this software.
 * By using this project, users acknowledge and agree to abide by these terms and conditions.
 */

package leyramu.softwarecup.digEdu.common.security.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

/**
 * 自定义feign注解，添加basePackages路径
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.0.0
 * @since 2024/7/23
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
public @interface EnableAscFeignClients {
    String[] value() default {};

    /**
     * 指定扫描的包路径
     * @return String[]
     */
    String[] basePackages() default {"leyramu.qinian.asc"};

    /**
     * 指定扫描的包路径
     * @return Class<?>[]
     */
    Class<?>[] basePackageClasses() default {};

    /**
     * 指定默认的Feign配置类
     * @return Class<?>[]
     */
    Class<?>[] defaultConfiguration() default {};

    /**
     * 指定Feign客户端
     * @return Class<?>[]
     */
    Class<?>[] clients() default {};
}
