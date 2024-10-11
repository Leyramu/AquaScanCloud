/*
 * Copyright (c) 2024 Leyramu. All rights reserved.
 * This project (Digitalization Education), including its source code, documentation, and any associated materials, is the intellectual property of Leyramu. No part of this software may be reproduced, distributed, or transmitted in any form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the copyright owner, Miraitowa_zcx, except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
 * For inquiries related to licensing or usage outside the scope of this notice, please contact the copyright holder at 2038322151@qq.com.
 * The author disclaims all warranties, express or implied, including but not limited to the warranties of merchantability and fitness for a particular purpose. Under no circumstances shall the author be liable for any special, incidental, indirect, or consequential damages arising from the use of this software.
 * By using this project, users acknowledge and agree to abide by these terms and conditions.
 */

package leyramu.softwarecup.digEdu.system.api.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import leyramu.softwarecup.digEdu.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 * @version 1.0.0
 * @since 2024/7/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单名称
     */
    private String parentName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 路由名称，默认和路由地址相同的驼峰格式
     */
    private String routeName;

    /**
     * 是否为外链（0是 1否）
     */
    private String isFrame;

    /**
     * 是否缓存（0为缓存 1为不缓存）
     */
    private String isCache;

    /**
     * 类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 显示状态（0显示 1隐藏）
     */
    private String visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;

    /**
     * 权限字符串
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 子菜单
     */
    private List<SysMenu> children = new ArrayList<>();

    /**
     * 获取菜单名称
     *
     * @return 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    public String getMenuName() {
        return menuName;
    }

    /**
     * 获取父菜单 ID
     *
     * @return 父菜单 ID
     */
    @NotNull(message = "显示顺序不能为空")
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 获取路由地址
     *
     * @return 路由地址
     */
    @Size(max = 200, message = "路由地址不能超过200个字符")
    public String getPath() {
        return path;
    }

    /**
     * 获取组件路径
     *
     * @return 组件路径
     */
    @Size(max = 200, message = "组件路径不能超过255个字符")
    public String getComponent() {
        return component;
    }

    /**
     * 获取菜单类型
     *
     * @return 菜单类型
     */
    @NotBlank(message = "菜单类型不能为空")
    public String getMenuType() {
        return menuType;
    }

    /**
     * 获取权限字符串
     *
     * @return 权限字符串
     */
    @Size(max = 100, message = "权限标识长度不能超过100个字符")
    public String getPerms() {
        return perms;
    }

    /**
     * 获取父菜单名称
     *
     * @return 父菜单名称
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("menuId", getMenuId())
                .append("menuName", getMenuName())
                .append("parentId", getParentId())
                .append("orderNum", getOrderNum())
                .append("path", getPath())
                .append("component", getComponent())
                .append("query", getQuery())
                .append("routeName", getRouteName())
                .append("isFrame", getIsFrame())
                .append("IsCache", getIsCache())
                .append("menuType", getMenuType())
                .append("visible", getVisible())
                .append("status ", getStatus())
                .append("perms", getPerms())
                .append("icon", getIcon())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
