package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.RoleDTO;
import com.callumezmoney.timefit.model.Role;
import com.callumezmoney.timefit.service.RoleService;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
public class RoleMapper implements WebMapper<Role, RoleDTO>{

    private Environment environment;
    private RoleService roleService;

    @Override
    public Role dtoToEntity(RoleDTO dto) {
        Role role = new Role(
                dto.getId(),
                dto.getName()
        );
        return role;
    }

    @Override
    public RoleDTO entityToDto(Role entity) {
        RoleDTO role = new RoleDTO(
                entity.getId(),
                entity.getName()
        );
        return role;
    }

    @Override
    public String toURI(Role object) {
        return environment.getProperty("callumezmoney.app.webapiprefix.program") + object.getId();
    }

    @Override
    public Role fromURI(String uri) {
        return roleService.getRole(getIdFromURI(uri, environment));
    }
}
