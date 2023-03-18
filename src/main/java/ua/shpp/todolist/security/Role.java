package ua.shpp.todolist.security;


import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ua.shpp.todolist.security.RolePermission.*;

public enum Role {
    EMPLOYEE(Sets.newHashSet(TASK_PROGRESS, TASK_DONE)),
    ADMIN(Sets.newHashSet(TASK_PLANNED, TASK_PROGRESS, TASK_DONE, TASK_CANCELLED)),
    MANAGER(Sets.newHashSet(TASK_PLANNED, TASK_CANCELLED));

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
