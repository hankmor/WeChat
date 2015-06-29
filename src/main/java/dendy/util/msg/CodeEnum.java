package dendy.util.msg;

/**
 * 消息码枚举值
 * @author Zero
 */
public enum CodeEnum {

    /**
     * 成功消息码
     */
    SUCCESS(0),
    /**
     * 失败消息码
     */
    ERROR(1),
    /**
     * 普通消息码
     */
    INFO(2),
    /**
     * 警告消息码
     */
    WARN(3);

    private int value;

    private CodeEnum(int value) {
        this.value = value;
    }

    /**
     * 获取消息码值
     *
     * @return 消息码值
     */
    public int getValue() {
        return value;
    }

}
