package com.darenzai.dzero.admin.service.imported;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.darenzai.dzero.admin.api.entity.SysLog;
import com.darenzai.dzero.admin.api.entity.imported.TemplateHeader;

/**
 * <p>
 * description: TemplateHeaderService
 * <p>
 *
 * @author 40233-胡佳艺 2023/06/12 20:58
 */
public interface TemplateHeaderService extends IService<TemplateHeader> {

	Page<TemplateHeader>  pageTemplateHeader(String templateCode, String templateName, Page page);

	TemplateHeader detailTemplateHeader(Long templateId);

	TemplateHeader createTemplateHeader(TemplateHeader templateHeader);

	TemplateHeader updateTemplateHeader(TemplateHeader templateHeader);

	void deleteTemplateHeader(Long id);
}
