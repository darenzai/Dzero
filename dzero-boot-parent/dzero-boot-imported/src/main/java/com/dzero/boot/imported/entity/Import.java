package com.dzero.boot.imported.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author shuangfei.zhu@hand-china.com 2018-12-19 14:57:57
 */
@Table(name = "himp_import")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class Import  {

    public static final String FIELD_IMPORT_ID = "importId";
    public static final String FIELD_BATCH = "batch";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_DATA_COUNT = "dataCount";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------

    @Schema(description = "主键 表ID")
    @Id
    @GeneratedValue
    private Long importId;

    @Schema(description = "批次", required = true)
    @NotBlank
    @Length(max = 120)
    private String batch;

    @Schema(description = "当前状态", required = true)
    @NotBlank
    @Length(max = 30)
    private String status;

    @Schema(description = "数据数量", required = true)
    @NotNull
    private Integer dataCount;

    @Schema(description = "自定义参数")
    private String param;

    @Schema(description = "租户ID")
    private Long tenantId;

    @Schema(description = "模板编码")
    private String templateCode;

    @TableField(exist = false)
    private String statusMeaning;

    @Schema(description = "进度")
    @Transient
    private String progress;

    @Schema(description = "数据总量")
    @Transient
    private Integer count;

    @Schema(description = "就绪数量")
    @Transient
    private Integer ready;

    @Transient
    @Schema(description = "创建人姓名")
    private String createdUserName;

    //
    // getter/setter
    // ------------------------------------------------------------------------------

	public Long getImportId() {
		return importId;
	}

	public void setImportId(Long importId) {
		this.importId = importId;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getDataCount() {
		return dataCount;
	}

	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getStatusMeaning() {
		return statusMeaning;
	}

	public void setStatusMeaning(String statusMeaning) {
		this.statusMeaning = statusMeaning;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getReady() {
		return ready;
	}

	public void setReady(Integer ready) {
		this.ready = ready;
	}

	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}
}
