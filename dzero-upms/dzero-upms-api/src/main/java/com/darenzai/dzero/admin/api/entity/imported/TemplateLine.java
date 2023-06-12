package com.darenzai.dzero.admin.api.entity.imported;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.darenzai.dzero.common.mybatis.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 通用模板行
 *
 * @author qingsheng.chen@hand-china.com 2019-01-24 16:04:37
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TemplateLine extends BaseEntity {

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
    public static final String FIELD_SAMPLE_DATA = "sampleData";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_MAX_VALUE = "maxValue";
    public static final String FIELD_MIN_VALUE = "minValue";
    public static final String FIELD_VALIDATE_SET = "validateSet";
    public static final String FIELD_REGULAR_EXPRESSION = "regularExpression";
    public static final String FIELD_TENANT_ID = "tenantId";

    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------

	@TableId(value = "id")
    private Long id;
    @NotNull

	@TableField(value = "target_id")
	@Schema(description = "目标id")
    private Long targetId;

	@NotNull
	@TableField(value = "column_index")
	@Schema(description = "列下标")
    private Integer columnIndex;

	@NotBlank
	@TableField(value = "column_name")
	@Schema(description = "列名称")
    private String columnName;

    @NotBlank
    @Schema(description = "列代码")
	@TableField(value = "column_code")
    private String columnCode;

    @NotBlank
	@TableField(value = "column_type")
	@Schema(description = "列类型")
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


	@Length(max = 240)
    private String description;
    private String maxValue;
    private String minValue;
    private String validateSet;
    private String regularExpression;
    private Long tenantId;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

	@Schema(description = "列类型")
	private String columnTypeMeaning;

}
