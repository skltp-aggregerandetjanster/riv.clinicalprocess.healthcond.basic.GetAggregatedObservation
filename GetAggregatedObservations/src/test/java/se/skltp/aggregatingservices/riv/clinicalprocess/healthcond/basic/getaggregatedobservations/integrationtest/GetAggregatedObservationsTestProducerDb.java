package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.basic.getaggregatedobservations.integrationtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.util.ThreadSafeSimpleDateFormat;

import riv.clinicalprocess.healthcond.basic._enum._1.TimeStampTypeFormatEnum;
import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.GetObservationsResponseType;
import riv.clinicalprocess.healthcond.basic.v1.AdditionalParticipantType;
import riv.clinicalprocess.healthcond.basic.v1.CVType;
import riv.clinicalprocess.healthcond.basic.v1.IIType;
import riv.clinicalprocess.healthcond.basic.v1.LegalAuthenticatorType;
import riv.clinicalprocess.healthcond.basic.v1.ObservationGroupType;
import riv.clinicalprocess.healthcond.basic.v1.ObservationType;
import riv.clinicalprocess.healthcond.basic.v1.PartialTimePeriodType;
import riv.clinicalprocess.healthcond.basic.v1.PartialTimeStampType;
import riv.clinicalprocess.healthcond.basic.v1.PatientType;
import riv.clinicalprocess.healthcond.basic.v1.PerformerRoleType;
import riv.clinicalprocess.healthcond.basic.v1.SourceSystemType;
import riv.clinicalprocess.healthcond.basic.v1.ValueANYType;
import se.skltp.agp.test.producer.TestProducerDb;

public class GetAggregatedObservationsTestProducerDb extends TestProducerDb {

    ThreadSafeSimpleDateFormat sdf = new ThreadSafeSimpleDateFormat("YYYYMMddHHmmss");

    private static final Logger log = LoggerFactory.getLogger(GetAggregatedObservationsTestProducerDb.class);

    @Override
    public Object processRequest(String logicalAddress, String registeredResidentId) {

        /*
         * Return an error-message if error logical address HSA-ID-6 is called.
         * 
         * This case was added to be able to test the case when a system returns
         * an error for a certain RR_ID and another system returns a OK
         * response. This case should probably be added to the agp test common
         * TestProducerDb, but for know its included here to test the concept.
         */
        if (TEST_LOGICAL_ADDRESS_6.equals(logicalAddress)) {
            throw new RuntimeException("Logical address to trigger exception was called: " + logicalAddress);
        }

        return super.processRequest(logicalAddress, registeredResidentId);
    }

    @Override
    public Object createResponse(Object... responseItems) {
        log.debug("Creates a response with {} items", responseItems);
        GetObservationsResponseType response = new GetObservationsResponseType();
        for (int i = 0; i < responseItems.length; i++) {
            response.getObservationGroup().add((ObservationGroupType) responseItems[i]);
        }
        return response;
    }

    @Override
    public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {

        log.debug("Created one response item for logical-address {}, registeredResidentId {} and businessObjectId {}", new Object[] { logicalAddress,
                registeredResidentId, businessObjectId });

        ObservationGroupType observations = new ObservationGroupType();
        
        observations.getAdditionalParticipant().add(new AdditionalParticipantType());
        observations.setLegalAuthenticator(new LegalAuthenticatorType());
        observations.setPatient(new PatientType());
        observations.getPatient().setId(new IIType());
        observations.getPatient().getId().setRoot("1.2.752.129.2.1.3.1");
        observations.getPatient().getId().setExtension(registeredResidentId);
        observations.setPerformerRole(new PerformerRoleType());
        observations.setSourceSystem(new SourceSystemType());
        
        observations.getObservation().add(new ObservationType());
        observations.getObservation().get(0).setApprovedForPatient(true);
        observations.getObservation().get(0).setDescription("Fritextbeskrivning av observationsen");
        observations.getObservation().get(0).setId(createId("1.2.752.129.2.1.2.1", logicalAddress));
        observations.getObservation().get(0).setMethod(createCVType());
        observations.getObservation().get(0).setRegistrationTime("20150131125932");
        observations.getObservation().get(0).setStatus(new CVType());
        observations.getObservation().get(0).getStatus().setCode("something");
        observations.getObservation().get(0).setTargetSite(createCVType());
        observations.getObservation().get(0).setTime(new PartialTimePeriodType());
        observations.getObservation().get(0).getTime().setStart(new PartialTimeStampType());
        observations.getObservation().get(0).getTime().getStart().setFormat(TimeStampTypeFormatEnum.YYYYMMD_DHHMM);
        observations.getObservation().get(0).getTime().getStart().setValue("201505011257");
        observations.getObservation().get(0).getTime().setEnd(new PartialTimeStampType());
        observations.getObservation().get(0).getTime().getEnd().setFormat(TimeStampTypeFormatEnum.YYYYMMD_DHHMM);
        observations.getObservation().get(0).getTime().getEnd().setValue("201505092359");
        observations.getObservation().get(0).setType(new CVType());
        observations.getObservation().get(0).getType().setCode("hello world");
        observations.getObservation().get(0).setValue(new ValueANYType());
        observations.getObservation().get(0).getValue().setCv(new CVType());
        observations.getObservation().get(0).setValueNegation(false);

        return observations;
    }

    private CVType createCVType() {
        CVType cvType = new CVType();
        cvType.setCode("CODE");
        cvType.setCodeSystem("CODESYSTEM");
        return cvType;
    }

    private IIType createId(String root, String extension) {
        IIType iiType = new IIType();
        iiType.setRoot(root);
        iiType.setExtension(extension);
        return iiType;
    }
}
