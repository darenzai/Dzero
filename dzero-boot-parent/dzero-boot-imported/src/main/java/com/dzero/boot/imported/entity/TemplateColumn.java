package com.dzero.boot.imported.entity;

import com.darenzai.dzero.common.mybatis.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 通用模板行
 *
 * @author qingsheng.chen@hand-china.com 2019-01-24 16:04:37
 */
@Data
public class TemplateColumn extends BaseEntity {

    public static final String FIELD_ID = "id";
    public static final String FIELD_TARGET_ID = "targetId";
    public static final String FIELD_COLUMN_INDEX = "columnIndex";
    public static final String FIELD_COLUMN_NAME = "columnName";
    public static final String FIELD_COLUMN_CODE = "columnCode";
    public static final String FIELD_COLUMN_TYPE = "columnType";
    public static final String FIELD_LENGTH = "length";
    public static final String FIELD_FORMAT_MASK = "formatMask";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_NULLABLE_FLAG = "nullableFlag";
    public static final String FIELD_VALIDATE_FLAG = "validateFlag";
    public static final String FIELD_CHANGE_DATA_FLAG = "changeDataFlag";
    public static final String FIELD_CHANGE_DATA_URL = "changeDataUrl";
    public static final String FIELD_CHANGE_DATA_CODE = "changeDataCode";
    public static final String FIELD_MAX_VALUE = "maxValue";
    public static final String FIELD_MIN_VALUE = "minValue";
    public static final String FIELD_VALIDATE_SET = "validateSet";
    public static final String FIELD_REGULAR_EXPRESSION = "regularExpression";
    public static final String FIELD_TENANT_ID = "tenantId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    private Long id;
    @NotNull
    private Long targetId;
    @NotNull
    private Integer columnIndex;
    @NotBlank
    private String columnName;
    @NotBlank
    private String columnCode;
    @NotBlank
    private String columnType;
    private Long length;
    private String formatMask;
    @NotNull
    private Integer enabledFlag;
    @NotNull
    private Integer nullableFlag;
    @NotNull
    private Integer validateFlag;
    @NotNull
    private Integer changeDataFlag;
    private String sampleData;
    private String description;
    private String maxValue;
    private String minValue;
    private String validateSet;
    private String regularExpression;
    private Long tenantId;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

    private String columnTypeMeaning;

    @JsonProperty("templateLineTls")
    private List<TemplateColumnTl> templateColumnTls;
}
