package com.dzero.boot.imported.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.generator.UUIDGenerator;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dzero.boot.imported.common.execute.ExcelImportExecute;
import com.dzero.boot.imported.entity.Import;
import com.dzero.boot.imported.entity.ImportData;
import com.dzero.boot.imported.entity.Template;
import com.dzero.boot.imported.entity.dto.ImportDTO;
import com.dzero.boot.imported.enums.DataStatus;
import com.dzero.boot.imported.mapper.ImportMapper;
import com.dzero.boot.imported.service.ImportDataService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * description: ImportDataServiceImpl
 * <p>
 *
 * @author 40233-胡佳艺 2023/06/12 23:26
 */
public class ImportDataServiceImpl implements ImportDataService {

	@Autowired
	ImportMapper importMapper;

	@Override
	public String uploadData(String templateCode, String param, MultipartFile file) throws IOException {
		return null;
	}

	@Override
	public String uploadData(String templateCode, String param, InputStream inputStream, String filename) {
		return null;
	}

	@Override
	public String syncUploadData(Long tenantId, String templateCode, String param, MultipartFile file) {
		param = validateParam(param);
		String filename = file.getOriginalFilename();
		Assert.notNull(filename, "文件读取错误");
		try {
			InputStream fileIo = file.getInputStream();
			Assert.notNull(fileIo, "读取文件失败");
			String batch = templateCode + UuidUtils.generateUuid();
			switch (filename.substring(filename.lastIndexOf("."))) {
				case "xlsx":
					Import anImport = Import.builder().batch(batch)
							.status("UPLOADING")
							.dataCount(0)
							.param(param)
							.templateCode(templateCode)
							.build();
					importMapper.insert(anImport);
					// 启动excel导入线程
					ExcelImportExecute excelImport = new ExcelImportExecute(fileIo,
							templateClientService.getTemplate(tenantId, templateCode),
							batch,
							importConfig,
							importRepository,
							dataRepository);
					excelImport.run();
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String validateParam(String param) {
	}

	@Override
	public String syncUploadData(Long tenantId, String templateCode, String param, Integer sheetIndex, JsonNode data) {
		return null;
	}

	@Override
	public Page<ImportData> pageData(String templateCode, String batch, Integer sheetIndex, DataStatus status, Page page) {
		return null;
	}

	@Override
	public Import validateData(Long tenantId, String templateCode, String batch, Map<String, Object> args) {
		return null;
	}

	@Override
	public Import syncValidateData(Long tenantId, String templateCode, String batch, Map<String, Object> args) {
		return null;
	}

	@Override
	public void syncValidateJsonData(Template template, Integer sheetIndex, String jsonData) {

	}

	@Override
	public ImportDTO importData(Long tenantId, String templateCode, String batch, Map<String, Object> args) {
		return null;
	}

	@Override
	public ImportDTO syncImportData(Long tenantId, String templateCode, String batch, Map<String, Object> args) {
		return null;
	}

	@Override
	public String updateImportData(Long id, String data) {
		return null;
	}

	@Override
	public void deleteById(Long id) {

	}

	@Override
	public void clearData(List<Import> importList) {

	}

	@Override
	public void exportExcelData(Long tenantId, String templateCode, String batch, Integer sheetIndex, DataStatus status, HttpServletResponse response) {

	}

	@Override
	public void exportCsvData(Long tenantId, String templateCode, String batch, Integer sheetIndex, DataStatus status, HttpServletResponse response) {

	}
}
