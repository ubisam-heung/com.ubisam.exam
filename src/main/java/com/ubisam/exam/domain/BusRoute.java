package com.ubisam.exam.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "example_bus_route")
public class BusRoute {

  @Id
  @GeneratedValue
  private Long id;

  // 노선 이름
  private String busRouteName;
  // 노선 시작점
  private String busRouteStart;
  // 노선 종점
  private String busRouteEnd;

  // 하나의 정류장은 여러 노선이 배정될 수 있고,
  // 하나의 노선은 여러 정류장이 배정될 수 있음.
  // @ManyToMany
  // private List<BusStop> busStops;

  // 하나의 노선에는 여러 대의 버스가 배정될 수 있음.
  // @OneToMany(mappedBy = "busRoute")
  // private List<Bus> buses;

  // 검색용 필드 추가
  @Transient
  private String searchName;

  @Transient
  private String searchStart;

  @Transient
  private String searchEnd;
  
}
