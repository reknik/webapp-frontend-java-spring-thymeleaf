package com.reknik.webAppDemoFrontEnd.controller;

import com.reknik.webAppDemoFrontEnd.entity.dto.ContactDTO;
import com.reknik.webAppDemoFrontEnd.entity.dto.EmployeeDTO;
import com.reknik.webAppDemoFrontEnd.entity.request.ContactAddRequest;
import com.reknik.webAppDemoFrontEnd.entity.request.JobAddRequest;
import com.reknik.webAppDemoFrontEnd.service.ContactService;
import com.reknik.webAppDemoFrontEnd.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ContactController {

    private final ContactService contactService;

    private final EmployeeService employeeService;

    public ContactController(final ContactService contactService, final EmployeeService employeeService) {
        this.contactService = contactService;
        this.employeeService = employeeService;
    }

    @GetMapping("/showContacts/{employeeId}")
    public String showContactsForEmployee(@PathVariable("employeeId") long employeeId, Model theModel) {
        Mono<EmployeeDTO> employee = employeeService.findById(employeeId);
        Flux<ContactDTO> contacts = contactService.findContactsForEmployeeId(employeeId);
        theModel.addAttribute("employee", employee);
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
