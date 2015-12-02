package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.basic.getaggregatedobservations

trait CommonParameters {
  val serviceName:String     = "Observations"
  val urn:String             = "urn:riv:clinicalprocess:healthcond:basic:GetObservationsResponder:1"
  val responseElement:String = "GetObservationsResponse"     
  val responseItem:String    = "observationGroup"
  var baseUrl:String         = if (System.getProperty("baseUrl") != null && !System.getProperty("baseUrl").isEmpty()) {
                                   System.getProperty("baseUrl") 
                               } else { 
                                   "http://33.33.33.33:8081/GetAggregatedObservations/service/v1"
                               }
}