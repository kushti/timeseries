package org.chepurnoy.timeseries

import org.joda.time.DateTime
import scala.concurrent.Future


trait Operations {

  def put(basket:String, data:TimeSeriesDatum):Future[Boolean]

  def get(basket:String):Future[TimeSeriesData]

  def getEnumerator(basket:String):TimeSeriesDataEnumerator

  def get(basket:String,startTime:DateTime,finishTime:DateTime):Future[TimeSeriesData]

  def get(basket: String, howMany: Int): Future[TimeSeriesData]

//  def getAs[T](basket:String):Option[Future[TimeSeriesData]]

// def getEnumratorAs[T](basket:String):Options[TimeSeriesDataEnumerator]

  def basketsList():Future[List[String]]

  //todo: getAs[T] Option[Future[TSData]]
  //todo: getEnumratorAs[T]

}
