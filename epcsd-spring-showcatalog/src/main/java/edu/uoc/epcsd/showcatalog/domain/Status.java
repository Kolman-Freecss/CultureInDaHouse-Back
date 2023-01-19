package edu.uoc.epcsd.showcatalog.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Status {

    CREATED(0) {
        @Override
        public boolean isCreated() {
            return true;
        }
    },
    OPEN(1) {
        @Override
        public boolean isOpen() {
            return true;
        }
    },
    
    CANCELLED(2) {
        @Override
        public boolean isCancelled() {
            return true;
        }
    },
	CLOSED(3) {
        @Override
        public boolean isClosed() {
            return true;
        }
    };

    private final int status;

    public boolean isCreated() {
        return false;
    }

    public boolean isClosed() {
		return false;
	}

	public boolean isOpen() {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    public int getStatus() {
        return status;
    }
}
