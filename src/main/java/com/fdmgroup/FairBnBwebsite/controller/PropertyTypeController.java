package com.fdmgroup.FairBnBwebsite.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fdmgroup.FairBnBwebsite.model.PropertyType;
import com.fdmgroup.FairBnBwebsite.service.PropertyTypeService;

@Controller
public class PropertyTypeController {

	private final PropertyTypeService propertyTypeService;

	@Autowired
	public PropertyTypeController(PropertyTypeService propertyTypeService) {
		this.propertyTypeService = propertyTypeService;
	}

	@GetMapping("/propertytypeindex")
	public String propertyTypeIndex(Model model) {
		model.addAttribute("propertyTypeAttr", propertyTypeService.getAllPropertyTypes());
		return "index-propertytypes";
	}

	@GetMapping("/propertytypesignup")
	public String propertyTypeSignupForm(Model model) {
		model.addAttribute("propertyTypeAttr", propertyTypeService.createPropertyType());
		return "add-propertytype";
	}

	@PostMapping("/addpropertytype")
	public String addPropertyType(@Valid PropertyType propertyTypeAttr, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-propertytype";
		}

		propertyTypeService.savePropertyType(propertyTypeAttr);
		model.addAttribute("propertyTypeAttr", propertyTypeService.getAllPropertyTypes());
		return "index-propertytypes";
	}

	@GetMapping("/editpropertytype/{id}")
	public String propertyTypeUpdateForm(@PathVariable("id") Integer propertyTypeId, Model model) {
		PropertyType propertyType = propertyTypeService.getPropertyTypeById(propertyTypeId);
		model.addAttribute("propertyTypeAttr", propertyType);
		return "update-propertytype";

	}

	@PostMapping("/updatepropertytype/{id}")
	public String updatePropertyType(@PathVariable("id") Integer propertyTypeId, @Valid PropertyType propertyType,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "update-propertytype";
		}
		propertyType.setPropertyTypeId(propertyTypeId);
		propertyTypeService.editPropertyType(propertyType);
		model.addAttribute("propertyTypeAttr", propertyTypeService.getAllPropertyTypes());
		return "index-propertytypes";
	}

	@GetMapping("/deletepropertytype/{id}")
	public String deletePropertyType(@PathVariable("id") Integer propertyTypeId, Model model) {
		propertyTypeService.deletePropertyTypeById(propertyTypeId);
		model.addAttribute("propertyTypeAttr", propertyTypeService.getAllPropertyTypes());
		return "index-propertytypes";
	}

}