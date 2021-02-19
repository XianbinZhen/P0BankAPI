package dev.zhen.utilTest;

import dev.zhen.utils.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionTest {

    @Test
    void connection_test() {
       Connection connection = ConnectionUtil.createConnection();
        Assertions.assertNotNull(connection);
    }
}
