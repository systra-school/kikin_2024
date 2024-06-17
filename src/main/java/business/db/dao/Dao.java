/**
 * ファイル名：Dao.java
 *
 * 変更履歴
 * 1.0  2010/07/19 Kazuya.Naraki
 */
package business.db.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * 説明：DBアクセス部品
 *
 * @author naraki
 *
 */
public abstract class Dao {

    // ログ出力クラス
    private Log log = LogFactory.getLog(this.getClass());

    // S2dao
    protected S2Container container = S2ContainerFactory.create("dao-mysql.dicon");

    private InitialContext InitialContext;
    private DataSource refDataSource;
    protected Connection connection;

    protected Dao() {
        try {
        	InitialContext = new InitialContext();
            refDataSource = (DataSource) InitialContext.lookup("java:comp/env/MySQL_DBCP");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * DBコネクションの接続を行います。
     *
     * @param なし
     * @return なし
     * @author naraki
     * @throws SQLException
     */
    protected void connect() {
        log.info("start");
        try {
            connection = refDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("end");
    }

    /**
     * DBコネクションの切断を行います。
     *
     * @param なし
     * @return なし
     * @throws SQLException
     * @author naraki
     */
    protected void disConnect() {
        log.info("start");
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("end");
    }

    public Connection getConnection() {

        try {
            // コネクション取得
            connection = refDataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

}
