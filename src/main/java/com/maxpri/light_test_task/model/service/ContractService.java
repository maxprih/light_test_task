package com.maxpri.light_test_task.model.service;

import com.maxpri.light_test_task.model.dto.GetContractsDto;
import com.maxpri.light_test_task.model.dto.MessageResponse;
import com.maxpri.light_test_task.model.entity.Admin;
import com.maxpri.light_test_task.model.entity.Contract;
import com.maxpri.light_test_task.model.entity.ContractStatus;
import com.maxpri.light_test_task.model.entity.User;
import com.maxpri.light_test_task.model.exceptions.AdminNotFoundException;
import com.maxpri.light_test_task.model.exceptions.ContractAlreadyExistsException;
import com.maxpri.light_test_task.model.exceptions.NoSuchContractException;
import com.maxpri.light_test_task.model.repository.AdminRepository;
import com.maxpri.light_test_task.model.repository.ContractRepository;
import com.maxpri.light_test_task.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author max_pri
 */
@Service
public class ContractService {
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final ContractRepository contractRepository;

    @Autowired
    public ContractService(AdminRepository adminRepository, UserRepository userRepository, ContractRepository contractRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.contractRepository = contractRepository;
    }


    @Transactional
    public MessageResponse createContract(UserDetails userDetails) {
        User user = userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));
        Admin admin = adminRepository.findAdminById(user.getId())
                .orElseThrow(() -> new AdminNotFoundException());
        if (contractRepository.existsByAdmin_Id(user.getId())) {
            throw new ContractAlreadyExistsException();
        }

        Contract contract = Contract
                .builder()
                .status(ContractStatus.NOT_OK)
                .admin(admin)
                .build();
        contractRepository.save(contract);
        return new MessageResponse("Successfully created contract");
    }

    @Transactional
    public MessageResponse signContract(Long contractId) {
        if (!contractRepository.existsById(contractId)) {
            throw new NoSuchContractException();
        }
        contractRepository.setContractStatus(ContractStatus.OK, contractId);
        return new MessageResponse("Successfully signed contract with id " + contractId);
    }

    public GetContractsDto getContracts() {
        return new GetContractsDto(
                contractRepository.findAllContracts()
        );
    }
}
