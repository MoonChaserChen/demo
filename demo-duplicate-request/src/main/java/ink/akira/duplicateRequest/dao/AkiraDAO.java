package ink.akira.duplicateRequest.dao;

import ink.akira.duplicateRequest.pojo.UserBook;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by akira on 2019/2/19.
 */
public interface AkiraDAO {
    void insertBook(@Param("userId") String userId, @Param("bookId") String bookId);
    List<UserBook> getUserBook(@Param("userId") String userId, @Param("bookId") String bookId);
}
