package cn.mypandora.springbootdemo.common.enums;

import lombok.Getter;

@Getter
public enum EnvironmentGroupEnum {

    /**
     * RUNTIME运行环境组：
     * 1. DEV(开发环境)
     * 2. PROD(生产环境)
     */
    RUNTIME(new EnvironmentEnum[]{EnvironmentEnum.DEV, EnvironmentEnum.PROD});

    /**
     * 运行环境
     */
    private EnvironmentEnum[] environmentEnums;

    EnvironmentGroupEnum(EnvironmentEnum[] environmentEnums) {
        this.environmentEnums = environmentEnums;
    }

    /**
     * 是否是runtime运行环境组。
     *
     * @param s     环境名
     * @return      boolean 判断结果
     */
    public static boolean isRuntime(String s) {
        EnvironmentEnum[] environmentEnums = RUNTIME.getEnvironmentEnums();
        for (EnvironmentEnum environmentEnum : environmentEnums) {
            if (environmentEnum.getName().equals(s)) {
                return true;
            }
        }
        return false;
    }
}
