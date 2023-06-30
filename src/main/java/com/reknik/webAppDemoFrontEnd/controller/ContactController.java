package com.reknik.webAppDemoFrontEnd.controller;

import com.reknik.webAppDemoFrontEnd.entity.dto.ContactDTO;
import com.reknik.webAppDemoFrontEnd.entity.dto.EmployeeDTO;
import com.reknik.webAppDemoFrontEnd.entity.request.ContactAddRequest;
import com.reknik.webAppDemoFrontEnd.service.ContactService;
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
public class ContactController {

    private final ContactService contactService;

    private final EmployeeService employeeService;
    private final RoleService roleService;

    public ContactController(final ContactService contactService, final EmployeeService employeeService, RoleService roleService) {
        this.contactService = contactService;
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    @GetMapping("/showContacts/{employeeId}")
    public String showContactsForEmployee(@PathVariable("employeeId") long employeeId, Model theModel) {
        Mono<EmployeeDTO> employee = employeeService.findById(employeeId);
        Flux<ContactDTO> contacts = contactService.findContactsForEmployeeId(employeeId);
        Mono<List<String>> userRoles = roleService.getUserRoles();
        theModel.addAttribute("employee", employee);
        theModel.addAttribute("userRoles", userRoles);
        theModel.addAttribute("contacts", contacts);
        return "list-contacts";
    }

    @GetMapping("/showContactsAdd")
    public String showContactsAdd(@RequestParam("employeeId") long employeeId, Model theModel) {
        ContactAddRequest contactAddRequest = new ContactAddRequest();
        contactAddRequest.setEmployeeId(employeeId);
        theModel.addAttribute("contact", contactAddRequest);
        return "add-contact";
    }

    @PostMapping("/contactSave")
    public String contactAdd(Model theModel, @ModelAttribute("contact") ContactAddRequest contact) {
        Mono<HttpStatus> status = contactService.save(contact);
        theModel.addAttribute("succeeded", status);
        return "redirect:/showContacts/" + contact.getEmployeeId();
    }

    @GetMapping("/contactDelete")
    public String contactAdd(Model theModel, @RequestParam("contactId") long contactId,
                             @RequestParam("employeeId") long employeeId) {
        Mono<HttpStatus> status = contactService.delete(contactId);
        theModel.addAttribute("succeeded", status);
        return "redirect:/showContacts/" + employeeId;
    }

}
