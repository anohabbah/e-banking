package me.abbah.ebanking.app.mappers;

import lombok.RequiredArgsConstructor;
import me.abbah.ebanking.app.dto.CurrentAccountDTO;
import me.abbah.ebanking.app.dto.SavingAccountDTO;
import me.abbah.ebanking.app.entities.CurrentAccount;
import me.abbah.ebanking.app.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountMapper {
    private final CustomerMapper customerMapper;

    public SavingAccountDTO toSavingDto(SavingAccount entity) {
        if (entity == null) {
            return null;
        }

        SavingAccountDTO dto = new SavingAccountDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.customer(customerMapper.toDto(entity.customer()));
        dto.type(entity.getClass().getSimpleName());

        return dto;
    }

    public SavingAccount toSavingEntity(SavingAccountDTO dto) {
        if (dto == null) {
            return null;
        }

        SavingAccount entity = new SavingAccount();
        BeanUtils.copyProperties(dto, entity);

        entity.customer(customerMapper.toEntity(dto.customer()));

        return entity;
    }

    public CurrentAccountDTO toCurrentDto(CurrentAccount entity) {
        if (entity == null) {
            return null;
        }

        CurrentAccountDTO dto = new CurrentAccountDTO();
        BeanUtils.copyProperties(entity, dto);

        dto.customer(customerMapper.toDto(entity.customer()));
        dto.type(entity.getClass().getSimpleName());

        return dto;
    }

    public CurrentAccount toCurrentEntity(CurrentAccountDTO dto) {
        if (dto == null) {
            return null;
        }

        CurrentAccount entity = new CurrentAccount();
        BeanUtils.copyProperties(dto, entity);

        entity.customer(customerMapper.toEntity(dto.customer()));

        return entity;
    }
}
