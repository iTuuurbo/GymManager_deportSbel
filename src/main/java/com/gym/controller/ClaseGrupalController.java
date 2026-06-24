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

import com.gym.entity.ClaseGrupal;
import com.gym.entity.EstadoGrupal;
import com.gym.entity.Instructor;
import com.gym.service.ClaseGrupalService;

@Controller
@RequestMapping("/gestionclase")
public class ClaseGrupalController {

	@Autowired
	private ClaseGrupalService claseGrupalService;

	@GetMapping("/lista")
	public String listar(Model model) {
		model.addAttribute("clases", claseGrupalService.listar());
		ClaseGrupal clase = new ClaseGrupal();
		clase.setInstructor(new Instructor());
		clase.setEstadoGrupal(EstadoGrupal.PROGRAMADA);
		model.addAttribute("clase", clase);
		model.addAttribute("instructores", claseGrupalService.listarInstructores());
		model.addAttribute("estados", EstadoGrupal.values());
		return "clase/mantClases";
	}

	@PostMapping("/registrar")
	public String registrar(@ModelAttribute("clase") ClaseGrupal clase, RedirectAttributes flash) {
		try {
			claseGrupalService.registrar(clase);
			flash.addFlashAttribute("msg", "Clase registrada correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo registrar la clase.");
		}
		return "redirect:/gestionclase/lista";
	}

	@PostMapping("/actualizar")
	public String actualizar(@ModelAttribute("clase") ClaseGrupal clase, RedirectAttributes flash) {
		try {
			claseGrupalService.actualizar(clase);
			flash.addFlashAttribute("msg", "Clase actualizada correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo actualizar la clase.");
		}
		return "redirect:/gestionclase/lista";
	}

	@GetMapping("/buscar/{id}")
	@ResponseBody
	public ClaseGrupal buscarPorId(@PathVariable("id") Integer id) {
		return claseGrupalService.buscarPorId(id);
	}

	@GetMapping("/desactivar/{id}")
	public String desactivar(@PathVariable("id") Integer id, RedirectAttributes flash) {
		claseGrupalService.desactivar(id);
		flash.addFlashAttribute("msg", "Clase cancelada.");
		return "redirect:/gestionclase/lista";
	}
}
