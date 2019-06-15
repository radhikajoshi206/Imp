package com.x.Controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.x.Model.Employee;
import com.x.Utility.EmployeeNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

@CrossOrigin(origins="*")
@RestController
public class HomeController {
	
	@Autowired
	RestTemplate rt;
	
	
	
	/*
	@RequestMapping(value="saveEmployee", method=RequestMethod.POST,consumes="application/json")
	@ResponseBody
	public Employee savedata(@RequestBody Employee e) {
		
		String url = "http://localhost:8086/save";
		Employee em = rt.postForObject(url, e, Employee.class);
		return em;		
	}
	*/
	
	//-----------consume whatever the data------------------------------------------
	@RequestMapping(value="saveEmployee", method=RequestMethod.POST,consumes= {"application/json","application/xml"})
	public Employee savedata(@RequestBody Employee e) {
		
		String url = "http://localhost:8086/save";
		Employee em = rt.postForObject(url, e, Employee.class);
		return em;		
	}
	
	
	@RequestMapping(value="Employee", method=RequestMethod.PUT)
	public void update(@RequestBody Employee e) {
		String url = "http://localhost:8086/updateData";
		rt.put(url, e, Employee.class);
		
	}
	
	
	/*@RequestMapping(value="deleteEmployee", method=RequestMethod.DELETE, produces="application/json")
	public List delete(@RequestParam  int empid) {
		
		String url = "http://localhost:8086/delete?empid="+empid;
		
		List<Employee> el = rt.getForObject(url, List.class);
		System.out.println(el);
		return el;
		
		
	}*/
	
	@RequestMapping(value="deleteEmployee/{empid}", method=RequestMethod.DELETE, produces="application/json")
	public List delete(@PathVariable (name="empid") int empid) throws EmployeeNotFoundException {
		
		
			String url = "http://localhost:8086/delete/"+empid+"";
			List<Employee> el = rt.getForObject(url, List.class);
			System.out.println(el);
			return el;
	}
	
	
	@RequestMapping(value="/findId/{empid}", method=RequestMethod.GET, produces="application/xml")
		public Employee getEmployee(@PathVariable (name="empid") int empid,HttpServletResponse res) throws EmployeeNotFoundException
		
	{
		
			String url = "http://localhost:8086/findById/"+empid+"";
			Employee e = rt.getForObject(url, Employee.class);
			if(e == null) {
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				throw new EmployeeNotFoundException("employye nai sapdla re bhoooooo");
			}
			System.out.println(e);
			return e;	
		}
	
//---------------------------------------generate the data in xml format very imp------------------------------------	
	@RequestMapping(value="/find", method=RequestMethod.GET, produces="application/xml")
	public List getAllEmployee(HttpServletResponse response)
	{
		String url = "http://localhost:8086/findall";
		List<Employee> el = rt.getForObject(url, List.class);
		if(el!=null) {
			response.setStatus(HttpServletResponse.SC_FOUND);
			return el;
		}
		else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return null; 
	}
	
//-------------------------------------------generate data in whatever format--------------------------	
	/*@RequestMapping(value="/find", method=RequestMethod.GET, produces= {"application/xml","application/json"})
	public List getAllEmployee(HttpServletResponse response)
	{
		String url = "http://localhost:8086/findall";
		List<Employee> el = rt.getForObject(url, List.class);
		if(el!=null) {
			response.setStatus(HttpServletResponse.SC_FOUND);
			return el;
		}
		else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		return null; 
	}*/
	
	
	
	
}
