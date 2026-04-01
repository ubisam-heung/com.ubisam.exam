package com.ubisam.exam.api.busStops;

import com.ubisam.exam.domain.BusStop;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface BusStopRepository extends RestfulJpaRepository<BusStop, Long>{
  
}
