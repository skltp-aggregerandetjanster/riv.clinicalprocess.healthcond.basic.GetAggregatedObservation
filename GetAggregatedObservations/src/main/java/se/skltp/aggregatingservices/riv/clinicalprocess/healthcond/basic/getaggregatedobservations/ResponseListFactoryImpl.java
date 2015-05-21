package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.basic.getaggregatedobservations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;

import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.GetObservationsResponseType;
import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.ObjectFactory;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.ResponseListFactory;

public class ResponseListFactoryImpl implements ResponseListFactory {

    private static final Logger log = LoggerFactory.getLogger(ResponseListFactoryImpl.class);
    private static final JaxbUtil jaxbUtil = new JaxbUtil(GetObservationsResponseType.class, ProcessingStatusType.class);
    private static final ObjectFactory OF = new ObjectFactory();

    @Override
    public String getXmlFromAggregatedResponse(QueryObject queryObject, List<Object> aggregatedResponseList) {
        GetObservationsResponseType aggregatedResponse = new GetObservationsResponseType();

        for (Object object : aggregatedResponseList) {
            GetObservationsResponseType response = (GetObservationsResponseType) object;
            aggregatedResponse.getObservationGroup().addAll(response.getObservationGroup());
        }

        if (log.isInfoEnabled()) {
            String patientId = queryObject.getFindContent().getRegisteredResidentIdentification();
            log.info("Returning {} aggregated observationss for patient id {}", aggregatedResponse.getObservationGroup().size(), patientId);
        }

        // Since the class GetObservationsResponseType doen't have an @XmlRootElement annotation we need to use the ObjectFactory to add it.
        return jaxbUtil.marshal(OF.createGetObservationsResponse(aggregatedResponse));
    }
}
