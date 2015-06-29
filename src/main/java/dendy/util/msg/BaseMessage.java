package dendy.util.msg;

/**
 * 系统通用消息类
 *
 * @author Dendy
 */
public class BaseMessage {
    //消息内容
    private String msg;

    //消息码，默认为普通消息
    private int code = CodeEnum.INFO.getValue();

    //业务数据，可选项
    private Object result;

    public BaseMessage(CodeEnum codeEnum, String msg) {
        if (null != codeEnum) {
            this.code = codeEnum.getValue();
        }
        this.msg = msg;
    }

    public BaseMessage(CodeEnum codeEnum, String msg, Object result) {
        if (null != codeEnum) {
            this.code = codeEnum.getValue();
        }
        this.msg = msg;
        this.result = result;
    }

    /**
     * info提示信息
     *
     * @param msg 提示信息
     *
     * @return 提示信息
     */
    public static BaseMessage infoMsg(String msg) {
        return new BaseMessage(CodeEnum.INFO, msg);
    }

    /**
     * info提示信息
     * @param msg 提示信息
     * @param result 返回结果对象
     * @return BaseMessage对象
     */
    public static BaseMessage infoMsg(String msg, Object result) {
        return new BaseMessage(CodeEnum.INFO, msg, result);
    }

    /**
     * 警告提示信息
     *
     * @param msg 提示信息
     *
     * @return 提示信息
     */
    public static BaseMessage warnMsg(String msg) {
        return new BaseMessage(CodeEnum.WARN, msg);
    }

    /**
     * 警告提示信息
     * @param msg 提示信息
     * @param result 返回结果对象
     * @return BaseMessage对象
     */
    public static BaseMessage warnMsg(String msg, Object result) {
        return new BaseMessage(CodeEnum.WARN, msg, result);
    }

    /**
     * 错误提示信息
     *
     * @param msg 提示信息
     *
     * @return 提示信息
     */
    public static BaseMessage errorMsg(String msg) {
        return new BaseMessage(CodeEnum.ERROR, msg);
    }

    /**
     * 错误提示信息
     * @param msg 提示信息
     * @param result 返回结果对象
     * @return BaseMessage对象
     */
    public static BaseMessage errorMsg(String msg, Object result) {
        return new BaseMessage(CodeEnum.ERROR, msg, result);
    }

    /**
     * 成功提示信息
     *
     * @param msg 提示信息
     *
     * @return 提示信息
     */
    public static BaseMessage successMsg(String msg) {
        return new BaseMessage(CodeEnum.SUCCESS, msg);
    }

    /**
     * 成功提示信息
     * @param msg 提示信息
     * @param result 返回结果对象
     * @return BaseMessage对象
     */
    public static BaseMessage successMsg(String msg, Object result) {
        return new BaseMessage(CodeEnum.SUCCESS, msg, result);
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public Object getResult() {
        return result;
    }
}
