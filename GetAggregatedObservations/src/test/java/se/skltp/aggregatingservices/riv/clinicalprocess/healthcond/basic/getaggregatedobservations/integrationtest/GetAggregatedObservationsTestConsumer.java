package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.basic.getaggregatedobservations.integrationtest;

import static se.skltp.agp.test.producer.TestProducerDb.TEST_RR_ID_ONE_HIT;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.healthcond.basic.getobservations.v1.rivtabp21.GetObservationsResponderInterface;
import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.GetObservationsResponseType;
import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.GetObservationsType;
import riv.clinicalprocess.healthcond.basic.v1.CVType;
import riv.clinicalprocess.healthcond.basic.v1.IIType;
import riv.clinicalprocess.healthcond.basic.v1.TimePeriodType;
import se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.basic.getaggregatedobservations.GetAggregatedObservationsMuleServer;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;
import se.skltp.agp.test.consumer.AbstractTestConsumer;
import se.skltp.agp.test.consumer.SoapHeaderCxfInterceptor;

public class GetAggregatedObservationsTestConsumer extends AbstractTestConsumer<GetObservationsResponderInterface> {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedObservationsTestConsumer.class);

	public static void main(String[] args) {
		String serviceAddress = GetAggregatedObservationsMuleServer.getAddress("SERVICE_INBOUND_URL");
		String personnummer = TEST_RR_ID_ONE_HIT;

		GetAggregatedObservationsTestConsumer consumer = new GetAggregatedObservationsTestConsumer(serviceAddress, SAMPLE_SENDER_ID, SAMPLE_ORIGINAL_CONSUMER_HSAID);
		Holder<GetObservationsResponseType> responseHolder = new Holder<GetObservationsResponseType>();
		Holder<ProcessingStatusType> processingStatusHolder = new Holder<ProcessingStatusType>();

		consumer.callService("logical-adress", personnummer, processingStatusHolder, responseHolder);


        // TODO: CHANGE GENERATED SAMPLE CODE - START
		log.info("Returned #observationss = " + responseHolder.value.getObservationGroup().size());
        // TODO: CHANGE GENERATED SAMPLE CODE - END

	}

	public GetAggregatedObservationsTestConsumer(String serviceAddress, String senderId, String originalConsumerHsaId) {

		// Setup a web service proxy for communication using HTTPS with Mutual Authentication
		super(GetObservationsResponderInterface.class, serviceAddress, senderId, originalConsumerHsaId);
	}

	public void callService(String logicalAddress, String registeredResidentId, String sourceSystemId, Holder<ProcessingStatusType> processingStatusHolder, Holder<GetObservationsResponseType> responseHolder) {
		callService(logicalAddress, registeredResidentId, sourceSystemId, null, null, processingStatusHolder, responseHolder);
	}

	public void callService(String logicalAddress, String registeredResidentId, Holder<ProcessingStatusType> processingStatusHolder, Holder<GetObservationsResponseType> responseHolder){
		callService(logicalAddress, registeredResidentId, null, null, null, processingStatusHolder, responseHolder);
	}

	public void callService(String logicalAddress, String registeredResidentId, String sourceSystemId, String starTime, String endTime, Holder<ProcessingStatusType> processingStatusHolder, Holder<GetObservationsResponseType> responseHolder) {

		log.debug("Calling GetObservations-soap-service with Registered Resident Id = {}", registeredResidentId);

		GetObservationsType request = new GetObservationsType();


        // TODO: CHANGE GENERATED SAMPLE CODE - START
		request.setPatientId(createID("1.2.752.129.2.1.3.1", registeredResidentId));

		request.setTime(createTimePeriodType(starTime, endTime));

		request.setSourceSystemHSAId(sourceSystemId);

        // TODO: CHANGE GENERATED SAMPLE CODE - END

		GetObservationsResponseType response = _service.getObservations(logicalAddress, request);
		responseHolder.value = response;

		processingStatusHolder.value = SoapHeaderCxfInterceptor.getLastFoundProcessingStatus();
	}

	public void callServiceIncludeValuesInAllReqParams(String logicalAddress, String registeredResidentId, String starTime, String endTime, Holder<ProcessingStatusType> processingStatusHolder, Holder<GetObservationsResponseType> responseHolder) {

		log.debug("Calling GetObservations-soap-service with Registered Resident Id = {}", registeredResidentId);

		GetObservationsType request = new GetObservationsType();

		// TODO: CHANGE GENERATED CODE - START

		request.setPatientId(createID("1.2.752.129.2.1.3.1", registeredResidentId));

		request.setTime(createTimePeriodType(starTime, endTime));

		request.getObservationType().add(createType());

		request.getObservationType().add(createType());

		request.getObservationId().add(createID("1.2.752.129.2.1.2.1", "HSA-ID-1"));

		request.setCareGiverId(createID("1.2.752.129.2.1.4.1", "HSA-ID-1"));

		request.setSourceSystemHSAId("HSA-ID-1");

		request.setCareUnitId(createID("1.2.752.129.2.1.4.1", "HSA-ID-1"));

		// TODO: CHANGE GENERATED CODE - END

		GetObservationsResponseType response = _service.getObservations(logicalAddress, request);
		responseHolder.value = response;

		processingStatusHolder.value = SoapHeaderCxfInterceptor.getLastFoundProcessingStatus();
	}

	private CVType createType() {
		CVType cvType = new CVType();
		cvType.setCode("CODE");
		cvType.setCodeSystem("CODESYSTEM");
		cvType.setCodeSystemVersion("CODESYSTEMVERSION");
		cvType.setDisplayName("DISPLAYNAME");
		return cvType;
	}

	private TimePeriodType createTimePeriodType(String starTime, String endTime) {
		TimePeriodType periodType = new TimePeriodType();
		periodType.setEnd(endTime);
		periodType.setStart(starTime);
		return periodType;
	}

	private IIType createID(String type, String value) {
		IIType iiType = new IIType();
		iiType.setRoot(type);
		iiType.setExtension(value);
		return iiType;
	}
}
