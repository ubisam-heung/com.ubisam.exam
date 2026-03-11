package com.ubisam.exam.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "exam_address_group")
public class AddressGroup {
    
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // @ManyToMany
    // private List<Address> addresses;

}
