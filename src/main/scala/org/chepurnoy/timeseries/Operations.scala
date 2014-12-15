package org.chepurnoy.timeseries

import org.joda.time.DateTime
import scala.concurrent.Future


trait Operations {

  /**
   *
   * @param basket is name basket which need put data
   * @param data about new event
   * @return result put
   */
  def put(basket:String, data:TimeSeriesDatum):Future[Boolean]

  /**
   *
   * @param basket is name basket which need get data
   * @return data which contains in basket
   */
  def get(basket:String):Future[TimeSeriesData]

  /**
   *
   * @param basket is name basket which need get data
   * @return data Enumerator which contains in basket
   */
  def getEnumerator(basket:String):TimeSeriesDataEnumerator

  /**
   *
   * @param basket is name basket which need get data
   * @param startTime for filter timestamp events
   * @param finishTime for filter timestamp events
   * @return data which contains in basket
   */
  def get(basket:String,startTime:DateTime,finishTime:DateTime):Future[TimeSeriesData]

  /**
   *
   * @param basket is name basket which need get data
   * @param howMany events need get
   * @return data which contains in basket
   */
  def get(basket: String, howMany: Int): Future[TimeSeriesData]

  /**
   *
   * @return list baskets in storage
   */
  def basketsList():Future[List[String]]


  /**
   * Delete all data in a basket before given timestamp
   * @param basket - basket
   * @param timestamep - timestamp
   */
  def deleteBefore(basket: String, timestamep:DateTime)


}
