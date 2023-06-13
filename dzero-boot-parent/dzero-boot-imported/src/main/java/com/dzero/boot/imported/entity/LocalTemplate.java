package com.dzero.boot.imported.entity;

import com.darenzai.dzero.common.mybatis.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author shuangfei.zhu@hand-china.com 2018-12-18 16:00:39
 */
@Table(name = "himp_local_template")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocalTemplate extends BaseEntity {

    public static final String FIELD_ID = "id";
    public static final String FIELD_TEMPLATE_CODE = "templateCode";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_TEMPLATE_JSON = "templateJson";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------


    @Id
    @GeneratedValue
    private Long id;

    @Schema(description = "模板编码", required = true)
    @NotBlank
    @Length(max = 30)
    private String templateCode;

    @Schema(description = "模板JSON内容", required = true)
    @JsonIgnore
    private String templateJson;

    @Transient
    @NotNull
    private Template template;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateJson() {
		return templateJson;
	}

	public void setTemplateJson(String templateJson) {
		this.templateJson = templateJson;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}
}
