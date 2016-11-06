package com.mcbain.sitewatcher.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mcbain.sitewatcher.domain.Probe;

@Repository
public interface ProbeRepository extends CrudRepository<Probe,String> {

}
