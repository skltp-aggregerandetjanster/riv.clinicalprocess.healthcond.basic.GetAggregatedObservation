package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.basic.getaggregatedobservations;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.GetObservationsResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateAggregatedResponseTest;
import se.skltp.aggregatingservices.data.TestDataGenerator;


@RunWith(SpringJUnit4ClassRunner.class)
public class GOCreateAggregatedResponseTest extends CreateAggregatedResponseTest {

  private static GOAgpServiceConfiguration configuration = new GOAgpServiceConfiguration();
  private static AgpServiceFactory<GetObservationsResponseType> agpServiceFactory = new GOAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GOCreateAggregatedResponseTest() {
      super(testDataGenerator, agpServiceFactory, configuration);
  }

  @Override
  public int getResponseSize(Object response) {
        GetObservationsResponseType responseType = (GetObservationsResponseType)response;
    return responseType.getObservationGroup().size();
  }
}