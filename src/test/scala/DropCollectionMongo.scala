import mongo.MongoUtils
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.api.MongoDriver
import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global


class DropCollectionMongo (servers: List[String], dbName:String) extends MongoUtils{

  val driver = new MongoDriver
  val connection = driver.connection(servers)

  // Gets a reference to the database
  val db = connection(dbName)

  def dropCollection(colName:String):Future[Boolean] = {
    val collection:BSONCollection = db(colName)
    collection.drop
  }


}
