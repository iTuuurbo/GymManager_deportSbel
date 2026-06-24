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

import com.gym.entity.Asistencia;
import com.gym.entity.Cliente;
import com.gym.service.AsistenciaService;

@Controller
@RequestMapping("/gestionasistencia")
public class AsistenciaController {

	@Autowired
	private AsistenciaService asistenciaService;

	@GetMapping("/lista")
	public String listar(Model model) {
		model.addAttribute("asistencias", asistenciaService.listar());
		Asistencia asistencia = new Asistencia();
		asistencia.setCliente(new Cliente());
		model.addAttribute("asistencia", asistencia);
		model.addAttribute("clientes", asistenciaService.listarClientes());
		return "asistencia/mantAsistencias";
	}

	@PostMapping("/registrar")
	public String registrar(@ModelAttribute("asistencia") Asistencia asistencia, RedirectAttributes flash) {
		try {
			asistenciaService.registrar(asistencia);
			flash.addFlashAttribute("msg", "Asistencia registrada correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/gestionasistencia/lista";
	}

	@PostMapping("/actualizar")
	public String actualizar(@ModelAttribute("asistencia") Asistencia asistencia, RedirectAttributes flash) {
		try {
			asistenciaService.actualizar(asistencia);
			flash.addFlashAttribute("msg", "Asistencia actualizada correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/gestionasistencia/lista";
	}

	@GetMapping("/buscar/{id}")
	@ResponseBody
	public Asistencia buscarPorId(@PathVariable("id") Integer id) {
		return asistenciaService.buscarPorId(id);
	}

	// Asistencia no tiene estado: el boton elimina fisicamente.
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") Integer id, RedirectAttributes flash) {
		try {
			asistenciaService.eliminar(id);
			flash.addFlashAttribute("msg", "Asistencia eliminada.");
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo eliminar la asistencia.");
		}
		return "redirect:/gestionasistencia/lista";
	}
}
