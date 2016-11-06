package com.mcbain.sitewatcher.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcbain.sitewatcher.domain.Probe;
import com.mcbain.sitewatcher.repository.ProbeRepository;
import com.mcbain.sitewatcher.service.ProbeService;

@Service
@Transactional
public class ProbeServiceImpl implements ProbeService {

	@Autowired
	ProbeRepository repo;
	
	@Override
	public Iterable<Probe> findAll() {
		return repo.findAll();
	}
	
	@Override
	public Probe findOne(String probeId) {
		return repo.findOne(probeId);
	}

	@Override
	public Probe createProbe(Probe theProbe) {
		return repo.save(theProbe);
	}

	@Override
	public Probe updateProbe(Probe theProbe) {
		return repo.save(theProbe);
	}

	@Override
	public void deleteProbe(String probeId) {
		repo.delete(probeId);
	}
	
}
