package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.basic.getaggregatedobservations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.GetObservationsType;
import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.ObjectFactory;
import riv.clinicalprocess.healthcond.basic.v1.IIType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.test.producer.TestProducerDb;

public class QueryObjectFactoryTest {

    private static final ObjectFactory OF = new ObjectFactory();
    private QueryObjectFactoryImpl testObject = new QueryObjectFactoryImpl();

    @Before
    public void beforeTest() {
        testObject.setEiCategorization("chb-gm");
        testObject.setEiServiceDomain("riv:clinicalprocess:healthcond:basic");
    }

    @Test
    public void findContentIsCreatedWithSourceSystem() throws Exception {

        // Create request
        GetObservationsType request = new GetObservationsType();
        request.setPatientId(createPatientId());
        request.setSourceSystemHSAId(TestProducerDb.TEST_LOGICAL_ADDRESS_1);
        // End create request

        // Create FindContent request
        QueryObject queryObject = testObject.createQueryObject(createNode(request));
        FindContentType findContent = queryObject.getFindContent();

        // Expected to be set with values
        assertEquals("chb-gm", findContent.getCategorization());
        assertEquals(TestProducerDb.TEST_RR_ID_ONE_HIT, findContent.getRegisteredResidentIdentification());
        assertEquals("riv:clinicalprocess:healthcond:basic", findContent.getServiceDomain());
        assertEquals(TestProducerDb.TEST_LOGICAL_ADDRESS_1, findContent.getSourceSystem());

        // Expected to be null
        assertNull(findContent.getLogicalAddress());
        assertNull(findContent.getBusinessObjectInstanceIdentifier());
        assertNull(findContent.getClinicalProcessInterestId());
        assertNull(findContent.getDataController());
        assertNull(findContent.getMostRecentContent());
        assertNull(findContent.getOwner());

    }

    @Test
    public void findContentIsCreatedWithEmptySourceSystem() throws Exception {

        // Create request
        GetObservationsType request = new GetObservationsType();
        request.setPatientId(createPatientId());
        request.setSourceSystemHSAId("");
        // End create request

        // Create FindContent request
        QueryObject queryObject = testObject.createQueryObject(createNode(request));
        FindContentType findContent = queryObject.getFindContent();

        // Expected to be set with values
        assertEquals("chb-gm", findContent.getCategorization());
        assertEquals(TestProducerDb.TEST_RR_ID_ONE_HIT, findContent.getRegisteredResidentIdentification());
        assertEquals("riv:clinicalprocess:healthcond:basic", findContent.getServiceDomain());

        // Expected to be null
        assertNull(findContent.getLogicalAddress());
        assertNull(findContent.getSourceSystem());
        assertNull(findContent.getBusinessObjectInstanceIdentifier());
        assertNull(findContent.getClinicalProcessInterestId());
        assertNull(findContent.getDataController());
        assertNull(findContent.getMostRecentContent());
        assertNull(findContent.getOwner());

    }

    @Test
    public void findContentIsCreatedWithNullSourceSystem() throws Exception {

        // Create request
        GetObservationsType request = new GetObservationsType();
        request.setPatientId(createPatientId());
        request.setSourceSystemHSAId(null);
        // End create request

        // Create FindContent request
        QueryObject queryObject = testObject.createQueryObject(createNode(request));
        FindContentType findContent = queryObject.getFindContent();

        // Expected to be set with values
        assertEquals("chb-gm", findContent.getCategorization());
        assertEquals(TestProducerDb.TEST_RR_ID_ONE_HIT, findContent.getRegisteredResidentIdentification());
        assertEquals("riv:clinicalprocess:healthcond:basic", findContent.getServiceDomain());

        // Expected to be null
        assertNull(findContent.getLogicalAddress());
        assertNull(findContent.getSourceSystem());
        assertNull(findContent.getBusinessObjectInstanceIdentifier());
        assertNull(findContent.getClinicalProcessInterestId());
        assertNull(findContent.getDataController());
        assertNull(findContent.getMostRecentContent());
        assertNull(findContent.getOwner());

    }

    private IIType createPatientId() {
        IIType iiType = new IIType();
        iiType.setRoot("1.2.752.129.2.1.3.1");
        iiType.setExtension(TestProducerDb.TEST_RR_ID_ONE_HIT);
        return iiType;
    }

    private Node createNode(GetObservationsType request) throws Exception {

        // Since the class GetObservationsType doesn't have an @XmlRootElement annotation we need to use the ObjectFactory to add it.
        JAXBElement<GetObservationsType> requestJaxb = OF.createGetObservations(request);

        // Node to carry marshalled content
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document node = db.newDocument();

        // Marshall
        JAXBContext jc = JAXBContext.newInstance(GetObservationsType.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(requestJaxb, node);
        return node;
    }
}
