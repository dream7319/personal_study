package lierl.jta;

import com.mysql.jdbc.jdbc2.optional.MysqlXAConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * XA 两阶段提交
 * @Author: lierl
 * @Description:
 * @Date: 2018/5/27 11:10
 */
public class XATest {

    public static void main(String[] args) throws Exception {
        // true表示打印xa语句，用于调试
        boolean logXaCommands = true;
        Class.forName("com.mysql.jdbc.Driver");
        //获取资源管理器操作接口的实例
        Connection conn1 = DriverManager.getConnection("jdbc:mysql://139.199.129.251:3306/test","root","root");
        XAConnection xaConn1 = new MysqlXAConnection((com.mysql.jdbc.Connection)conn1,logXaCommands);
        XAResource rm1 = xaConn1.getXAResource();

        Connection conn2 = DriverManager.getConnection("jdbc:mysql://139.199.129.251:3306/test2","root","root");
        XAConnection xaConn2 = new MysqlXAConnection((com.mysql.jdbc.Connection)conn2,logXaCommands);
        XAResource rm2 = xaConn2.getXAResource();

        //ap请求TM执行一个分布式事务，TM生成全局事务id
        byte[] gtrid = "g12345".getBytes();
        int formatId = 1;

        //================分别执行rm1, rm2上的事务分支======================
        //TM生成rm1上的事务分支id
        byte[] bqual1 = "b00001".getBytes();
        Xid xid1 = new MysqlXid(gtrid, bqual1,formatId);
        //执行rm1上的事务分支
        rm1.start(xid1, XAResource.TMNOFLAGS);
        PreparedStatement ps1 = conn1.prepareStatement("insert into user(name) values ('zhangsan')");
        ps1.executeUpdate();
        rm1.end(xid1,XAResource.TMSUCCESS);


        //TM生成rm2上的事务分支id
        byte[] bqual2 = "b00002".getBytes();
        Xid xid2 = new MysqlXid(gtrid,bqual2,formatId);
        //执行rm2上的事务分支
        rm2.start(xid2,XAResource.TMNOFLAGS);
        PreparedStatement ps2 = conn2.prepareStatement("insert into user(name) values ('lisi')");
        ps2.executeUpdate();
        rm2.end(xid2,XAResource.TMSUCCESS);

        //==========两阶段提交=============================
        //phase1:询问所有的RM准备提交事务分支
        int rm1_phase = rm1.prepare(xid1);
        int rm2_phase = rm2.prepare(xid2);
        //phase2:提交所有的事务分支
        boolean onePhase = false;//TM判断有2个事务分支，所以不能优化为一阶段提交
        if(rm1_phase == XAResource.XA_OK && rm2_phase == XAResource.XA_OK){
            //所有事务分支都prepare成功，提交所有事务分支
            rm1.commit(xid1,onePhase);
            rm2.commit(xid2,onePhase);
        }else{
            rm1.rollback(xid1);
            rm2.rollback(xid2);
        }


    }
}
