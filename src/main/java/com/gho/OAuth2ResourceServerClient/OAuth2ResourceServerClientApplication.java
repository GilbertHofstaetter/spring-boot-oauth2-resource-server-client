package com.gho.OAuth2ResourceServerClient;

import com.gho.OAuth2ResourceServerClient.repository.CompanyRepository;
import com.gho.OAuth2ResourceServerClient.obj.Company;
import com.gho.OAuth2ResourceServerClient.obj.Employee;
import com.gho.OAuth2ResourceServerClient.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashSet;

//https://www.djamware.com/post/6225b66ba88c55c95abca0b6/spring-boot-security-postgresql-and-keycloak-rest-api-oauth2
//https://stackoverflow.com/questions/72719400/how-to-configure-oauth2-in-spring-boot-be-spring-boot-fe-keycloak
@SpringBootApplication
public class OAuth2ResourceServerClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2ResourceServerClientApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public CommandLineRunner loadData(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
		return (args) -> {
			Employee employee0 = new Employee();
			employee0.setFirstName("Max0");
			employee0.setLastName("Mustermann0");
			employee0.setBirthDate(new Date());
			employee0.setEmail("m0.m0@company.com");
			employee0 = employeeRepository.save(employee0);

			Employee employee1 = new Employee();
			employee1.setFirstName("Max1");
			employee1.setLastName("Mustermann1");
			employee1.setBirthDate(new Date());
			employee1.setEmail("m1.m1@company.com");
			employee1 = employeeRepository.save(employee1);

			Employee employee2 = new Employee();
			employee2.setFirstName("Max2");
			employee2.setLastName("Mustermann2");
			employee2.setBirthDate(new Date());
			employee2.setEmail("m2.m2@company.com");
			employee2 = employeeRepository.save(employee2);

			Company company = new Company();
			company.setName("Company");
			company = companyRepository.save(company);
			company.setEmployees(new HashSet<Employee>());
			company.getEmployees().add(employee0);
			company.getEmployees().add(employee1);
			company.getEmployees().add(employee2);
			company = companyRepository.save(company);

			employee0.setCompany(company);
			employeeRepository.save(employee0);

			employee1.setCompany(company);
			employeeRepository.save(employee1);

			employee2.setCompany(company);
			employeeRepository.save(employee2);

		};
	}

}
