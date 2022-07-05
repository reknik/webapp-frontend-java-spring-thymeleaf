package com.reknik.webAppDemoFrontEnd.controller;

import static com.reknik.webAppDemoFrontEnd.config.Constants.HEADER_ACCESS_TOKEN;
import static com.reknik.webAppDemoFrontEnd.config.Constants.HEADER_AUTHORIZATION;
import static com.reknik.webAppDemoFrontEnd.config.Constants.HEADER_AUTHORIZATION_REFRESH;
import static com.reknik.webAppDemoFrontEnd.config.Constants.HEADER_REFRESH_TOKEN;
import static com.reknik.webAppDemoFrontEnd.config.Constants.SESSION_LOGIN_FAILED;
import com.reknik.webAppDemoFrontEnd.entity.Address;
import com.reknik.webAppDemoFrontEnd.entity.Company;
import com.reknik.webAppDemoFrontEnd.entity.Contact;
import com.reknik.webAppDemoFrontEnd.entity.Employee;
import com.reknik.webAppDemoFrontEnd.entity.Job;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/frontend")
public class HomeController {

  @Value("${back-end.base-uri}")
  private String baseUrl;

  @Value("${back-end.employee-uri}")
  private String employeeUri;

  @Value("${back-end.user-uri}")
  private String userUri;

  private final RestTemplate restTemplate;

  @Autowired
  public HomeController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @GetMapping("/showLogin")
  public String showLogin() {
    return "main-menu";
  }

  @PostMapping("/login")
  public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
    HttpHeaders headers = new HttpHeaders();
    HttpSession session = request.getSession(true);
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("username", request.getParameter("username"));
    map.add("password", request.getParameter("password"));
    HttpEntity<?> entity = new HttpEntity<>(map, headers);
    ResponseEntity<?> responseEntity = restTemplate.exchange(baseUrl + userUri + "/login", HttpMethod.POST, entity, String.class);
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      session.setAttribute(HEADER_AUTHORIZATION, "Bearer " + responseEntity.getHeaders().get(HEADER_ACCESS_TOKEN).get(0));
      session.setAttribute(HEADER_AUTHORIZATION_REFRESH, "Bearer " + responseEntity.getHeaders().get(HEADER_REFRESH_TOKEN).get(0));
      session.setAttribute(SESSION_LOGIN_FAILED, false);
      response.sendRedirect("/frontend/showEmployees");
      return;
    }
    session.setAttribute(SESSION_LOGIN_FAILED, true);
    response.sendRedirect("/frontend/showLogin");
  }

  @GetMapping("/showEmployees")
  public String showEmployees(HttpServletRequest request, Model theModel) {
    HttpHeaders headers = new HttpHeaders();
    HttpSession session = request.getSession(false);
    headers.add(HEADER_AUTHORIZATION, (String) session.getAttribute(HEADER_AUTHORIZATION));
    HttpEntity<?> entity = new HttpEntity<>(headers);
    ResponseEntity<List<Employee>> response = restTemplate.exchange(baseUrl + employeeUri + "/getAll", HttpMethod.GET, entity,
        new ParameterizedTypeReference<>() {
        });
    List<Employee> employees = response.getBody();
    theModel.addAttribute("employees", employees);
    return "list-employees";
  }

  @GetMapping("/showEmployeeAdd")
  public String showEmployeeAdd(Model theModel) {
    Employee employee = new Employee();
    theModel.addAttribute("employee", employee);
    return "add-employee";
  }

  @PostMapping("/employeeSave")
  public void employeeAdd(HttpServletRequest request, HttpServletResponse servletResponse, @ModelAttribute("employee") Employee employee)
      throws IOException {
    HttpHeaders headers = new HttpHeaders();
    HttpSession session = request.getSession(false);
    headers.add(HEADER_AUTHORIZATION, (String) session.getAttribute(HEADER_AUTHORIZATION));
    HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);
    restTemplate.postForObject(baseUrl + employeeUri + "/save", entity, Employee.class);
    servletResponse.sendRedirect("/frontend/showEmployees");
  }

  @GetMapping("/showEmployeeUpdate")
  public String showEmployeeUpdate(HttpServletRequest request, @RequestParam("employeeId") int id,
                                   Model theModel) {
    HttpHeaders headers = new HttpHeaders();
    HttpSession session = request.getSession(false);
    headers.add(HEADER_AUTHORIZATION, (String) session.getAttribute(HEADER_AUTHORIZATION));
    HttpEntity<?> entity = new HttpEntity<>(headers);
    ResponseEntity<Employee> response =
        restTemplate.exchange(baseUrl + employeeUri + "/getById?id=" + id, HttpMethod.GET, entity, Employee.class);
    Employee employee = response.getBody();
    if (employee == null) {
      session.setAttribute("updateFailed", true);
      return "list-employees";
    }
    if (employee.getAddresses() == null || employee.getAddresses().isEmpty()) {
      employee.setAddresses(new ArrayList<>(List.of(new Address())));
    }
    if (employee.getContacts() == null || employee.getContacts().isEmpty()) {
      employee.setContacts(new ArrayList<>(List.of(new Contact())));
    }
    if (employee.getCompanies() == null || employee.getCompanies().isEmpty()) {
      employee.setCompanies(new HashSet<>(List.of(new Company())));
    }
    if (employee.getJobs() == null || employee.getJobs().isEmpty()) {
      employee.setJobs(new HashSet<>(List.of(new Job())));
    }
    theModel.addAttribute("employee", employee);
    return "update-employee";
  }

  @PostMapping("/employeeUpdate")
  public void employeeUpdate(HttpServletRequest request, HttpServletResponse servletResponse, @ModelAttribute("employee") Employee employee)
      throws IOException {
    HttpHeaders headers = new HttpHeaders();
    HttpSession session = request.getSession(false);
    headers.add(HEADER_AUTHORIZATION, (String) session.getAttribute(HEADER_AUTHORIZATION));
    HttpEntity<Employee> entity = new HttpEntity<>(employee, headers);
    restTemplate.exchange(baseUrl + employeeUri + "/update", HttpMethod.PUT, entity, Employee.class);
    servletResponse.sendRedirect("/frontend/showEmployees");
  }

  @GetMapping("/employeeDelete")
  public void employeeDelete(HttpServletRequest request, HttpServletResponse servletResponse, @RequestParam("employeeId") int id)
      throws IOException {
    HttpHeaders headers = new HttpHeaders();
    HttpSession session = request.getSession(false);
    headers.add(HEADER_AUTHORIZATION, (String) session.getAttribute(HEADER_AUTHORIZATION));
    HttpEntity<?> entity = new HttpEntity<>(headers);
    restTemplate.exchange(baseUrl + employeeUri + "/deleteById?id=" + id, HttpMethod.DELETE, entity, Employee.class);
    servletResponse.sendRedirect("/frontend/showEmployees");
  }
}
