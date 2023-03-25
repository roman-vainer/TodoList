package ua.shpp.todolist.model;

public enum RolePermission {
    TASK_PLANNED("task:planned"),
    TASK_PROGRESS("task:progress"),
    TASK_DELETED("task:deleted");

    private final String permission;

    RolePermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}