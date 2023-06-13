package com.darenzai.dzero.admin.controller.imported;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.darenzai.dzero.admin.api.entity.imported.TemplateHeader;
import com.darenzai.dzero.admin.service.imported.TemplateHeaderService;
import com.darenzai.dzero.common.core.util.R;
import org.apache.ibatis.annotations.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * description: TemplateHeaderController
 * <p>
 *
 * @author 40233-胡佳艺 2023/06/11 21:15
 */
@RequestMapping("/v1/template-headers")
public class TemplateHeaderController {

	@Autowired
	TemplateHeaderService templateHeaderService;


	@RequestMapping("/page")
	public R<IPage<TemplateHeader>> pageTemplateHeader(String templateCode, String templateName, Page page) {
		return R.ok(templateHeaderService.pageTemplateHeader(templateCode, templateName,  page));
	}

	@GetMapping(value = "/{templateId}")
	public R<TemplateHeader> detailTemplateHeader(
			@PathVariable(value = "templateId")  Long templateId) {
		return R.ok(templateHeaderService.detailTemplateHeader(templateId));
	}

	@PostMapping
	public R<TemplateHeader> createTemplateHeader(@RequestBody TemplateHeader templateHeader) {

		return R.ok(templateHeaderService.createTemplateHeader(templateHeader));
	}

	@PutMapping
	public R<TemplateHeader> updateTemplateHeader(@RequestBody  TemplateHeader templateHeader) {
		return R.ok(templateHeaderService.updateTemplateHeader(templateHeader));
	}


	@DeleteMapping
	public R<Void> deleteTemplateHeader(@RequestBody TemplateHeader templateHeader) {
		templateHeaderService.deleteTemplateHeader(templateHeader.getId());
		return R.ok();
	}
}
