package kevin.practice.mongodb;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoDBClientTest {
	public Mongo mongo = null;

	public MongoDBClientTest() throws UnknownHostException {
		mongo = new Mongo("ubuntu");
	}
	public void testSave() {
		DB db = mongo.getDB("test");
		DBCollection co = db.getCollection("user");
		BasicDBObject o = new BasicDBObject();
		o.append("name", "小红").append("age", 18);
		co.insert(o);
	}
	public void testGet() {
		DB db = mongo.getDB("test");
		DBCollection co = db.getCollection("user");
		DBCursor cursor = co.find();
		while(cursor.hasNext()){
			DBObject o = cursor.next();
			System.out.println(o);
		}
	}
	
	public static void main(String[] args) throws Exception {
		MongoDBClientTest test = new MongoDBClientTest();
		test.testGet();
	}
	
}
