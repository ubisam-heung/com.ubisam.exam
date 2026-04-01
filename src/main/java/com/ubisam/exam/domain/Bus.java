package com.ubisam.exam.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "example_bus")
public class Bus {

  @Id
  @GeneratedValue
  private Long id;

  // 버스 번호
  private Integer busNumber;
  // 버스 정원
  private Integer busCapacity;
  // 버스 상태
  private String busStatus;
  // 버스 종류
  private String busType;

  // 한 버스는 하나의 노선만을 운행함.
  // @ManyToOne
  // private BusRoute busRoute;

  // 한 버스에는 한 명의 운전기사가 배정됌.
  // @ManyToOne
  // private BusDriver busDriver;

}