package com.dzero.boot.imported.common.util;

import com.darenzai.dzero.common.core.convertor.ApplicationContextHelper;
import io.choerodon.core.convertor.ApplicationContextHelper;
import org.hzero.boot.imported.config.ImportConfig;

/**
 * 步长工具
 *
 * @author shuangfei.zhu@hand-china.com 2019/08/21 20:24
 */
public class StepUtils {

    private StepUtils() {
    }

    /**
     * 获取进度刷新步长 算出步长 数据总量 / 配置类中的频次 = 步长(如果步长小于最低步长则将最低步长赋值给步长)
     *
     * @param count 数据总量
     * @return 步长
     */
    public static Integer getStepSize(int count) {
        ImportConfig importConfig = ApplicationContextHelper
                .getContext().getBean(ImportConfig.class);
        int minStep = importConfig.getMinStepSize();
        int frequency = importConfig.getFrequency();
        int step = (int) Math.ceil((double) count / frequency);
        if (step < minStep) {
            step = minStep;
        }
        return step;
    }
}
