
package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.basic.getaggregatedobservations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import riv.clinicalprocess.healthcond.basic.getobservations.v1.rivtabp21.GetObservationsResponderInterface;
import riv.clinicalprocess.healthcond.basic.getobservations.v1.rivtabp21.GetObservationsResponderService;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "getaggregatedobservations.v1")
public class GOAgpServiceConfiguration extends se.skltp.aggregatingservices.configuration.AgpServiceConfiguration {

  public static final String SCHEMA_PATH = "/schemas/clinicalprocess_healthcond_basic_1.0/interactions/GetObservationsInteraction/GetObservationsInteraction_1.0_RIVTABP21.wsdl";

  public GOAgpServiceConfiguration() {

    setServiceName("GetAggregatedObservations-v1");
    setTargetNamespace("urn:riv:clinicalprocess:healthcond:basic:GetObservations:1:rivtabp21");

    // Set inbound defaults
    setInboundServiceURL("http://localhost:9011/GetAggregatedObservations/service/v1");
    setInboundServiceWsdl(SCHEMA_PATH);
    setInboundServiceClass(GetObservationsResponderInterface.class.getName());
    setInboundPortName(GetObservationsResponderService.GetObservationsResponderPort.toString());

    // Set outbound defaults
    setOutboundServiceWsdl(SCHEMA_PATH);
    setOutboundServiceClass(GetObservationsResponderInterface.class.getName());
    setOutboundPortName(GetObservationsResponderService.GetObservationsResponderPort.toString());

    // FindContent
    setEiServiceDomain("riv:clinicalprocess:healthcond:basic");
    setEiCategorization("chb-o");

    // TAK
    setTakContract("urn:riv:clinicalprocess:healthcond:basic:GetObservationsResponder:1");

    // Set service factory
    setServiceFactoryClass(GOAgpServiceFactoryImpl.class.getName());
  }


}
