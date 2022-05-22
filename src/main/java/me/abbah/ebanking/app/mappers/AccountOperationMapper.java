package me.abbah.ebanking.app.mappers;

import me.abbah.ebanking.app.dto.AccountHistoryDTO;
import me.abbah.ebanking.app.dto.AccountOperationDTO;
import me.abbah.ebanking.app.entities.AccountOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AccountOperationMapper {
    public AccountOperationDTO toDto(AccountOperation entity) {
        if (entity == null) {
            return null;
        }

        AccountOperationDTO dto = new AccountOperationDTO();
        BeanUtils.copyProperties(entity, dto);

        return dto;
    }

    public AccountOperation toEntity(AccountOperationDTO dto) {
        if (dto == null) {
            return null;
        }

        AccountOperation entity = new AccountOperation();
        BeanUtils.copyProperties(dto, entity);

        return entity;
    }
}
