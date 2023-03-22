package ua.shpp.todolist.services;

import ua.shpp.todolist.dto.TaskDto;
import ua.shpp.todolist.model.TaskEntity;

public class DtoProjection {
    private DtoProjection() {
    }

    public static TaskEntity dtoToEntity (TaskDto dto) {
        return new TaskEntity(dto.getAction(), dto.getPlannedTime(), dto.getStatus());
    }

    public static TaskDto entityToDto (TaskEntity entity) {
        return new TaskDto(entity.getId(), entity.getAction(), entity.getPlannedTime(), entity.getStatus());
    }
}
