package com.vmanager.service.impl;

import java.time.LocalDate;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmanager.entities.Vendor;
import com.vmanager.repository.VendorRepository;
import com.vmanager.service.VendorService;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    private Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

    public VendorServiceImpl() {
        logger.info(getClass().getSimpleName());
    }

    @Override
    public Vendor save(Vendor vendor) {

        vendor.setId(UUID.randomUUID().toString());
        vendor.setCreatedBy(vendor.getName());
        vendor.setCreatedDate(LocalDate.now());
        vendor.setRole("ROLE_USER");
        vendor.setImage("default");

        System.out.println("saved");
        return vendorRepository.save(vendor);
    }

}
