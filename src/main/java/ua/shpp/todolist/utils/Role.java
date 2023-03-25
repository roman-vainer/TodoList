package ua.shpp.todolist.utils;


import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ua.shpp.todolist.utils.RolePermission.*;

public enum Role {
    EMPLOYEE(Set.of(TASK_PROGRESS)),
    ADMIN(Set.of(TASK_PLANNED, TASK_PROGRESS, TASK_CANCELLED)),
    MANAGER(Set.of(TASK_PLANNED, TASK_CANCELLED));

    private final Set<RolePermission> permissions;

    Role(Set<RolePermission> permissions) {
        this.permissions = permissions;
    }

    public Set<RolePermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> rolePermission = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        rolePermission.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return rolePermission;
    }
}
