package com.maxpri.light_test_task.model.controller;

import com.maxpri.light_test_task.model.dto.GetContractsDto;
import com.maxpri.light_test_task.model.dto.MessageResponse;
import com.maxpri.light_test_task.model.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author max_pri
 */
// big bebra
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/contract")
public class ContractController {
    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }


    @PostMapping("/create-contract")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MessageResponse> createContract(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        MessageResponse result = contractService.createContract(userDetails);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/get-contracts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<GetContractsDto> getContracts() {
        GetContractsDto result = contractService.getContracts();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/sign-contract/{contractId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MessageResponse> signContract(@PathVariable(value="contractId") Long contractId) {
        MessageResponse result = contractService.signContract(contractId);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
}
