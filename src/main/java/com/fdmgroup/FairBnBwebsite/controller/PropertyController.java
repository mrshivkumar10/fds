package com.fdmgroup.FairBnBwebsite.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fdmgroup.FairBnBwebsite.model.Host;
import com.fdmgroup.FairBnBwebsite.model.Location;
import com.fdmgroup.FairBnBwebsite.model.Property;
import com.fdmgroup.FairBnBwebsite.model.PropertyType;
import com.fdmgroup.FairBnBwebsite.repository.HostRepository;
import com.fdmgroup.FairBnBwebsite.service.HostService;
import com.fdmgroup.FairBnBwebsite.service.LocationService;
import com.fdmgroup.FairBnBwebsite.service.PropertyService;
import com.fdmgroup.FairBnBwebsite.service.PropertyTypeService;

@Controller
public class PropertyController {
	
	private final PropertyService propertyService;
	private final HostService hostService;
	private final LocationService locationService;
	private final PropertyTypeService propertyTypeService;
	
	@Autowired
	public PropertyController(PropertyService propertyService, HostService hostService,
			LocationService locationService, PropertyTypeService propertyTypeService) {
		this.propertyService = propertyService;	
		this.hostService = hostService;
		this.locationService = locationService;
		this.propertyTypeService = propertyTypeService;
	}
	
	@GetMapping("/propertyindex")
	public String propertyIndex(Model model) {
		model.addAttribute("propertyAttr", propertyService.getAllPropertys());
		return "index-property";
	}

	@GetMapping("/propertysignup")
	public String propertySignupForm(Model model) {
		Iterable<PropertyType> propertyTypes = propertyTypeService.getAllPropertyTypes();
		model.addAttribute("propertyTypes", propertyTypes);
		Iterable<Location> locations = locationService.getAllLocations();
		model.addAttribute("locations", locations);
		Iterable<Host> hosts = hostService.getAllHosts();
		model.addAttribute("hosts", hosts);		
		model.addAttribute("propertyAttr", propertyService.createProperty());
		return "add-property";
	}

	@PostMapping("/addproperty")
	public String addProperty(@Valid Property propertyAttr, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-property";
		}

		propertyService.saveProperty(propertyAttr);
		model.addAttribute("propertyAttr", propertyService.getAllPropertys());
		return "index-property";
	}

	@GetMapping("/editproperty/{id}")
	public String propertyUpdateForm(@PathVariable("id") int propertyId, Model model) {
		Property property = propertyService.getPropertyById(propertyId);
		model.addAttribute("propertyAttr", property);
		return "update-property";
	}

	@PostMapping("/updateproperty/{id}")
	public String updateProperty(@PathVariable("id") int propertyId, @Valid Property property,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "update-property";
		}

		property.setPropertyId(propertyId);
		propertyService.editProperty(property);
		model.addAttribute("propertyAttr", propertyService.getAllPropertys());
		return "index-property";
	}

	@GetMapping("/deleteproperty/{id}")
	public String deleteProperty(@PathVariable("id") int propertyId, Model model) {
		propertyService.deletePropertyById(propertyId);
		model.addAttribute("propertyAttr", propertyService.getAllPropertys());
		return "index-property";
	}
	
	

}
