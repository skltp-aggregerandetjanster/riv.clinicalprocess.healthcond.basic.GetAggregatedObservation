package observations

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scenarios.GetAggregatedObservationsScenario

/**
 * Load test VP:GetAggregatedReferralOutcome.
 */
class TP03Load extends Simulation {

  val baseURL             = "https://qa.esb.ntjp.se/vp/clinicalprocess/healthcond/basic/GetObservations/1/rivtabp21"
  
  val testDuration            = 140 seconds
  val numberOfConcurrentUsers =  45
  val rampDuration            =  10 seconds
  val minWaitDuration         =   2 seconds
  val maxWaitDuration         =   5 seconds
  
  val httpProtocol = http.baseURL(baseURL).disableResponseChunksDiscarding

  val load = scenario("load")
                 .during(testDuration) {
                   exec(session => {
                     session.set("status","200").set("patientid","121212121212").set("name","Tolvan Tolvansson").set("count","2")
                   })    
                   .exec(GetAggregatedObservationsScenario.request)
                   .pause(minWaitDuration, maxWaitDuration)
                  }
                 
  setUp (load.inject(rampUsers(numberOfConcurrentUsers) over (rampDuration)).protocols(httpProtocol))
}