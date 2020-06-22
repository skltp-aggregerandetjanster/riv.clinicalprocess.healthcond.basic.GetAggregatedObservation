package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.basic.getaggregatedobservations;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.GetObservationsResponseType;
import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.GetObservationsType;
import se.skltp.aggregatingservices.AgServiceFactoryBase;

@Log4j2
public class GOAgpServiceFactoryImpl extends
    AgServiceFactoryBase<GetObservationsType, GetObservationsResponseType> {

  @Override
  public String getPatientId(GetObservationsType getObservationsType) {
    return getObservationsType.getPatientId().getExtension();
  }

  @Override
  public String getSourceSystemHsaId(GetObservationsType getObservationsType) {
    return getObservationsType.getSourceSystemHSAId();
  }

  @Override
  public GetObservationsResponseType aggregateResponse(List<GetObservationsResponseType> aggregatedResponseList) {

    GetObservationsResponseType aggregatedResponse = new GetObservationsResponseType();

    for (Object object : aggregatedResponseList) {
      GetObservationsResponseType response = (GetObservationsResponseType) object;
      aggregatedResponse.getObservationGroup().addAll(response.getObservationGroup());
    }

    log.info("Returning {} aggregated observations", aggregatedResponse.getObservationGroup().size());

    return aggregatedResponse;
  }
}

