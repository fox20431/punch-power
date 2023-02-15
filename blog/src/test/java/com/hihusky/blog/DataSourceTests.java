package com.hihusky.blog;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;



@SpringBootTest
public class DataSourceTests {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void testConnection() throws SQLException {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT 1");
        assertTrue(resultSet.next());
        assertEquals(1, resultSet.getInt(1));
        connection.close();
    }
}
