package com.xworkz.services;

import com.xworkz.dto.VendorDTO;
import com.xworkz.exception.AccountUnderVerificationException;
import com.xworkz.exception.InValidateOTPException;
import com.xworkz.exception.OTPExpiredException;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface VendorService {

    boolean checkEmailExistence(String email);

    boolean checkContactNumberExistence(long contactNumber);

    boolean checkGSTNumberExistence(String GSTnumber);

    boolean checkWebsiteExistence(String website);

    Set<ConstraintViolation<VendorDTO>> validateAndSaveVendorDTO(VendorDTO vendorDTO);

    boolean saveLoginOTP(String email);

    boolean validateOTPAndLogin(String email, String otp)
            throws InValidateOTPException, OTPExpiredException, AccountUnderVerificationException;

    VendorDTO getVendorDetailsByEmail(String email);

    VendorDTO getVendorDetailsById(int id);

    String findImagePathByEmail(String email);

    public Set<ConstraintViolation<VendorDTO>> validateAndVendorUpdateProfile(String email, VendorDTO vendorDTO)
            throws IOException;

    List<VendorDTO> readAllVendorData();

    boolean updateVendorStatus(int id, String status);

    boolean Sendmessage(String email, String message);
}