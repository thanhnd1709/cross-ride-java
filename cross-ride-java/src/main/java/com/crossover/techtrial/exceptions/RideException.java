package com.crossover.techtrial.exceptions;

import lombok.Getter;

@Getter
public class RideException extends RuntimeException {

    private static final long serialVersionUID = -3926816220257601136L;

    public RideException() {
        super();
    }

    public RideException(String message) {
        super(message);
    }

}
