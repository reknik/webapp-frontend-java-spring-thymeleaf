package com.reknik.webAppDemoFrontEnd.controller;

import com.reknik.webAppDemoFrontEnd.entity.dto.AddressDTO;
import com.reknik.webAppDemoFrontEnd.entity.dto.EmployeeDTO;
import com.reknik.webAppDemoFrontEnd.entity.dto.JobDTO;
import com.reknik.webAppDemoFrontEnd.entity.request.AddressAddRequest;
import com.reknik.webAppDemoFrontEnd.entity.request.JobAddRequest;
import com.reknik.webAppDemoFrontEnd.service.AddressService;
import com.reknik.webAppDemoFrontEnd.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class AddressController {

    private final AddressService addressService;

    private final EmployeeService employeeService;

    public AddressController(final AddressService addressService, final EmployeeService employeeService) {
        this.addressService = addressService;
        this.employeeService = employeeService;
    }

    @GetMapping("/showAddresses/{employeeId}")
    public String showAddressesForEmployee(@PathVariable("employeeId") long employeeId, Model theModel) {
        Mono<EmployeeDTO> employee = employeeService.findById(employeeId);
        Flux<AddressDTO> addresses = addressService.findAddressesForEmployeeId(employeeId);
        theModel.addAttribute("employee", employee);
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
