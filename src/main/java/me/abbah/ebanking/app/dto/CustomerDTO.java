package me.abbah.ebanking.app.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(fluent = true)
public class CustomerDTO {
    private UUID id;
    private String name;
    private String email;
}
