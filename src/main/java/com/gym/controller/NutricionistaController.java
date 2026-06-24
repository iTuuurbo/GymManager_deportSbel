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

import com.gym.entity.Nutricionista;
import com.gym.entity.Usuario;
import com.gym.service.NutricionistaService;

@Controller
@RequestMapping("/gestionnutricionista")
public class NutricionistaController {

	@Autowired
	private NutricionistaService nutricionistaService;

	@GetMapping("/lista")
	public String listar(Model model) {
		model.addAttribute("nutricionistas", nutricionistaService.listar());
		Nutricionista nutricionista = new Nutricionista();
		nutricionista.setUsuario(new Usuario());
		nutricionista.setEstado((byte) 1);
		model.addAttribute("nutricionista", nutricionista);
		model.addAttribute("usuarios", nutricionistaService.listarUsuarios());
		return "nutricionista/mantNutricionistas";
	}

	@PostMapping("/registrar")
	public String registrar(@ModelAttribute("nutricionista") Nutricionista nutricionista, RedirectAttributes flash) {
		try {
			nutricionistaService.registrar(nutricionista);
			flash.addFlashAttribute("msg", "Nutricionista registrado correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo registrar: el DNI ya existe o el usuario ya esta asignado.");
		}
		return "redirect:/gestionnutricionista/lista";
	}

	@PostMapping("/actualizar")
	public String actualizar(@ModelAttribute("nutricionista") Nutricionista nutricionista, RedirectAttributes flash) {
		try {
			nutricionistaService.actualizar(nutricionista);
			flash.addFlashAttribute("msg", "Nutricionista actualizado correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo actualizar: el DNI ya existe o el usuario ya esta asignado.");
		}
		return "redirect:/gestionnutricionista/lista";
	}

	@GetMapping("/buscar/{id}")
	@ResponseBody
	public Nutricionista buscarPorId(@PathVariable("id") Integer id) {
		return nutricionistaService.buscarPorId(id);
	}

	@GetMapping("/desactivar/{id}")
	public String desactivar(@PathVariable("id") Integer id, RedirectAttributes flash) {
		nutricionistaService.desactivar(id);
		flash.addFlashAttribute("msg", "Nutricionista desactivado.");
		return "redirect:/gestionnutricionista/lista";
	}
}
