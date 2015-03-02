package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck

object GetAggregatedObservationPingForConfigurationScenario {
  
  val headers = Map(
    "Accept-Encoding" -> "gzip,deflate",
    "Content-Type"    -> "text/xml;charset=UTF-8",
    "SOAPAction"      -> "urn:riv:itintegration:monitoring:PingForConfigurationResponder:1:PingForConfiguration",
    "Keep-Alive"      -> "115")

  val request = exec(
        http("GetAggregatedObservationPingForConfiguration")
          .post("")
          .headers(headers)
          .body(RawFileBody("GetObservationPingForConfiguration.xml"))
          .check(status.is(200))
          .check(substring("Applikation"))
          .check(substring("GetAggregatedObservation"))
          .check(xpath("soap:Envelope", List("soap" -> "http://schemas.xmlsoap.org/soap/envelope/")).exists)
          .check(regex("GetAggregatedObservation").exists)
      )
}
