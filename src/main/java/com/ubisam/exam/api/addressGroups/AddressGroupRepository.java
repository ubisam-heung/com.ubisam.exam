package com.ubisam.exam.api.addressGroups;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubisam.exam.domain.AddressGroup;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface AddressGroupRepository extends RestfulJpaRepository<AddressGroup, Long>{
    
}
