package com.siga.ecp.tn.service.mapper;

import com.siga.ecp.tn.domain.DemandeConge;
import com.siga.ecp.tn.domain.User;
import com.siga.ecp.tn.repository.UserRepository;
import com.siga.ecp.tn.service.TypeCongeService;
import com.siga.ecp.tn.service.dto.DemandeCongeDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link com.siga.ecp.tn.domain.DemandeConge} and its DTO called {@link com.siga.ecp.tn.service.dto.DemandeCongeDTO}.
 */
@Service
public class DemandeCongeMapper {

    private final TypeCongeService typeCongeService;
    private final UserRepository userRepository;

    public DemandeCongeMapper(TypeCongeService typeCongeService, UserRepository userRepository) {
        this.typeCongeService = typeCongeService;
        this.userRepository = userRepository;
    }

    public List<DemandeCongeDTO> demandesCongeToDemandeCongeDTOs(List<DemandeConge> demandesConge) {
        return demandesConge.stream().filter(Objects::nonNull).map(this::demandeCongeToDemandeCongeDTO).collect(Collectors.toList());
    }

    public DemandeCongeDTO demandeCongeToDemandeCongeDTO(DemandeConge demandeConge) {
        return new DemandeCongeDTO(demandeConge);
    }

    public List<DemandeConge> demandeCongeDTOsToDemandesConge(List<DemandeCongeDTO> demandeCongeDTOs) {
        return demandeCongeDTOs.stream().filter(Objects::nonNull).map(this::demandeCongeDTOToDemandeConge).collect(Collectors.toList());
    }

    public DemandeConge demandeCongeDTOToDemandeConge(DemandeCongeDTO demandeCongeDTO) {
        if (demandeCongeDTO == null) {
            return null;
        } else {
            DemandeConge demandeConge = new DemandeConge();
            if (demandeCongeDTO.getId() != null) {
                demandeConge.setId(demandeCongeDTO.getId());
            }
            if (demandeCongeDTO.getVld() != null) {
                demandeConge.setVld(demandeCongeDTO.getVld());
            }
            if (demandeCongeDTO.getTelephone() != null) {
                demandeConge.setTelephone(demandeCongeDTO.getTelephone());
            }
            if (demandeCongeDTO.getType() != null) {
                demandeConge.setType(typeCongeService.findByLibFr(demandeCongeDTO.getType()));
            }
            Optional<User> user = userRepository.findOneByLogin(demandeCongeDTO.getUser());
            if (demandeCongeDTO.getUser() != null && (user.isPresent())) {
                demandeConge.setUser(user.get());
            }
            if (demandeCongeDTO.getDateDebut() != null) {
                demandeConge.setDateDebut(demandeCongeDTO.getDateDebut());
            }
            if (demandeCongeDTO.getDateFin() != null) {
                demandeConge.setDateFin(demandeCongeDTO.getDateFin());
            }
            if (demandeCongeDTO.getAddress() != null) {
                demandeConge.setAddress(demandeCongeDTO.getAddress());
            }
            if (demandeCongeDTO.getNotes() != null) {
                demandeConge.setNotes(demandeCongeDTO.getNotes());
            }
            if (demandeCongeDTO.getCreatedBy() != null) {
                demandeConge.setCreatedBy(demandeCongeDTO.getCreatedBy());
            }
            if (demandeCongeDTO.getCreatedDate() != null) {
                demandeConge.setCreatedDate(demandeCongeDTO.getCreatedDate());
            }

            return demandeConge;
        }
    }

}
