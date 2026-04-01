package com.ubisam.exam.api.buses;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ubisam.exam.domain.Bus;

public interface BusRepository extends JpaRepository<Bus, Long>, JpaSpecificationExecutor<Bus>{

  List<Bus> findByBusNumber(Integer busNumber);
  List<Bus> findByBusType(String busType);
  
}
