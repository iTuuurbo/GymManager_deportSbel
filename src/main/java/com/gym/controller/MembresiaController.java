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

import com.gym.entity.Cliente;
import com.gym.entity.EstadoMembresia;
import com.gym.entity.Membresia;
import com.gym.entity.TipoMembresia;
import com.gym.service.MembresiaService;

@Controller
@RequestMapping("/gestionmembresia")
public class MembresiaController {

	@Autowired
	private MembresiaService membresiaService;

	@GetMapping("/lista")
	public String listar(Model model) {
		model.addAttribute("membresias", membresiaService.listar());
		Membresia membresia = new Membresia();
		membresia.setCliente(new Cliente());
		membresia.setTipoMembresia(new TipoMembresia());
		membresia.setEstado(EstadoMembresia.ACTIVA);
		model.addAttribute("membresia", membresia);
		model.addAttribute("clientes", membresiaService.listarClientes());
		model.addAttribute("tiposMembresia", membresiaService.listarTiposMembresia());
		model.addAttribute("estados", EstadoMembresia.values());
		return "membresia/mantMembresias";
	}

	@PostMapping("/registrar")
	public String registrar(@ModelAttribute("membresia") Membresia membresia, RedirectAttributes flash) {
		try {
			membresiaService.registrar(membresia);
			flash.addFlashAttribute("msg", "Membresia registrada correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo registrar la membresia.");
		}
		return "redirect:/gestionmembresia/lista";
	}

	@PostMapping("/actualizar")
	public String actualizar(@ModelAttribute("membresia") Membresia membresia, RedirectAttributes flash) {
		try {
			membresiaService.actualizar(membresia);
			flash.addFlashAttribute("msg", "Membresia actualizada correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo actualizar la membresia.");
		}
		return "redirect:/gestionmembresia/lista";
	}

	@GetMapping("/buscar/{id}")
	@ResponseBody
	public Membresia buscarPorId(@PathVariable("id") Integer id) {
		return membresiaService.buscarPorId(id);
	}

	@GetMapping("/desactivar/{id}")
	public String desactivar(@PathVariable("id") Integer id, RedirectAttributes flash) {
		membresiaService.desactivar(id);
		flash.addFlashAttribute("msg", "Membresia suspendida.");
		return "redirect:/gestionmembresia/lista";
	}
}
