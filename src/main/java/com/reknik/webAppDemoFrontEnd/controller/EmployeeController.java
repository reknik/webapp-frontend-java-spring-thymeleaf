package com.reknik.webAppDemoFrontEnd.controller;

import com.reknik.webAppDemoFrontEnd.entity.dto.CompanyDTO;
import com.reknik.webAppDemoFrontEnd.entity.dto.EmployeeDTO;
import com.reknik.webAppDemoFrontEnd.entity.request.EmployeeAddRequest;
import com.reknik.webAppDemoFrontEnd.service.CompanyService;
import com.reknik.webAppDemoFrontEnd.service.EmployeeService;
import com.reknik.webAppDemoFrontEnd.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Controller
public class EmployeeController {


    private final EmployeeService employeeService;

    private final CompanyService companyService;

    private final RoleService roleService;

    private final String redirectToMain;

    public EmployeeController(final EmployeeService employeeService, final CompanyService companyService, RoleService roleService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
        this.roleService = roleService;
        redirectToMain = "redirect:/showEmployees";
    }

    @GetMapping("/")
    public String defaultSuccess() {
        return redirectToMain;
    }

    @GetMapping("/showEmployees")
    public String showEmployees(Model theModel) {
        Flux<CompanyDTO> companyFlux = companyService.findAll();
        Flux<EmployeeDTO> employeeFlux = employeeService.findAll();
        Mono<List<String>> userRoles = roleService.getUserRoles();
        theModel.addAttribute("employees", employeeFlux);
        theModel.addAttribute("userRoles", userRoles);
        theModel.addAttribute("companyMap", companyFlux.collectMap(CompanyDTO::getId, Function.identity()));
        return "list-employees";
    }

    @GetMapping("/showEmployeesForCompany")
    public String showEmployeesForCompany(@RequestParam("companyId") long companyId, Model theModel) {
        Flux<EmployeeDTO> employeeFlux = employeeService.findAll();
        Mono<List<String>> userRoles = roleService.getUserRoles();
        Flux<CompanyDTO> companyFlux = companyService.findAll();
        employeeFlux = employeeFlux.filter(
                employeeDTO -> employeeDTO.getCompanies() != null && employeeDTO.getCompanies().contains(companyId));
        theModel.addAttribute("employees", employeeFlux);
        theModel.addAttribute("userRoles", userRoles);
        theModel.addAttribute("companyMap", companyFlux.collectMap(CompanyDTO::getId, Function.identity()));
        return "list-employees";
    }

    @GetMapping("/showEmployeeAdd")
    public String showEmployeeAdd(Model theModel) {
        Flux<CompanyDTO> companyFlux = companyService.findAll();
        theModel.addAttribute("companies", companyFlux);
        EmployeeAddRequest employee = new EmployeeAddRequest();
        theModel.addAttribute("employee", employee);
        return "add-employee";
    }

    @PostMapping("/employeeSave")
    public String employeeAdd(Model theModel, @ModelAttribute("employee") EmployeeAddRequest employee) {
        Mono<HttpStatus> status = employeeService.save(employee);
        theModel.addAttribute("succeeded", status);
        return redirectToMain;
    }

    @GetMapping("/showEmployeeUpdate")
    public Mono<String> showEmployeeUpdate(@RequestParam("employeeId") int id, Model theModel) {
        Flux<CompanyDTO> companyFlux = companyService.findAll();
        theModel.addAttribute("companies", companyFlux);
        return employeeService.findById(id)
                .defaultIfEmpty(new EmployeeDTO())
                .map(employee -> {
                    if (employee.getId() == 0) {
                        theModel.addAttribute("updateFailed", true);
                        return redirectToMain;
                    }
                    if (employee.getAddresses() == null || employee.getAddresses().isEmpty()) {
                        employee.setAddresses(new ArrayList<>(List.of(0L)));
                    }
                    if (employee.getContacts() == null || employee.getContacts().isEmpty()) {
                        employee.setContacts(new ArrayList<>(List.of(0L)));
                    }
                    if (employee.getCompanies() == null || employee.getCompanies().isEmpty()) {
                        employee.setCompanies(new ArrayList<>(List.of(0L)));
                    }
                    if (employee.getJobs() == null || employee.getJobs().isEmpty()) {
                        employee.setJobs(new ArrayList<>(List.of(0L)));
                    }
                    EmployeeAddRequest employeeAddRequest = new EmployeeAddRequest();
                    employeeAddRequest.setId(employee.getId());
                    employeeAddRequest.setFirstName(employee.getFirstName());
                    employeeAddRequest.setLastName(employee.getLastName());
                    employeeAddRequest.setCompanyId(
                            employee.getCompanies() != null && !employee.getCompanies().isEmpty() ?
                                    employee.getCompanies().get(0) : null);
                    employeeAddRequest.setDrivingLicense(employee.isDrivingLicense());
                    theModel.addAttribute("employee", employeeAddRequest);
                    return "update-employee";
                });
    }

    @PostMapping("/employeeUpdate")
    public String employeeUpdate(@ModelAttribute("employee") EmployeeAddRequest employee, Model theModel) {
        Mono<HttpStatus> status = employeeService.update(employee);
        theModel.addAttribute("succeeded", status);
        return redirectToMain;
    }

    @GetMapping("/employeeDelete")
    public String employeeDelete(@RequestParam("employeeId") int id, Model theModel) {
        Mono<HttpStatus> status = employeeService.deleteById(id);
        theModel.addAttribute("succeeded", status);
        return redirectToMain;
    }
}
