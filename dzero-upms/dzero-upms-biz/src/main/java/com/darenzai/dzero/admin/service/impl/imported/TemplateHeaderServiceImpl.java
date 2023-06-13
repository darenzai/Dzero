package com.darenzai.dzero.admin.service.impl.imported;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.darenzai.dzero.admin.api.entity.SysUserRole;
import com.darenzai.dzero.admin.api.entity.imported.TemplateHeader;
import com.darenzai.dzero.admin.mapper.SysUserRoleMapper;
import com.darenzai.dzero.admin.mapper.TemplateHeaderMapper;
import com.darenzai.dzero.admin.service.SysUserRoleService;
import com.darenzai.dzero.admin.service.imported.TemplateHeaderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * description: TemplateHeaderServiceImpl
 * <p>
 *
 * @author 40233-胡佳艺 2023/06/12 20:59
 */
@Slf4j
@Service
@AllArgsConstructor
public class TemplateHeaderServiceImpl extends ServiceImpl<TemplateHeaderMapper, TemplateHeader> implements TemplateHeaderService {

	@Override
	public Page<TemplateHeader> pageTemplateHeader(String templateCode, String templateName, Page page) {
		LambdaQueryWrapper<TemplateHeader> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		if(StringUtils.isNotEmpty(templateCode)) {
			lambdaQueryWrapper.eq(TemplateHeader::getTemplateCode, templateCode);
		}
		if(StringUtils.isNotEmpty(templateName)) {
			lambdaQueryWrapper.eq(TemplateHeader::getTemplateName, templateName);
		}
		return baseMapper.selectPage(page, lambdaQueryWrapper);
	}

	@Override
	public TemplateHeader detailTemplateHeader(Long templateId) {
		return baseMapper.selectById(templateId);
	}

	@Override
	public TemplateHeader createTemplateHeader(TemplateHeader templateHeader) {
		LambdaQueryWrapper<TemplateHeader> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(TemplateHeader::getTemplateCode, templateHeader.getTemplateCode());
		Assert.isTrue(baseMapper.selectCount(queryWrapper) == 1,
				"当前记录已存在");
		baseMapper.insert(templateHeader);
		return templateHeader;
	}

	@Override
	public TemplateHeader updateTemplateHeader(TemplateHeader templateHeader) {
		baseMapper.updateById(templateHeader);
		return templateHeader;
	}

	@Override
	public void deleteTemplateHeader(Long id) {
		baseMapper.deleteById(id);
	}
}
