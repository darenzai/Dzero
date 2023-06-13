package com.dzero.boot.imported.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 导入目标
 *
 * @author qingsheng.chen@hand-china.com 2019-01-24 16:04:37
 */
@Data
public class TemplatePage {

    public static final String FIELD_ID = "id";
    public static final String FIELD_HEADER_ID = "headerId";
    public static final String FIELD_SHEET_INDEX = "sheetIndex";
    public static final String FIELD_SHEET_NAME = "sheetName";
    public static final String FIELD_DATASOURCE_CODE = "datasourceCode";
    public static final String FIELD_TABLE_NAME = "tableName";
    public static final String FIELD_RULE_SCRIPT_CODE = "ruleScriptCode";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";

//
// 业务方法(按public protected private顺序排列)
// ------------------------------------------------------------------------------

    private Long id;
    private Long headerId;
    @NotNull
    private Integer sheetIndex;
    @NotBlank
    private String sheetName;
    private String datasourceCode;
    private String tableName;
    private String ruleScriptCode;
    private Integer enabledFlag;
    private Integer startLine;

    //
    // 非数据库字段
    // ------------------------------------------------------------------------------

    private Long tenantId;

    /**
     * 模板ID
     */
    @Valid
    @JsonProperty("templateLineList")
    private List<TemplateColumn> templateColumnList;

    private String sheetIndexMeaning;
    private String datasourceDesc;
    private String scriptDescription;
    //
    // getter/setter
    // ------------------------------------------------------------------------------



}
