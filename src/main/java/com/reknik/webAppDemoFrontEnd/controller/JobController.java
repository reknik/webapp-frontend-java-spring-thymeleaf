package com.reknik.webAppDemoFrontEnd.controller;

import com.reknik.webAppDemoFrontEnd.entity.dto.EmployeeDTO;
import com.reknik.webAppDemoFrontEnd.entity.dto.JobDTO;
import com.reknik.webAppDemoFrontEnd.entity.request.JobAddRequest;
import com.reknik.webAppDemoFrontEnd.service.EmployeeService;
import com.reknik.webAppDemoFrontEnd.service.JobService;
import com.reknik.webAppDemoFrontEnd.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class JobController {

    private final JobService jobService;

    private final EmployeeService employeeService;
    private final RoleService roleService;

    public JobController(final JobService jobService, final EmployeeService employeeService, RoleService roleService) {
        this.jobService = jobService;
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    @GetMapping("/showJobs/{employeeId}")
    public String showJobsForEmployee(@PathVariable("employeeId") long employeeId, Model theModel) {
        Mono<EmployeeDTO> employee = employeeService.findById(employeeId);
        Flux<JobDTO> jobs = jobService.findJobsForEmployeeId(employeeId);
        Mono<List<String>> userRoles = roleService.getUserRoles();
        theModel.addAttribute("employee", employee);
        theModel.addAttribute("userRoles", userRoles);
        theModel.addAttribute("jobs", jobs);
        return "list-jobs";
    }

    @GetMapping("/showJobsAdd")
    public String showJobsAdd(@RequestParam("employeeId") long employeeId, Model theModel) {
        JobAddRequest jobAddRequest = new JobAddRequest();
        jobAddRequest.setEmployeeId(employeeId);
        theModel.addAttribute("job", jobAddRequest);
        return "add-job";
    }

    @PostMapping("/jobSave")
    public String jobAdd(Model theModel, @ModelAttribute("job") JobAddRequest job) {
        Mono<HttpStatus> status = jobService.save(job);
        theModel.addAttribute("succeeded", status);
        return "redirect:/showJobs/" + job.getEmployeeId();
    }

    @GetMapping("/jobDelete")
    public String jobAdd(Model theModel, @RequestParam("jobId") long jobId,
                         @RequestParam("employeeId") long employeeId) {
        Mono<HttpStatus> status = jobService.delete(jobId);
        theModel.addAttribute("succeeded", status);
        return "redirect:/showJobs/" + employeeId;
    }

}
