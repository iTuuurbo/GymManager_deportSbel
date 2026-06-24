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

import com.gym.entity.CitaNutricional;
import com.gym.entity.Cliente;
import com.gym.entity.EstadoCita;
import com.gym.entity.Nutricionista;
import com.gym.service.CitaNutricionalService;

@Controller
@RequestMapping("/gestioncita")
public class CitaNutricionalController {

	@Autowired
	private CitaNutricionalService citaService;

	@GetMapping("/lista")
	public String listar(Model model) {
		model.addAttribute("citas", citaService.listar());
		CitaNutricional cita = new CitaNutricional();
		cita.setCliente(new Cliente());
		cita.setNutricionista(new Nutricionista());
		cita.setEstado(EstadoCita.PENDIENTE);
		model.addAttribute("cita", cita);
		model.addAttribute("clientes", citaService.listarClientes());
		model.addAttribute("nutricionistas", citaService.listarNutricionistas());
		model.addAttribute("estados", EstadoCita.values());
		return "cita/mantCitas";
	}

	@PostMapping("/registrar")
	public String registrar(@ModelAttribute("cita") CitaNutricional cita, RedirectAttributes flash) {
		try {
			citaService.registrar(cita);
			flash.addFlashAttribute("msg", "Cita registrada correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo registrar la cita.");
		}
		return "redirect:/gestioncita/lista";
	}

	@PostMapping("/actualizar")
	public String actualizar(@ModelAttribute("cita") CitaNutricional cita, RedirectAttributes flash) {
		try {
			citaService.actualizar(cita);
			flash.addFlashAttribute("msg", "Cita actualizada correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/gestioncita/lista";
	}

	@GetMapping("/buscar/{id}")
	@ResponseBody
	public CitaNutricional buscarPorId(@PathVariable("id") Integer id) {
		return citaService.buscarPorId(id);
	}

	@GetMapping("/desactivar/{id}")
	public String desactivar(@PathVariable("id") Integer id, RedirectAttributes flash) {
		citaService.desactivar(id);
		flash.addFlashAttribute("msg", "Cita cancelada.");
		return "redirect:/gestioncita/lista";
	}
}
