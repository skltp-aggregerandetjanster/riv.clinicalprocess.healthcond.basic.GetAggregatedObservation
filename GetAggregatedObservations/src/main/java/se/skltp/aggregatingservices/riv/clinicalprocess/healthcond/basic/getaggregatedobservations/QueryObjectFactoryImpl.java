package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.basic.getaggregatedobservations;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import org.w3c.dom.Node;

import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.GetObservationsType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.QueryObjectFactory;

public class QueryObjectFactoryImpl implements QueryObjectFactory {

	private static final Logger log = LoggerFactory.getLogger(QueryObjectFactoryImpl.class);
	private static final JaxbUtil ju = new JaxbUtil(GetObservationsType.class);

	private String eiServiceDomain;
	public void setEiServiceDomain(String eiServiceDomain) {
		this.eiServiceDomain = eiServiceDomain;
	}

	private String eiCategorization;
	public void setEiCategorization(String eiCategorization) {
		this.eiCategorization = eiCategorization;
	}

	/**
	 * Transformerar GetMeasurement request till EI FindContent request enligt:
	 *
	 * 1. patientId --> registeredResidentIdentification
	 * 2. riv:clinicalprocess:healthcond:basic --> serviceDomain
	 * 3. chb-go --> categorization
	 * 4. sourceSystemId.extension --> sourceSystem
	 */
	@Override
	public QueryObject createQueryObject(Node node) {

		GetObservationsType request = (GetObservationsType)ju.unmarshal(node);

		if (log.isDebugEnabled()) log.debug("Transformed payload for pid: {}", request.getPatientId().getExtension());

		FindContentType fc = new FindContentType();
		fc.setRegisteredResidentIdentification(request.getPatientId().getExtension());
		fc.setServiceDomain(eiServiceDomain);
		fc.setCategorization(eiCategorization);
		fc.setSourceSystem(getSourceSystem(request));

		QueryObject qo = new QueryObject(fc, request);

		return qo;
	}

	/**
	 * Enligt TKB: Används när man vill söka ur ett specifikt källsystem.  
	 * HSA-id för det källsystem inom vilket aktivitetsId är unikt.
	 * 
	 * sourceSystemId.root: Root sätts till OID för HSA-id: 1.2.752.129.2.1.4.1
	 * sourceSystemId.extension: Källsystemets HSA-id.
	 * 
	 * Någon kontroll av OID görs inte här utan ansvaret läggs på källsystemet.
	 * 
	 * @param request
	 * @return
	 */
	String getSourceSystem(GetObservationsType request) {
		if(request.getSourceSystemId() == null || StringUtils.isBlank(request.getSourceSystemId().getExtension())) {
			return null;
		}
		return request.getSourceSystemId().getExtension();
	}
}
