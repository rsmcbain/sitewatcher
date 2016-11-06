package com.mcbain.sitewatcher.service;

import com.mcbain.sitewatcher.domain.Probe;

public interface ProbeService {
	Iterable<Probe> findAll();
	Probe findOne(String probeId);
	Probe createProbe(Probe theProbe);
	Probe updateProbe(Probe theProbe);
	void deleteProbe(String probeId);
}
