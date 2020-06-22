package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.basic.getaggregatedobservations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import riv.clinicalprocess.healthcond.basic.getobservations.v1.rivtabp21.GetObservationsResponderInterface;
import riv.clinicalprocess.healthcond.basic.getobservations.v1.rivtabp21.GetObservationsResponderService;
import se.skltp.aggregatingservices.config.TestProducerConfiguration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="go.teststub")
public class ServiceConfiguration extends TestProducerConfiguration {
  public static final String SCHEMA_PATH = "/schemas/clinicalprocess_healthcond_basic_1.0/interactions/GetObservationsInteraction/GetObservationsInteraction_1.0_RIVTABP21.wsdl";

  public ServiceConfiguration() {
    setProducerAddress("http://localhost:8083/vp");
    setServiceClass(GetObservationsResponderInterface.class.getName());
    setServiceNamespace("urn:riv:clinicalprocess:healthcond:basic:GetObservationsResponder:1");
    setPortName(GetObservationsResponderService.GetObservationsResponderPort.toString());
    setWsdlPath(SCHEMA_PATH);
    setTestDataGeneratorClass(ServiceTestDataGenerator.class.getName());
  }

}
