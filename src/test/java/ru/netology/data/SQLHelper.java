package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app",
                "app", "pass");
    }
    public static DataHelper.VerificationCode getVerificationCodeFor() {
        var authCodeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";
        try (var conn = getConn()) {
            var code = runner.query(conn, authCodeSQL, new ScalarHandler<String>());
            return new DataHelper.VerificationCode(code);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
    @SneakyThrows
    public static void clearDB() {
        var deleteCode = "DELETE FROM auth_codes";
        var deleteTransaction = "DELETE FROM card_transactions";
        var deleteCard = "DELETE FROM cards";
        var deleteUser = "DELETE FROM users";
        var connection = getConn();
        runner.execute(connection, deleteCode);
        runner.execute(connection, deleteTransaction);
        runner.execute(connection, deleteCard);
        runner.execute(connection, deleteUser);
    }
}
