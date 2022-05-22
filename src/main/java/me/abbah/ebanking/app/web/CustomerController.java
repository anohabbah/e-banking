package me.abbah.ebanking.app.web;

import lombok.RequiredArgsConstructor;
import me.abbah.ebanking.app.dto.CustomerDTO;
import me.abbah.ebanking.app.exceptions.CustomerNotFoundException;
import me.abbah.ebanking.app.services.BankingService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/customers")
@RestController
public class CustomerController {
    private final BankingService bankingService;

    @GetMapping
    public List<CustomerDTO> getAll() {
        return bankingService.getAllCustomers();
    }

    @GetMapping("/{customer}")
    public CustomerDTO get(@PathVariable("customer") UUID customerId) throws CustomerNotFoundException {
        return bankingService.getCustomer(customerId);
    }

    @PostMapping
    public CustomerDTO create(@RequestBody CustomerDTO dto) {
        return bankingService.createCustomer(dto);
    }

    @PutMapping("/{customer}")
    public CustomerDTO update(@PathVariable("customer") UUID customerId, @RequestBody CustomerDTO dto) {
        return bankingService.updateCustomer(dto.id(customerId));
    }

    @DeleteMapping("/{customer}")
    public void delete(@PathVariable("customer") UUID customerId) {
        bankingService.deleteCustomer(customerId);
    }
}
