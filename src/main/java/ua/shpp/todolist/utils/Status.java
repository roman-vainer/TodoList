package ua.shpp.todolist.utils;
import java.util.Arrays;
import java.util.List;

public enum Status {
    PLANNED {
        @Override
        public List<Status> getAllowedState() {
            return Arrays.asList(PROGRESS, POSTPONED, NOTIFIED, SIGNED, CANCELLED);
        }
    },
    PROGRESS {
        @Override
        public List<Status> getAllowedState() {
            return Arrays.asList(POSTPONED, NOTIFIED, SIGNED, CANCELLED);
        }
    },
    POSTPONED {
        @Override
        public List<Status> getAllowedState() {
            return Arrays.asList(NOTIFIED, SIGNED, CANCELLED);
        }
    },
    NOTIFIED {
        @Override
        public List<Status> getAllowedState() {
            return Arrays.asList(SIGNED, DONE, CANCELLED);
        }
    },
    SIGNED {
        @Override
        public List<Status> getAllowedState() {
            return Arrays.asList(DONE, CANCELLED);
        }
    },
    DONE {
        @Override
        public List<Status> getAllowedState() {
            return List.of(this);
        }
    },
    CANCELLED {
        @Override
        public List<Status> getAllowedState() {
            return List.of(this);
        }
    };

    public abstract List<Status> getAllowedState();
}
