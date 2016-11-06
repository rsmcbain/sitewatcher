package com.mcbain.sitewatcher.rest;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mcbain.sitewatcher.domain.Probe;
import com.mcbain.sitewatcher.rest.exceptions.BadRequestException;
import com.mcbain.sitewatcher.rest.exceptions.ConflictException;
import com.mcbain.sitewatcher.rest.exceptions.NotFoundException;
import com.mcbain.sitewatcher.service.ProbeService;

@RestController("probeControllerV1")
@RequestMapping(path={"/v1/","/latest/"})
@CrossOrigin
public class ProbeController {
	@Autowired
	ProbeService service;
	
	private boolean probeExists(String probeId) {
		Probe probe = service.findOne(probeId);
		return (probe != null);
	}
	
	@RequestMapping(value="/probes",method=RequestMethod.GET)
	public ResponseEntity<Iterable<Probe>> getProbes() {
		Iterable<Probe> allProbes = service.findAll();
		return new ResponseEntity<Iterable<Probe>>(allProbes, HttpStatus.OK);
	}
	
	@RequestMapping(value="/probes/{probeId}",method=RequestMethod.GET)
	public ResponseEntity<Probe> getProbe(@PathVariable String probeId) {
		if (!probeExists(probeId)) {
			throw new NotFoundException("Probe with id=" + probeId + " not found.");
		}
		Probe probe = service.findOne(probeId);
		return new ResponseEntity<>(probe, HttpStatus.OK);
	}
	
	@RequestMapping(value="/probes",method=RequestMethod.POST)
	public ResponseEntity<Probe> createProbe(@Valid @RequestBody Probe theProbe) {
		System.out.println(theProbe.toString());
		if ((theProbe.getId() != null) && (probeExists(theProbe.getId()) ) ) {
			throw new ConflictException("Creation failed. Probe with id=" + theProbe.getId() + " already exists.");
		}
		System.out.println(theProbe.toString());
		Probe probe = service.createProbe(theProbe);
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newProbeUri = ServletUriComponentsBuilder
								.fromCurrentRequest()
								.path("/{id}")
								.buildAndExpand(probe.getId())
								.toUri();
		responseHeaders.setLocation(newProbeUri);
		return new ResponseEntity<Probe>(probe,responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/probes/{probeId}",method=RequestMethod.PUT)
	public ResponseEntity<Probe> updateProbe(@PathVariable String probeId, @Valid @RequestBody Probe theProbe) {
		if (!probeExists(probeId)) {
			throw new NotFoundException("Probe with id=" + theProbe.getId() + " not found.");
		}
		if ( (theProbe == null ) || (! probeId.equals(theProbe.getId())) ) {
			throw new BadRequestException("Request does not match probe with id=" + probeId);
		}
		Probe probe = service.updateProbe(theProbe);
		return new ResponseEntity<>(probe, HttpStatus.OK);
	}
	
	@RequestMapping(value="/probes/{probeId}",method=RequestMethod.DELETE)
	public ResponseEntity<Probe> deleteProbe(@PathVariable String probeId) {
		if (!probeExists(probeId)) {
			throw new NotFoundException("Probe with id=" + probeId + " not found.");
		}
		service.deleteProbe(probeId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
