package org.chepurnoy.timeseries

import org.joda.time.DateTime
import play.api.libs.iteratee.Enumerator

sealed trait TimeSeriesDatum{
  type T
  val value:T
  val timestamp:DateTime
}
/** A datum about new event
  *
  * @constructor create new time series datum with value and timestamp
  * @param value is result of new event, it have type string
  * @param timestamp is time of result new event
*/
case class TimeSeriesStringDatum(value:String,timestamp:DateTime) extends TimeSeriesDatum{type T = String }

/** A datum about new event
  *
  * @constructor create new time series datum with value and timestamp
  * @param value is result of new event, it have type double or convert to double
  * @param timestamp is time of result new event
  */
case class TimeSeriesDoubleDatum(value:Double,timestamp:DateTime) extends TimeSeriesDatum{type T = Double }

/** A data about events in basket(type event)
  *
  * @constructor create data about events
  * @param basket is name basket which contains events
  * @param data about events
*/
case class TimeSeriesData(basket:String, data:List[TimeSeriesDatum])

/** A data about events in basket(type event)
  *
  * @constructor create data about events
  * @param basket is name basket which contains events
  * @param enumerator is data about events  in Enumerator format
  */
case class TimeSeriesDataEnumerator(basket:String, enumerator:Enumerator[TimeSeriesDatum])
