package ink.akira.duplicateRequest.pojo;

/**
 * Created by akira on 2019/2/19.
 */
public class UserBook {
    private long id;
    private long userId;
    private long bookId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
}
