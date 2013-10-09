package org.chepurnoy.timeseries

import org.joda.time.DateTime
import play.api.libs.iteratee.Enumerator

sealed trait TimeSeriesDatum{
  type T
  val value:T
  val timestamp:DateTime
}

case class TimeSeriesStringDatum(value:String,timestamp:DateTime) extends TimeSeriesDatum{type T = String }
case class TimeSeriesDoubleDatum(value:Double,timestamp:DateTime) extends TimeSeriesDatum{type T = Double }

case class TimeSeriesData(basket:String, data:List[TimeSeriesDatum])
case class TimeSeriesDataEnumerator(basket:String, enumerator:Enumerator[TimeSeriesDatum])
