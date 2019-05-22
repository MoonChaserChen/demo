package ink.akira.demoSpring.pojo;

public class JsonResult {
    public static final int SUCCESS = 0;
    public static final String SUCCESS_MSG = "SUCCESS";
    public static final int ERROR = -1;
    public static final String ERROR_MSG = "ERROR";

    private int status;
    private String message;
    private Object data;

    public JsonResult() {
    }

    public JsonResult(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static JsonResult success(Object data) {
        return new JsonResult(SUCCESS, SUCCESS_MSG, data);
    }

    public static JsonResult error(String message) {
        return new JsonResult(ERROR, ERROR_MSG, null);
    }
}
