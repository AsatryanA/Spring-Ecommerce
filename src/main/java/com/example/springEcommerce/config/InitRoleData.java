package com.example.springEcommerce.config;

import com.example.springEcommerce.model.entity.RoleEntity;
import com.example.springEcommerce.repository.RoleRepository;
import com.example.springEcommerce.util.constants.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitRoleData {
    private final RoleRepository roleRepository;

  //  @PostConstruct
    public void init() {
        roleRepository.save(RoleEntity.builder().name(Role.USER).build());
        roleRepository.save(RoleEntity.builder().name(Role.ADMIN).build());
    }
}
