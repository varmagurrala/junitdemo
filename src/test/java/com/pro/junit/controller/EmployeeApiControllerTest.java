package com.pro.junit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pro.junit.model.Employee;
import com.pro.junit.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
class EmployeeApiControllerTest {


    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeApi employeeApi;

    private List<Employee> employees;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employees = new ArrayList<>();
        employees.add(new Employee(1, "John", "Doe", "john.doe@test.com"));
        employees.add(new Employee(2, "Jane", "Doe", "jane.doe@test.com"));

        employee = new Employee(3, "David", "Smith", "david.smith@test.com");
    }

    @Test
    public void testCreateEmployee() {
        when(employeeService.saveEmployee(employee)).thenReturn(employee);

        Employee createdEmployee = employeeApi.createEmployee(employee);

        assertNotNull(createdEmployee);
        assertEquals(employee.getFirstName(), createdEmployee.getFirstName());
        assertEquals(employee.getLastName(), createdEmployee.getLastName());
        assertEquals(employee.getEmail(), createdEmployee.getEmail());
    }

    @Test
    public void testGetAllEmployees() {
        when(employeeService.getAllEmployees()).thenReturn(employees);

        List<Employee> allEmployees = employeeApi.getAllEmployees();

        assertNotNull(allEmployees);
        assertEquals(2, allEmployees.size());
    }

    @Test
    public void testGetEmployeeById() {
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(employees.get(0)));

        ResponseEntity<Employee> response = employeeApi.getEmployeeById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees.get(0), response.getBody());
    }

    @Test
    public void testGetEmployeeByIdNotFound() {
        when(employeeService.getEmployeeById(10L)).thenReturn(Optional.empty());

        ResponseEntity<Employee> response = employeeApi.getEmployeeById(10L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.hasBody());
    }

    @Test
    public void testUpdateEmployee() {
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(employees.get(0)));
        when(employeeService.updateEmployee(employees.get(0))).thenReturn(employee);

        ResponseEntity<Employee> response = employeeApi.updateEmployee(1L, employee);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
    }

    @Test
    public void testUpdateEmployeeNotFound() {
        when(employeeService.getEmployeeById(10L)).thenReturn(Optional.empty());

        ResponseEntity<Employee> response = employeeApi.updateEmployee(10L, employee);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.hasBody());
    }

    @Test	
    public void testDeleteEmployee() {
        ResponseEntity<String> response = employeeApi.deleteEmployee(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee deleted successfully!.", response.getBody());
    }

}