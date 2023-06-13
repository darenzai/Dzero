package com.dzero.boot.imported.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dzero.boot.imported.enums.DataStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 * 要导入的数据:
 *
 * @author chunqiang.bai@hand-china.com
 */
@Table(name = "himp_data")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImportData {

    public static final String FIELD_ID = "id";
    public static final String FIELD_DATA = "data";
    public static final String FIELD_DATA_STATUS = "dataStatus";
    public static final String FIELD_ERROR_MSG = "errorMsg";
    public static final String FIELD_BACK_INFO = "backInfo";

    @Id
    @GeneratedValue
    @OrderBy
    @JsonProperty("_id")
	@TableId
    private Long id;
    @NotBlank
    @Schema(description = "批次")
    @JsonProperty("_batch")
    private String batch;
    @NotBlank
    @Schema(description = "模板编码")
    @JsonProperty("_templateCode")
    private String templateCode;
    @NotNull
    @Schema(description = "页下标")
    @JsonProperty("_sheetIndex")
    private Integer sheetIndex;
    @Column
    @NotNull
    @Schema(description = "数据状态[NEW(Excel导入),VALID_SUCCESS(验证成功),VALID_FAILED(验证失败),IMPORT_SUCCESS(导入成功),IMPORT_FAILED(导入失败)]")
    @JsonProperty("_dataStatus")
    private DataStatus dataStatus;

    @Schema(description = "错误信息")
    @JsonProperty("_errorMsg")
    @JsonIgnore
    private String errorMsg;

    @Schema(description = "数据")
    @JsonProperty("_data")
    private String data;

    @Schema(description = "回写信息")
    @JsonProperty("_backInfo")
    @JsonIgnore
    private String backInfo;

    @TableField(exist = false)
    @JsonProperty("_dataStatusMeaning")
    private String dataStatusMeaning;

    @Transient
    @JsonProperty("_info")
    private String info;


}