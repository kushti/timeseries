import mongo.MongoOperations
import org.chepurnoy.timeseries.{TimeSeriesDatum, TimeSeriesStringDatum, TimeSeriesDoubleDatum}
import org.joda.time.DateTime
import org.specs2.mutable.Specification
import scala.concurrent.Await
import scala.concurrent.duration._



class IntegrationSpec extends Specification{

  val db = new  MongoOperations(List("localhost"),"test")
  val duration = Duration(1, SECONDS)

  def testWriteMongo(basket:String, timeSeries:TimeSeriesDatum) = {
    val writeResult = timeSeries match {
      case d:TimeSeriesDoubleDatum => Await.result(db.put(basket,d),duration)
      case s:TimeSeriesStringDatum => Await.result(db.put(basket,s),duration)
    }
    writeResult mustEqual true
  }

  "montool" should {
    "correctly write and read double and string in org.chepurnoy.timeseries.mongo" in {

      val basket = "basket"
      val timeSeriesTest1 = TimeSeriesDoubleDatum(6.0,new DateTime(1381436764))
      val timeSeriesTest2 = TimeSeriesStringDatum("string",new DateTime(1381436763))
      val timeSeriesTest3 = TimeSeriesStringDatum("str",new DateTime(1381436767))

      testWriteMongo(basket,timeSeriesTest1)
      testWriteMongo(basket,timeSeriesTest2)
      testWriteMongo(basket,timeSeriesTest3)

      val f = db.get(basket,2)
      val results = Await.result(f, duration)
      new DropCollectionMongo(List("localhost"),"test").dropCollection(basket)
      results.data.map{datum =>{
          datum match{
            case d:TimeSeriesDoubleDatum=> d.value mustEqual 6.0
            case s:TimeSeriesStringDatum=> s.value must contain("string")
            case _=> throw new Exception
          }
        }
      }
    }
  }
}
