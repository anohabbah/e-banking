package me.abbah.ebanking.app.exceptions;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Customer not found.")
@StandardException
public class CustomerNotFoundException extends Exception {}
