/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.aggregatingservices.riv.clinicalprocess.healthcond.basic.getaggregatedobservations;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import riv.clinicalprocess.healthcond.basic.getobservationsresponder.v1.GetObservationsResponseType;
import riv.clinicalprocess.healthcond.basic.v1.IIType;
import riv.clinicalprocess.healthcond.basic.v1.ObservationGroupType;
import riv.clinicalprocess.healthcond.basic.v1.PatientType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.ResponseListFactory;
import se.skltp.agp.test.producer.TestProducerDb;

public class ResponseListFactoryTest {

    private ResponseListFactory testObject = new ResponseListFactoryImpl();

    @Test
    public void testQueryObjectFactory() throws Exception {

        QueryObject queryObject = createQueryObject(TestProducerDb.TEST_RR_ID_MANY_HITS);
        List<Object> aggregatedResponseList = createAggregatedResponseList(TestProducerDb.TEST_RR_ID_MANY_HITS);

        String actualXml = testObject.getXmlFromAggregatedResponse(queryObject, aggregatedResponseList);
        String expectedXml = getFile("testfiles/responseListFactoryTest.xml");

        assertEquals(expectedXml, actualXml);
    }

    private List<Object> createAggregatedResponseList(String rrId) {
        List<Object> aggregatedResponseList = new ArrayList<Object>();
        aggregatedResponseList.add(createResponseType(rrId));
        aggregatedResponseList.add(createResponseType(rrId));
        return aggregatedResponseList;
    }

    private QueryObject createQueryObject(String rrID) {
        FindContentType findContentType = new FindContentType();
        findContentType.setRegisteredResidentIdentification(rrID);
        QueryObject queryObject = new QueryObject(findContentType, new Object());
        return queryObject;
    }

    private Object createResponseType(String rrId) {
        GetObservationsResponseType responseType = new GetObservationsResponseType();
        responseType.getObservationGroup().add(createObservations(rrId));
        return responseType;
    }

    private ObservationGroupType createObservations(String rrId) {
        ObservationGroupType observations = new ObservationGroupType();
        observations.setPatient(createPatient(rrId));
        return observations;
    }

    private PatientType createPatient(String rrId) {
        PatientType patient = new PatientType();
        patient.setId(createID(rrId));
        return patient;
    }

    private IIType createID(String rrId) {
        IIType id = new IIType();
        id.setExtension(rrId);
        id.setRoot("1.2.752.129.2.1.3.1");
        return id;
    }

    private String getFile(String fileName) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream(fileName));
    }
}
