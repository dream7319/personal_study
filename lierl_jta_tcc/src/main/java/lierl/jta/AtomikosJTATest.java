package lierl.jta;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * atomikos 商业版需要付费，实现了 tcc--> try-confirm-cancel
 * atomikos 免费版实现了jta
 * jta是对整个操作过程的资源加锁
 * tcc是把整个操作过程分为几个操作步骤，针对每个小操作加锁，提高了整个系统的性能
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/27 10:38
 */
public class AtomikosJTATest {

    private static AtomikosDataSourceBean createAtomikosDataSourceBean(String dbName){
        //连接数据库的基本属性
        Properties properties = new Properties();
        properties.setProperty("url","jdbc:mysql://139.199.129.251:3306/"+dbName);
        properties.setProperty("user","root");
        properties.setProperty("password","root");

        //使用AtomikosDataSourceBean封装 com.mysql.jdbc.jdbc2.optional.MysqlXADataSource
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        //atomikos 要求为每个AtomikosDataSourceBean名称
        ds.setUniqueResourceName(dbName);
        ds.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        ds.setXaProperties(properties);

        return ds;
    }

    public static void main(String[] args) {
        AtomikosDataSourceBean ds1 = createAtomikosDataSourceBean("test");
        AtomikosDataSourceBean ds2 = createAtomikosDataSourceBean("test2");

        Connection conn1 = null;
        Connection conn2 = null;

        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        UserTransaction userTransaction = new UserTransactionImp();
        try{
            //开启事务
            userTransaction.begin();

            //执行test1数据库的sql
            conn1 = ds1.getConnection();
            ps1 = conn1.prepareStatement("insert into user(name) values (?)", Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1,"zhangsan");
            ps1.executeUpdate();
            ResultSet generatedKeys = ps1.getGeneratedKeys();
            while(generatedKeys.next()){
                System.out.println(generatedKeys.getInt(1));
            }

            //模拟异常，直接进入catch块，两个都不会提交
//            int i = 1/0;

            conn2 = ds2.getConnection();
            ps2 = conn2.prepareStatement("insert into user(name) values ('lisi')");
            ps2.executeUpdate();

            userTransaction.commit();
        } catch (Exception e) {
            try {
                e.printStackTrace();
                userTransaction.rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                ps1.close();
                ps2.close();
                conn1.close();
                conn2.close();
                ds1.close();
                ds2.close();
            } catch (Exception ignore) {
            }
        }
    }
}
