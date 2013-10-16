package mongo

import scala.concurrent.{ExecutionContext, Future}
import org.joda.time.DateTime
import reactivemongo.bson.BSONDocument
import reactivemongo.api.{MongoDriver, QueryOpts}
import reactivemongo.api.collections.default.BSONCollection
import ExecutionContext.Implicits.global
import org.chepurnoy.timeseries.{TimeSeriesData, TimeSeriesDataEnumerator, TimeSeriesDatum, Operations}

/** implement Operation trait in Mongo
 *
 * @constructor create connect to mongo
 * @param servers is list servers with mongo
 * @param dbName is name data base in mongo
 */

class MongoOperations(servers: List[String], dbName:String) extends Operations with MongoUtils {

  val driver = new MongoDriver
  val connection = driver.connection(servers)
  val db = connection(dbName)

  lazy val emptyQuery = BSONDocument()
  lazy val reverseOrder = BSONDocument("t" -> -1)

  def put(basket:String, datum:TimeSeriesDatum):Future[Boolean] = {
    val collection:BSONCollection = db(basket)
    collection.insert(datum).map(_.ok)
  }

  def getEnumerator(basket: String):TimeSeriesDataEnumerator  = {
    val collection:BSONCollection = db(basket)
    val data = collection.find(emptyQuery).sort(reverseOrder).cursor[TimeSeriesDatum].enumerate()
    TimeSeriesDataEnumerator(basket, data)
  }

  def get(basket:String):Future[TimeSeriesData] = {
    val collection:BSONCollection = db(basket)
    val fData = collection.find(emptyQuery).sort(reverseOrder).cursor[TimeSeriesDatum].toList()
    fData.map(data=> TimeSeriesData(basket, data))
  }


  def get(basket: String, startTime: DateTime, finishTime: DateTime): Future[TimeSeriesData] = {
    val collection:BSONCollection = db(basket)
    val filter = BSONDocument("$and" -> BSONDocument("timestamp" -> BSONDocument("$gt" -> startTime),
      "timestamp" -> BSONDocument("$lt" -> finishTime)
    ))

    val fData = collection.
      find(emptyQuery, filter).
      sort(reverseOrder).
      cursor[TimeSeriesDatum].
      toList()

    fData.map(data=> TimeSeriesData(basket, data))
  }

  def get(basket: String, howMany: Int): Future[TimeSeriesData] = {

    val collection:BSONCollection = db(basket)
    val fData = collection.find(emptyQuery).
              sort(reverseOrder).
              options(QueryOpts().batchSize(howMany)).
              cursor[TimeSeriesDatum].
              toList()
    fData.map(data=> TimeSeriesData(basket, data))
  }

  def basketsList():Future[List[String]] = {
    db.collectionNames.map(baskets=> baskets.filter(!_.contains("system")))
  }

  //  def getAs[T](basket:String):Option[Future[TimeSeriesData]] = {
  //    val collection:BSONCollection = db(basket)
  //    val data = collection.find(emptyQuery).cursor[TimeSeriesDatum{type T = T}].enumerate()
  //    TimeSeriesDataEnumerator(basket, data)
  //
  //  }

}
