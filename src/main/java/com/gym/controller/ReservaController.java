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
import com.gym.entity.Cliente;
import com.gym.entity.EstadoReserva;
import com.gym.entity.Reserva;
import com.gym.service.ReservaService;

@Controller
@RequestMapping("/gestionreserva")
public class ReservaController {

	@Autowired
	private ReservaService reservaService;

	@GetMapping("/lista")
	public String listar(Model model) {
		model.addAttribute("reservas", reservaService.listar());
		Reserva reserva = new Reserva();
		reserva.setClaseGrupal(new ClaseGrupal());
		reserva.setCliente(new Cliente());
		reserva.setEstadoReserva(EstadoReserva.CONFIRMADA);
		model.addAttribute("reserva", reserva);
		model.addAttribute("clases", reservaService.listarClases());
		model.addAttribute("clientes", reservaService.listarClientes());
		model.addAttribute("estados", EstadoReserva.values());
		return "reserva/mantReservas";
	}

	@PostMapping("/registrar")
	public String registrar(@ModelAttribute("reserva") Reserva reserva, RedirectAttributes flash) {
		try {
			reservaService.registrar(reserva);
			flash.addFlashAttribute("msg", "Reserva registrada correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "Este cliente ya tiene una reserva en esa clase.");
		}
		return "redirect:/gestionreserva/lista";
	}

	@PostMapping("/actualizar")
	public String actualizar(@ModelAttribute("reserva") Reserva reserva, RedirectAttributes flash) {
		try {
			reservaService.actualizar(reserva);
			flash.addFlashAttribute("msg", "Reserva actualizada correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		}
		return "redirect:/gestionreserva/lista";
	}

	@GetMapping("/buscar/{id}")
	@ResponseBody
	public Reserva buscarPorId(@PathVariable("id") Integer id) {
		return reservaService.buscarPorId(id);
	}

	@GetMapping("/desactivar/{id}")
	public String desactivar(@PathVariable("id") Integer id, RedirectAttributes flash) {
		reservaService.desactivar(id);
		flash.addFlashAttribute("msg", "Reserva cancelada.");
		return "redirect:/gestionreserva/lista";
	}
}
