package com.vmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vmanager.entities.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, String> {

}
