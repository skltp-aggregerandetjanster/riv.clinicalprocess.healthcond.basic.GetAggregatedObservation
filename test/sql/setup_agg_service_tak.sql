


SET @serviceContractNamespace = 'urn:riv:clinicalprocess:healthcond:basic:GetObservations:1:rivtabp21';
SET @popularDomainName = 'Hantera hälsorelaterade tillstånd, basuppgifter - GetObservations';
SET @pathToServices = 'http://localhost:8081/GetAggregatedObservations/service/v1';
SET @producerHsaId = 'GetAggregatedObservations';
SET @consumerHsaId = 'client';

-- ===========================================================================
-- Setup routing in TAK
-- ===========================================================================

-- Sätt upp tjänstekontrakt om det inte redan finns
INSERT INTO `Tjanstekontrakt` (`beskrivning`, `namnrymd`, `version`) VALUES (@popularDomainName, @serviceContractNamespace, 0);

-- Sätt upp logiska adressater Inera om den inte finns redan
INSERT INTO LogiskAdressat (beskrivning, hsaId, version) VALUES('Inera', '5565594230', 0);

-- Sätt upp den aggregerade tjänsten som producent
INSERT INTO Tjanstekomponent (adress, beskrivning, hsaId, version) VALUES(@pathToServices, @popularDomainName, @producerHsaId, 0);

-- Sätt upp logisk adress till den aggregerade tjänsten
INSERT INTO LogiskAdress (fromTidpunkt, tomTidpunkt, version, logiskAdressat_id, rivVersion_id, tjanstekontrakt_id, tjansteproducent_id)
select '2013-10-07', '2113-10-07', 0, adressat.id, riv.id, kontrakt.id, producent.id from LogiskAdressat as adressat, RivVersion as riv,
Tjanstekontrakt as kontrakt, Tjanstekomponent as producent
where adressat.hsaId = '5565594230'
and riv.namn = 'RIVTABP21'
and kontrakt.namnrymd = @serviceContractNamespace
and producent.hsaId = @producerHsaId;

-- Sätt upp behörighet för klient till aggregerade tjänsten
INSERT INTO `Anropsbehorighet` (`fromTidpunkt`, `integrationsavtal`, `tomTidpunkt`, `version`, `logiskAdressat_id`, `tjanstekonsument_id`, `tjanstekontrakt_id`)
select '2013-01-01', 'Aggregerandetjänst' , '2114-12-31', 0, adressat.id, konsument.id, kontrakt.id
from `LogiskAdressat` as adressat, `Tjanstekontrakt` as kontrakt, `Tjanstekomponent` as konsument
where adressat.hsaId = '5565594230'
and kontrakt.namnrymd = @serviceContractNamespace
and konsument.hsaId = @consumerHsaId;
