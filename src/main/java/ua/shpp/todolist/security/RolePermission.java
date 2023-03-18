package ua.shpp.todolist.security;

public enum RolePermission {
    TASK_PLANNED("task:planned"),
    TASK_PROGRESS("task:progress"),
    TASK_CANCELLED("task:cancelled");

    private final String permission;

    RolePermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}