package com.reknik.webAppDemoFrontEnd.controller;

import com.reknik.webAppDemoFrontEnd.entity.dto.AddressDTO;
import com.reknik.webAppDemoFrontEnd.entity.dto.EmployeeDTO;
import com.reknik.webAppDemoFrontEnd.entity.request.AddressAddRequest;
import com.reknik.webAppDemoFrontEnd.service.AddressService;
import com.reknik.webAppDemoFrontEnd.service.EmployeeService;
import com.reknik.webAppDemoFrontEnd.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class AddressController {

    private final AddressService addressService;

    private final EmployeeService employeeService;
    private final RoleService roleService;

    public AddressController(final AddressService addressService, final EmployeeService employeeService, RoleService roleService) {
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    @GetMapping("/showAddresses/{employeeId}")
    public String showAddressesForEmployee(@PathVariable("employeeId") long employeeId, Model theModel) {
        Mono<EmployeeDTO> employee = employeeService.findById(employeeId);
        Flux<AddressDTO> addresses = addressService.findAddressesForEmployeeId(employeeId);
        Mono<List<String>> userRoles = roleService.getUserRoles();
        theModel.addAttribute("employee", employee);
        theModel.addAttribute("userRoles", userRoles);
        theModel.addAttribute("addresses", addresses);
        return "list-addresses";
    }

    @GetMapping("/showAddressAdd")
    public String showAddressAdd(@RequestParam("employeeId") long employeeId, Model theModel) {
        AddressAddRequest addressAddRequest = new AddressAddRequest();
        addressAddRequest.setEmployeeId(employeeId);
        theModel.addAttribute("address", addressAddRequest);
        return "add-address";
    }

    @PostMapping("/addressSave")
    public String addressAdd(Model theModel, @ModelAttribute("address") AddressAddRequest address) {
        Mono<HttpStatus> status = addressService.save(address);
        theModel.addAttribute("succeeded", status);
        return "redirect:/showAddresses/" + address.getEmployeeId();
    }

    @GetMapping("/addressDelete")
    public String addressDelete(Model theModel, @RequestParam("addressId") long addressId,
                                @RequestParam("employeeId") long employeeId) {
        Mono<HttpStatus> status = addressService.delete(addressId);
        theModel.addAttribute("succeeded", status);
        return "redirect:/showAddresses/" + employeeId;
    }
}
