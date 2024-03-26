package com.siga.ecp.tn.service.mapper;

import com.siga.ecp.tn.domain.SoldeConge;
import com.siga.ecp.tn.domain.User;
import com.siga.ecp.tn.exception.UserNotFoundException;
import com.siga.ecp.tn.repository.UserRepository;
import com.siga.ecp.tn.service.dto.SoldeCongeDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link com.siga.ecp.tn.domain.SoldeConge} and its DTO called {@link com.siga.ecp.tn.service.dto.SoldeCongeDTO}.
 */
@Service
public class SoldeCongeMapper {

    private final UserRepository userRepository;

    public SoldeCongeMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SoldeCongeDTO soldeCongeToSoldeCongeDTO(SoldeConge soldeConge) {
        return new SoldeCongeDTO(soldeConge);
    }

    public SoldeConge soldeCongeDTOToSoldeConge(SoldeCongeDTO soldeCongeDTO) {
        if (soldeCongeDTO == null) {
            return null;
        } else {
            SoldeConge soldeConge = new SoldeConge();
            soldeConge.setSolde(soldeCongeDTO.getSolde());
            soldeConge.setYear(soldeCongeDTO.getYear());
            Optional<User> user = userRepository.findOneByLogin(soldeCongeDTO.getUser());
            if (user.isEmpty()) {
                throw new UserNotFoundException();
            }
            soldeConge.setUser(user.get());
            if (soldeCongeDTO.getCreatedBy() != null) {
                soldeConge.setCreatedBy(user.get().getLogin());
            }
            if (soldeCongeDTO.getCreatedDate() != null) {
                soldeConge.setCreatedDate(soldeCongeDTO.getCreatedDate());
            }
            return soldeConge;
        }
    }

    public SoldeConge updateSoldeCongeFromDTO(SoldeCongeDTO soldeCongeDTO, SoldeConge soldeConge) {
        soldeConge.setSolde(soldeCongeDTO.getSolde());
        soldeConge.setYear(soldeCongeDTO.getYear());
        Optional<User> user = userRepository.findOneByLogin(soldeCongeDTO.getUser());
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        soldeConge.setUser(user.get());
        if (soldeCongeDTO.getCreatedBy() != null) {
            soldeConge.setCreatedBy(user.get().getLogin());
        }
        if (soldeCongeDTO.getCreatedDate() != null) {
            soldeConge.setCreatedDate(soldeCongeDTO.getCreatedDate());
        }
        return soldeConge;
    }

    public List<SoldeCongeDTO> soldeCongesToSoldeCongeDTOs(List<SoldeConge> soldeConges) {
        return soldeConges.stream().filter(Objects::nonNull).map(this::soldeCongeToSoldeCongeDTO).collect(Collectors.toList());
    }

    public List<SoldeConge> soldeCongeDTOsToSoldeConges(List<SoldeCongeDTO> soldeCongeDTOs) {
        return soldeCongeDTOs.stream().filter(Objects::nonNull).map(this::soldeCongeDTOToSoldeConge).collect(Collectors.toList());
    }

}
