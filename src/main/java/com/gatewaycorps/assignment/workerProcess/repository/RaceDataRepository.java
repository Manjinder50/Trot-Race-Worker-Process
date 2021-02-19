package com.gatewaycorps.assignment.workerProcess.repository;

import com.gatewaycorps.assignment.workerProcess.entity.TrotEventEntity;
import org.springframework.data.repository.CrudRepository;

public interface RaceDataRepository extends CrudRepository<TrotEventEntity,Long> {
}
