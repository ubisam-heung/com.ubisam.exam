package com.ubisam.exam.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "example_bus_driver")
public class BusDriver {

  @Id
  @GeneratedValue
  private Long id;

  // 운전수 이름
  private String busDriverName;
  // 운전수 라이센스
  private String busDriverLicense;
  // 운전수 폰번호
  private String busDriverPhone;

  // 한 명의 운전기사는 여러 대의 버스를 운전할 수 있음.
  // @OneToMany(mappedBy = "busDriver")
  // private List<Bus> buses;

  // 검색용 필드 추가
  @Transient
  private String searchName;

  @Transient
  private String searchLicense;
  
  
}
