package ink.akira.duplicateRequest.pojo;

/**
 * Created by akira on 2019/2/13.
 */
public class JsonResult {
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
}
