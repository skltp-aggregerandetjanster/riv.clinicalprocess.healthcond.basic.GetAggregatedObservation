package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.basic.getaggregatedobservations.integrationtest;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.healthcond.basic.getobservations.v1.rivtabp21.GetObservationsResponderInterface;
import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.GetObservationsResponseType;
import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.GetObservationsType;
import se.skltp.agp.test.producer.TestProducerDb;

@WebService(serviceName = "GetObservationsResponderService", 
               portName = "GetObservationsResponderPort", 
        targetNamespace = "urn:riv:clinicalprocess:healthcond:basic:GetObservations:1:rivtabp21", 
                   name = "GetObservationsInteraction")
public class GetAggregatedObservationsTestProducer implements GetObservationsResponderInterface {

    private static final Logger log = LoggerFactory.getLogger(GetAggregatedObservationsTestProducer.class);

    private TestProducerDb testDb;

    public void setTestDb(TestProducerDb testDb) {
        this.testDb = testDb;
    }

    public GetObservationsResponseType getObservations(String logicalAddress, GetObservationsType request) {
        log.info("### Virtual service for GetObservations call the source system with logical address: {} and patientId: {}", 
                logicalAddress, request.getPatientId().getExtension());

        GetObservationsResponseType response 
            = (GetObservationsResponseType) testDb.processRequest(logicalAddress, request.getPatientId().getExtension());
        if (response == null) {
            // Return an empty response object instead of null if nothing is found
            response = new GetObservationsResponseType();
        }

        log.info("### Virtual service got {} observationss in the reply from the source system with logical address: {} and patientId: {}",
                new Object[] { response.getObservationGroup().size(), logicalAddress, request.getPatientId().getExtension() });
        // We are done
        return response;
    }
}
