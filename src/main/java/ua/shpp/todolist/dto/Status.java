package ua.shpp.todolist.dto;

import java.util.Set;

public enum Status {
    PLANNED {
        @Override
        public Set<Status> getAllowedState() {
            return Set.of(PROGRESS, POSTPONED, NOTIFIED, SIGNED, CANCELLED);
        }
    },
    PROGRESS {
        @Override
        public Set<Status> getAllowedState() {
            return Set.of(POSTPONED, NOTIFIED, SIGNED, CANCELLED);
        }
    },
    POSTPONED {
        @Override
        public Set<Status> getAllowedState() {
            return Set.of(NOTIFIED, SIGNED, CANCELLED);
        }
    },
    NOTIFIED {
        @Override
        public Set<Status> getAllowedState() {
            return Set.of(SIGNED, DONE, CANCELLED);
        }
    },
    SIGNED {
        @Override
        public Set<Status> getAllowedState() {
            return Set.of(DONE, CANCELLED);
        }
    },
    DONE {
        @Override
        public Set<Status> getAllowedState() {
            return Set.of(this);
        }
    },
    CANCELLED {
        @Override
        public Set<Status> getAllowedState() {
            return Set.of(this);
        }
    };

    public abstract Set<Status> getAllowedState();
}
