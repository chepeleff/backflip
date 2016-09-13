package dbtest;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Properties;

/**
 * Created by ChepelevAG on 13.09.2016.
 */
public class WorkWithMongo {
    private MongoClient mongoClient;
    private DB db;
    private boolean authenticate;
    private DBCollection table;

    public WorkWithMongo(Properties prop) {
        try{
            mongoClient = new MongoClient(prop.getProperty("host"), Integer.valueOf(prop.getProperty("port")));
            db = mongoClient.getDB(prop.getProperty("dbname"));
            authenticate = db.authenticate(prop.getProperty("login"), prop.getProperty("password").toCharArray());
            table = db.getCollection(prop.getProperty("table"));
        } catch (UnknownHostException e) {
            System.err.println("Doesn't connect ¯\\_(ツ)_/¯ ");
        }
    }

 public void add(User user){
     BasicDBObject doc = new BasicDBObject();
     doc.put("login", user.getLogin());
     table.insert(doc);
 }

 public User getByLogin(String login) {
     BasicDBObject query = new BasicDBObject();
     query.put("login", login);
     DBObject result = table.findOne(query);

     User user = new User();
     user.setLogin(String.valueOf(result.get("login")));
     user.setId(String.valueOf(result.get("_id")));

     return user;
 }

}
