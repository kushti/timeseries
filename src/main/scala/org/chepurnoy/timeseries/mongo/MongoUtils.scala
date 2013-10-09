package mongo

import reactivemongo.bson._
import org.joda.time.DateTime
import reactivemongo.bson.BSONDateTime
import org.chepurnoy.timeseries.{TimeSeriesDatum, TimeSeriesDoubleDatum, TimeSeriesStringDatum}


trait MongoUtils {

  implicit object BSONDateTimeHandler extends BSONHandler[BSONDateTime, DateTime] {
    def read(time: BSONDateTime) = new DateTime(time.value)
    def write(jdtime: DateTime) = new BSONDateTime(jdtime.getMillis)
  }

  implicit object DataWriter extends BSONDocumentWriter[TimeSeriesDatum]{
    def write(dt:TimeSeriesDatum):BSONDocument = (dt match {
      case s:TimeSeriesStringDatum => BSONDocument("s"->"s", "v" -> s.value)
      case d:TimeSeriesDoubleDatum => BSONDocument("s"->"d", "v" -> d.value)
    }).add("t" -> dt.timestamp)
  }

  implicit object DataReader extends BSONDocumentReader[TimeSeriesDatum] {
    def read(doc:BSONDocument):TimeSeriesDatum = {
      doc.getAs[String]("s").get match {
        case "s" => TimeSeriesStringDatum(
                      doc.getAs[String]("v").get,
                      doc.getAs[DateTime]("t").get
                    )
        case "d" => TimeSeriesDoubleDatum(
                      doc.getAs[Double]("v").get,
                      doc.getAs[DateTime]("t").get
                     )
      }
    }
  }
}
