package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck
import scala.util.Random

object GetAggregatedObservationsScenario {
  
  val headers = Map(
    "Accept-Encoding"                        -> "gzip,deflate",
    "Content-Type"                           -> "text/xml;charset=UTF-8",
    "SOAPAction"                             -> "urn:riv:clinicalprocess:healthcond:basic:GetObservationsResponder:1:GetObservations",
    "x-vp-sender-id"                         -> "test",
    "x-rivta-original-serviceconsumer-hsaid" -> "test",
    "Keep-Alive"                             -> "115")
    
  val request = exec(
        http("GetAggregatedObservations ${patientid} - ${name}")
          .post("")
          .headers(headers)
          .body(ELFileBody("GetObservations.xml"))
          .check(status.is(session => session("status").as[String].toInt))
          .check(xpath("soap:Envelope", List("soap" -> "http://schemas.xmlsoap.org/soap/envelope/")).exists)
          .check(substring("GetObservationsResponse"))
          .check(xpath("//ns3:observations", List("ns3" -> "urn:riv:clinicalprocess:healthcond:basic:GetObservationsResponder:1")).count.is(session => session("count").as[String].toInt))
      )
}
