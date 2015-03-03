package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck
import scala.util.Random

object GetAggregatedObservationScenario {
  
  val headers = Map(
    "Accept-Encoding"                        -> "gzip,deflate",
    "Content-Type"                           -> "text/xml;charset=UTF-8",
    "SOAPAction"                             -> "urn:riv:clinicalprocess:healthcond:basic:GetObservationResponder:1:GetObservation",
    "x-vp-sender-id"                         -> "test",
    "x-rivta-original-serviceconsumer-hsaid" -> "test",
    "Keep-Alive"                             -> "115")
    
  val request = exec(
        http("GetAggregatedObservation ${patientid} - ${name}")
          .post("")
          .headers(headers)
          .body(ELFileBody("GetObservation.xml"))
          .check(status.is(session => session("status").as[String].toInt))
          .check(xpath("soap:Envelope", List("soap" -> "http://schemas.xmlsoap.org/soap/envelope/")).exists)
          .check(substring("GetObservationResponse"))
          .check(xpath("//ns3:observation", List("ns3" -> "urn:riv:clinicalprocess:healthcond:basic:GetObservationResponder:1")).count.is(session => session("count").as[String].toInt))
      )
}
