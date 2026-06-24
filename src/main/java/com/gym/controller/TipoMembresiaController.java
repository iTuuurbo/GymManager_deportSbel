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

import com.gym.entity.TipoMembresia;
import com.gym.service.TipoMembresiaService;

@Controller
@RequestMapping("/gestiontipomembresia")
public class TipoMembresiaController {

	@Autowired
	private TipoMembresiaService tipoMembresiaService;

	@GetMapping("/lista")
	public String listar(Model model) {
		model.addAttribute("tiposMembresia", tipoMembresiaService.listar());
		model.addAttribute("tipoMembresia", new TipoMembresia());
		return "tipomembresia/mantTiposMembresia";
	}

	@PostMapping("/registrar")
	public String registrar(@ModelAttribute("tipoMembresia") TipoMembresia tipoMembresia, RedirectAttributes flash) {
		try {
			tipoMembresiaService.registrar(tipoMembresia);
			flash.addFlashAttribute("msg", "Tipo de membresia registrado correctamente.");
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo registrar: el nombre ya existe.");
		}
		return "redirect:/gestiontipomembresia/lista";
	}

	@PostMapping("/actualizar")
	public String actualizar(@ModelAttribute("tipoMembresia") TipoMembresia tipoMembresia, RedirectAttributes flash) {
		try {
			tipoMembresiaService.actualizar(tipoMembresia);
			flash.addFlashAttribute("msg", "Tipo de membresia actualizado correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo actualizar: el nombre ya existe.");
		}
		return "redirect:/gestiontipomembresia/lista";
	}

	@GetMapping("/buscar/{id}")
	@ResponseBody
	public TipoMembresia buscarPorId(@PathVariable("id") Integer id) {
		return tipoMembresiaService.buscarPorId(id);
	}

	@GetMapping("/desactivar/{id}")
	public String desactivar(@PathVariable("id") Integer id, RedirectAttributes flash) {
		tipoMembresiaService.desactivar(id);
		flash.addFlashAttribute("msg", "Tipo de membresia desactivado.");
		return "redirect:/gestiontipomembresia/lista";
	}
}
