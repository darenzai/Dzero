package com.darenzai.dzero.admin.api.entity.imported;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.darenzai.dzero.common.mybatis.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 通用模板头
 *
 * @author qingsheng.chen@hand-china.com 2019-01-24 16:04:37
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TemplateHeader extends BaseEntity {

    public static final String FIELD_ID = "id";
    public static final String FIELD_TEMPLATE_CODE = "templateCode";
    public static final String FIELD_TEMPLATE_NAME = "templateName";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_TEMPLATE_TYPE = "templateType";
    public static final String FIELD_PREFIX_PATCH = "prefixPatch";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_TEMPLATE_URL = "templateUrl";
    public static final String FIELD_FRAGMENT_FLAG = "fragmentFlag";
    //
    // 业务方法(按public protected private顺序排列)
    // ------------------------------------------------------------------------------

    //
    // 数据库字段
    // ------------------------------------------------------------------------------

	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@Schema(description = "模板头id")
    private Long id;

    @NotBlank
	@TableField(value = "template_code")
	@Schema(description = "模板代码")
    private String templateCode;

    @NotBlank
	@TableField(value = "template_name")
	@Schema(description = "模板名称")
    private String templateName;

    @NotNull
	@TableField(value = "enabled_flag")
	@Schema(description = "开启标识")
    private Integer enabledFlag;

    @NotBlank
    @TableField(value = "template_type")
	@Schema(description = "模板类型")
    private String templateType;

	@TableField(value = "prefix_patch")
	private String prefixPatch;

	@Schema(description = "描述")
	@TableField(value = "description")
	private String description;

	@Schema(description = "模板链接")
	@TableField(value = "template_url")
	private String templateUrl;

    private Integer fragmentFlag;



    /**
     * 服务端导入
     */
    public interface ServerImport {
    }
}
