
package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.basic.getaggregatedobservations;

import lombok.extern.log4j.Log4j2;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Service;
import riv.clinicalprocess.healthcond.basic._enum._1.TimeStampTypeFormatEnum;
import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.GetObservationsResponseType;
import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.GetObservationsType;
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
import se.skltp.aggregatingservices.data.TestDataGenerator;

@Log4j2
@Service
public class ServiceTestDataGenerator extends TestDataGenerator {

  @Override
  public String getPatientId(MessageContentsList messageContentsList) {
    GetObservationsType observationsType = (GetObservationsType) messageContentsList.get(1);
    String patientId = observationsType.getPatientId().getExtension();
    return patientId;
  }

  @Override
  public Object createResponse(Object... responseItems) {
    log.info("Creating a response with {} items", responseItems.length);
    GetObservationsResponseType observationsResponseType = new GetObservationsResponseType();
    for (int i = 0; i < responseItems.length; i++) {
			observationsResponseType.getObservationGroup().add((ObservationGroupType) responseItems[i]);
    }

    log.info("response.toString:" + observationsResponseType.toString());

    return observationsResponseType;
  }

  @Override
  public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {
    log.debug("Created ResponseItem for logical-address {}, registeredResidentId {} and businessObjectId {}",
        new Object[]{logicalAddress, registeredResidentId, businessObjectId});

    log.debug("Created one response item for logical-address {}, registeredResidentId {} and businessObjectId {}",
        new Object[]{logicalAddress, registeredResidentId, businessObjectId});

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

  @Override
  public Object createRequest(String patientId, String sourceSystemHSAId) {
    GetObservationsType outcomeType = new GetObservationsType();

		outcomeType.setPatientId(createId("1.2.752.129.2.1.3.1", patientId));
		outcomeType.setSourceSystemHSAId(sourceSystemHSAId);

    return outcomeType;
  }
}
