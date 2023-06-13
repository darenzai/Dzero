package com.dzero.boot.imported.common.execute;

import com.dzero.boot.imported.common.redis.AmountRedis;
import com.dzero.boot.imported.entity.Template;
import com.dzero.boot.imported.mapper.ImportDataMapper;
import com.dzero.boot.imported.mapper.ImportMapper;
import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.Iterator;

/**
 * <p>
 * description: ExcelImportExecute
 * <p>
 *
 * @author 40233-胡佳艺 2023/06/13 21:33
 */
public class ExcelImportExecute implements Runnable{

	private final InputStream file;
	private final Template template;
	private final String batch;
	private final ImportMapper importMapper;
	private final ImportDataMapper dataMapper;
	private final Integer batchNumber;
	private final Integer bufferMemory;
	private Integer count = 0;
	private Integer stepSize;

	public ExcelImportExecute(InputStream file, Template template, String batch, ImportMapper importMapper, ImportDataMapper dataMapper, Integer batchNumber, Integer bufferMemory, Integer stepSize) {
		this.file = file;
		this.template = template;
		this.batch = batch;
		this.importMapper = importMapper;
		this.dataMapper = dataMapper;
		this.batchNumber = batchNumber;
		this.bufferMemory = bufferMemory;
		this.stepSize = stepSize;
	}



	@Override
	public void run() {
		try (Workbook workbook = StreamingReader.builder()
				.rowCacheSize(batchNumber)
				// 读取资源时，缓存到内存的字节大小，默认是1024
				.bufferSize(bufferMemory)
				// 打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
				.open(fileIo)) {
			// 获取数据总量
			Iterator<Sheet> iterator = workbook.sheetIterator();
			while (iterator.hasNext()) {
				/** 获取当前Excel 每个sheet页最后一行数据算出数据总量 */
				Sheet sheet = iterator.next();
				count += sheet.getLastRowNum();
			}
			/** 在Redis里刷新数据总量 */
			AmountRedis.refreshCount(null, batch, count);
			// 进度刷新步长
			stepSize = StepUtils.getStepSize(count);
			long start = System.nanoTime();
			Assert.notNull(template, HimpBootConstants.ErrorCode.LOCAL_TEMPLATE_NOT_EXISTS);
			Assert.notEmpty(template.getTemplatePageList(), HimpBootConstants.ErrorCode.TEMPLATE_PAGE_NOT_EXISTS);
			logger.debug("<<<<<<<<<<<<<<<     batch {} start     >>>>>>>>>>>>>>>", batch);
			template.getTemplatePageList().forEach(templatePage ->
					readSheet(templatePage.getSheetIndex(), workbook.getSheetAt(templatePage.getSheetIndex()), templatePage.getTemplateColumnList(), templatePage.getStartLine())
			);
			logger.debug("<<<<<<<<<<<<<<<     batch {} upload success , time consuming : {}     >>>>>>>>>>>>>>>>", batch, System.nanoTime() - start);
	}
}
