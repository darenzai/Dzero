package com.darenzai.dzero.admin.controller.imported;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.darenzai.dzero.admin.api.entity.imported.TemplateHeader;
import com.darenzai.dzero.common.core.util.R;
import org.apache.ibatis.annotations.Results;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * description: TemplateHeaderController
 * <p>
 *
 * @author 40233-胡佳艺 2023/06/11 21:15
 */
public class TemplateHeaderController {


	public R<IPage<TemplateHeader>> pageTemplateHeader(String templateCode, String templateName, Page page) {
		return R.ok(headerService.pageTemplateHeader(templateCode, templateName, organizationId, pageRequest);
	}
}
