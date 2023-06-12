package com.darenzai.dzero.admin.api.entity.imported;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.darenzai.dzero.common.mybatis.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 导入目标
 *
 * @author qingsheng.chen@hand-china.com 2019-01-24 16:04:37
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TemplateTarget extends BaseEntity {

    public static final String FIELD_ID = "id";
    public static final String FIELD_HEADER_ID = "headerId";
    public static final String FIELD_SHEET_INDEX = "sheetIndex";
    public static final String FIELD_SHEET_NAME = "sheetName";
    public static final String FIELD_DATASOURCE_CODE = "datasourceCode";
    public static final String FIELD_TABLE_NAME = "tableName";
    public static final String FIELD_RULE_SCRIPT_CODE = "ruleScriptCode";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_START_LINE = "startLine";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

    public interface Insert{}

//
// 数据库字段
// ------------------------------------------------------------------------------

    @Id
	@TableId
    private Long id;

	@TableField(value = "header_id")
	@Schema(description = "头id")
    private Long headerId;

    @NotNull
    @TableField(value = "sheet_index")
	@Schema(description = "sheet下标")
    private Integer sheetIndex;

    @NotBlank
	@TableField(value = "sheet_name")
	@Schema(description = "sheet名称")
    private String sheetName;

	@TableField(value = "datasource_code")
	@Schema(description = "数据源code")
    private String datasourceCode;

	@TableField(value = "table_name")
	@Schema(description = "表名")
    private String tableName;

	@TableField(value = "enable_flag")
	@Schema(description = "开启标识")
    private Integer enabledFlag;

    @Schema(description = "开始行")
	@TableField(value = "star_line")
    private Integer startLine;


    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

    /**
     * 模板ID
     */
    @TableField(exist = false)
    @Valid
    private List<TemplateLine> templateLineList;
	@TableField(exist = false)
    private String datasourceDesc;
	@TableField(exist = false)
    private String scriptDescription;
    @TableField(exist = false)
    private String sheetIndexMeaning;

}
