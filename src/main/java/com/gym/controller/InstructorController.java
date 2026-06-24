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

import com.gym.entity.EstadoRegistro;
import com.gym.entity.Instructor;
import com.gym.entity.Usuario;
import com.gym.service.InstructorService;

@Controller
@RequestMapping("/gestioninstructor")
public class InstructorController {

	@Autowired
	private InstructorService instructorService;

	@GetMapping("/lista")
	public String listar(Model model) {
		model.addAttribute("instructores", instructorService.listar());
		Instructor instructor = new Instructor();
		instructor.setUsuario(new Usuario());
		instructor.setEstado(EstadoRegistro.ACTIVO);
		model.addAttribute("instructor", instructor);
		model.addAttribute("usuarios", instructorService.listarUsuarios());
		model.addAttribute("estados", EstadoRegistro.values());
		return "instructor/mantInstructores";
	}

	@PostMapping("/registrar")
	public String registrar(@ModelAttribute("instructor") Instructor instructor, RedirectAttributes flash) {
		try {
			instructorService.registrar(instructor);
			flash.addFlashAttribute("msg", "Instructor registrado correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo registrar: el DNI ya existe o el usuario ya esta asignado.");
		}
		return "redirect:/gestioninstructor/lista";
	}

	@PostMapping("/actualizar")
	public String actualizar(@ModelAttribute("instructor") Instructor instructor, RedirectAttributes flash) {
		try {
			instructorService.actualizar(instructor);
			flash.addFlashAttribute("msg", "Instructor actualizado correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo actualizar: el DNI ya existe o el usuario ya esta asignado.");
		}
		return "redirect:/gestioninstructor/lista";
	}

	@GetMapping("/buscar/{id}")
	@ResponseBody
	public Instructor buscarPorId(@PathVariable("id") Integer id) {
		return instructorService.buscarPorId(id);
	}

	@GetMapping("/desactivar/{id}")
	public String desactivar(@PathVariable("id") Integer id, RedirectAttributes flash) {
		instructorService.desactivar(id);
		flash.addFlashAttribute("msg", "Instructor desactivado.");
		return "redirect:/gestioninstructor/lista";
	}
}
