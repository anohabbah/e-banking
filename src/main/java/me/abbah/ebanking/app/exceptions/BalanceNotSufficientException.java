package me.abbah.ebanking.app.exceptions;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@StandardException
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Balance not sufficient.")
public class BalanceNotSufficientException extends Exception {
}
