package me.abbah.ebanking.app.mappers;

import me.abbah.ebanking.app.dto.CustomerDTO;
import me.abbah.ebanking.app.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerDTO toDto(Customer entity) {
        if (entity == null) {
            return null;
        }

        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(entity, dto);

        return dto;
    }

    public Customer toEntity(CustomerDTO dto) {
        if (dto == null) {
            return null;
        }

        Customer entity = new Customer();
        BeanUtils.copyProperties(dto, entity);

        return entity;
    }
}
