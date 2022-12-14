package com.eaiesb;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.eaiesb.employee.Employee;
import com.eaiesb.employee.EmployeeController;
import com.eaiesb.employee.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private EmployeeService service;
	
	private List<Employee> employees;
	
	private Optional<Employee> empById;
	
	private Employee emp;
	
	private Employee empupdate;
	
	@BeforeEach
	void setUp() {
		employees = List.of(new Employee("2013456","E1","Ravi","Kumar",2000),new Employee("52631456","E1","Ravi","Kumar",2000));
		emp = new Employee("123456","E1","Ravi","Kumar",2000);
		empById = Optional.of(new Employee("123456","E3","Ravi","Kumar",2000));
	}
	
	@Test
    //@Disabled
    void createEmployeeTest() throws Exception {

        Mockito.when(service.create(Mockito.any(Employee.class))).thenReturn(emp);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(emp).getBytes(StandardCharsets.UTF_8))
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String empJson = result.getResponse().getContentAsString();
        Assertions.assertThat(empJson).isNotEmpty();
        Assertions.assertThat(empJson).isEqualToIgnoringCase(mapper.writeValueAsString(emp));
    }
	
	@Test
    //@Disabled
    void getEmployeesTest() throws Exception {

        Mockito.when(service.getAll()).thenReturn(employees);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String empJson = result.getResponse().getContentAsString();
        Assertions.assertThat(empJson).isEqualToIgnoringCase(mapper.writeValueAsString(employees));
    }
	
	@Test
    //@Disabled
    void getEmployeeByIdTest() throws Exception {
		
		String id = "123456";
        Mockito.when(service.getById(id)).thenReturn(empById);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employee/123456")
        		.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String empJson = result.getResponse().getContentAsString();
        Assertions.assertThat(empJson).isEqualToIgnoringCase(mapper.writeValueAsString(empById));
    }
	
	@Test
    //@Disabled
    void updateEmployeeByIdTest() throws Exception {
		
		String id = "123456";
        Mockito.when(service.update(id,emp)).thenReturn(emp);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/employee/123456")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(emp).getBytes(StandardCharsets.UTF_8))
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String empJson = result.getResponse().getContentAsString();
        Assertions.assertThat(empJson).isEqualToIgnoringCase(mapper.writeValueAsString(emp));
    }
	
	@Test
	//@Disabled
	void deleteEmployee() throws Exception {
		String id = "123456";
		
		Mockito.when(service.getById(id)).thenReturn(null);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/employee/123456"))
							.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		
		Assertions.assertThat(result).isNotNull();
	}
	
}
