package com.ubisam.exam.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "example_bus_stop")
public class BusStop {

  @Id
  @GeneratedValue
  private Long id;

  // 정류장 이름
  private String busStopName;
  // 정류장 위치
  private String busStopLocation;

  // 하나의 정류장은 여러 노선이 배정될 수 있고,
  // 하나의 노선은 여러 정류장이 배정될 수 있음.
  // @ManyToMany(mappedBy = "busStops")
  // private List<BusRoute> busRoutes;

  // 검색용 필드 추가
  @Transient
  private String searchName;

  @Transient
  private String searchLocation;

  
}
