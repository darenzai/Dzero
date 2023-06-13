package com.dzero.boot.imported.entity;

import com.darenzai.dzero.common.mybatis.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 通用模板头
 *
 * @author qingsheng.chen@hand-china.com 2019-01-24 16:04:37
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Template extends BaseEntity {

    public static final String FIELD_ID = "id";
    public static final String FIELD_TEMPLATE_CODE = "templateCode";
    public static final String FIELD_TEMPLATE_NAME = "templateName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_TEMPLATE_TYPE = "templateType";
    public static final String FIELD_PREFIX_PATCH = "prefixPatch";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TENANT_ID = "tenantId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------

    private Long id;
    @NotBlank
    private String templateCode;
    @NotBlank
    private String templateName;
    @NotNull
    private Integer enabledFlag;
    @NotBlank
    private String templateType;
    private String prefixPatch;
    private String description;
    private String templateUrl;
    private String tenantName;
    private Integer fragmentFlag;

    /**
     * 模板目标
     */
    @Valid
    @JsonProperty("templateTargetList")
    private List<TemplatePage> templatePageList;
    private String templateTypeMeaning;

}
