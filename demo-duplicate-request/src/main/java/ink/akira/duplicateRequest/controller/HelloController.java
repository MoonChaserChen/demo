package ink.akira.duplicateRequest.controller;

import ink.akira.duplicateRequest.dao.AkiraDAO;
import ink.akira.duplicateRequest.pojo.JsonResult;
import ink.akira.duplicateRequest.pojo.UserBook;
import ink.akira.jedis.service.JedisDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by akira on 2019/2/13.
 */
@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private JedisDAO jedisDAO;
    @Autowired
    private AkiraDAO akiraDAO;

    @RequestMapping(value="/getBook")
    public JsonResult getBook(String userId, String bookId) {
        logger.info("===== userId: {}, bookId: {}",userId, bookId);
        String lockKey = "lck:" + userId + ":" + bookId;
        try {
            if (jedisDAO.getLock(lockKey, String.valueOf(new Date()))) {
                List<UserBook> userBook = akiraDAO.getUserBook(userId, bookId);
                if (userBook.isEmpty()) {
                    akiraDAO.insertBook(userId, bookId);
                    return new JsonResult(0, "OK", null);
                } else {
                    return new JsonResult(-1, "Already have this book", null);
                }
            } else {
                return new JsonResult(-1, "Getting this book currently", null);
            }
        } finally {
            jedisDAO.delete(lockKey);
        }
    }

}
