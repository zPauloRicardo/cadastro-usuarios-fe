package me.paulojr.cadastrosfront.models;

import java.util.List;

public record ErrorResponse(

        String message,

        int status,

        List<ErrorResponse> errors
) {


    public String makeErrorString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(message).append("\n");


        for (ErrorResponse error : errors) {
            stringBuilder.append(error.message).append("\n");
        }

        return stringBuilder.toString();
    }
}

