package com.callumezmoney.timefit.mapper;

import com.callumezmoney.timefit.dto.RoleDTO;
import com.callumezmoney.timefit.model.Role;
import com.callumezmoney.timefit.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static com.callumezmoney.timefit.util.MapperUtils.getIdFromURI;

@Data
@Component
@AllArgsConstructor
public class RoleMapper implements WebMapper<Role, RoleDTO>{

    private Environment environment;
    private RoleService roleService;

    @Override
    public Role dtoToEntity(RoleDTO dto) {
        return roleService.getRole(dto.getName().name());
    }

    @Override
    public RoleDTO entityToDto(Role entity) {
        return new RoleDTO(
                entity.getName()
        );
    }

    public static String toURI(Role object, Environment environment) {
        return environment.getProperty("callumezmoney.app.webapiprefix.role") + "/" + object.getId();
    }

    @Override
    public Role fromURI(String uri) {
        return roleService.getRole(getIdFromURI(uri, environment));
    }
}
