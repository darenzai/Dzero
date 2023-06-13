package com.dzero.boot.imported.service;

/**
 * <p>
 * description: IDoImportService
 * <p>
 *
 * @author 40233-胡佳艺 2023/06/12 22:08
 */
public interface IDoImportService {
	/**
	 * 逐条导入处理程序
	 *
	 * @param data 行数据，Json格式，可以转化为Json对象进行处理
	 * @return true成功  false失败
	 */
	Boolean doImport(String data);

	/**
	 * 导入开始前调用
	 */
	default void onStart(){

	}

	/**
	 * 导入结束后调用
	 */
	default void onFinish(){

	}
}
