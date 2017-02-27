
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author generals
 */
public class CassandraStore {
    private String database ;
    private Cluster cluster;
    private Session session;
    public CassandraStore(String database){
        cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
        session = cluster.connect(database);
    }
    public Session getSession()
    {
        return session;
    }
}
