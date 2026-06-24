package com.gym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gym.entity.Rol;
import com.gym.service.RolService;

@Controller
@RequestMapping("/gestionrol")
public class RolController {

	@Autowired
	private RolService rolService;

	@GetMapping("/lista")
	public String listar(Model model) {
		model.addAttribute("roles", rolService.listar());
		model.addAttribute("rol", new Rol());
		return "rol/mantRoles";
	}

	@PostMapping("/registrar")
	public String registrar(@ModelAttribute("rol") Rol rol, RedirectAttributes flash) {
		try {
			rolService.registrar(rol);
			flash.addFlashAttribute("msg", "Rol registrado correctamente.");
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo registrar: el nombre del rol ya existe.");
		}
		return "redirect:/gestionrol/lista";
	}

	@PostMapping("/actualizar")
	public String actualizar(@ModelAttribute("rol") Rol rol, RedirectAttributes flash) {
		try {
			rolService.actualizar(rol);
			flash.addFlashAttribute("msg", "Rol actualizado correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo actualizar: el nombre del rol ya existe.");
		}
		return "redirect:/gestionrol/lista";
	}

	@GetMapping("/buscar/{id}")
	@ResponseBody
	public Rol buscarPorId(@PathVariable("id") Integer id) {
		return rolService.buscarPorId(id);
	}

	@GetMapping("/desactivar/{id}")
	public String desactivar(@PathVariable("id") Integer id, RedirectAttributes flash) {
		rolService.desactivar(id);
		flash.addFlashAttribute("msg", "Rol desactivado.");
		return "redirect:/gestionrol/lista";
	}
}
