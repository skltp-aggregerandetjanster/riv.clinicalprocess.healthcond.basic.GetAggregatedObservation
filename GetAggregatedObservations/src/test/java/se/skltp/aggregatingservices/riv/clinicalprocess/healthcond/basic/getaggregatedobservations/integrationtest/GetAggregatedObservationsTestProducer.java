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
