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
import com.gym.entity.EstadoRegistro;
import com.gym.entity.Usuario;
import com.gym.service.ClienteService;

@Controller
@RequestMapping("/gestioncliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	// get ---- listar, buscar
	// post --- registrar, actualizar

	// LISTAR -> GET /gestioncliente/lista
	@GetMapping("/lista")
	public String listar(Model model) {
		model.addAttribute("clientes", clienteService.listar());

		Cliente cliente = new Cliente();
		cliente.setEstado(EstadoRegistro.ACTIVO);
		model.addAttribute("cliente", cliente);

		model.addAttribute("estados", EstadoRegistro.values());

		return "cliente/mantClientes";
	}

	// REGISTRAR -> POST /gestioncliente/registrar
	@PostMapping("/registrar")
	public String registrar(@ModelAttribute("cliente") Cliente cliente,
			RedirectAttributes flash) {
		try {
			clienteService.registrar(cliente);
			flash.addFlashAttribute("msg", "Cliente registrado correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error",
					"No se pudo registrar: el DNI ya existe o el usuario ya esta asignado a otro cliente.");
		}
		return "redirect:/gestioncliente/lista";
	}

	// ACTUALIZAR -> POST /gestioncliente/actualizar
	@PostMapping("/actualizar")
	public String actualizar(@ModelAttribute("cliente") Cliente cliente,
			RedirectAttributes flash) {
		try {
			clienteService.actualizar(cliente);
			flash.addFlashAttribute("msg", "Cliente actualizado correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error",
					"No se pudo actualizar: el DNI ya existe o el usuario ya esta asignado a otro cliente.");
		}
		return "redirect:/gestioncliente/lista";
	}

	// BUSCAR POR ID (para llenar el modal al editar) -> GET /gestioncliente/buscar/{id}
	// Devuelve la entidad como JSON (estilo del profe). Las relaciones que
	// causarian recursion/lazy van marcadas con @JsonIgnore en las entidades.
	@GetMapping("/buscar/{id}")
	@ResponseBody
	public Cliente buscarPorId(@PathVariable("id") Integer id) {
		return clienteService.buscarPorId(id);
	}

	// DESACTIVAR (baja logica) -> GET /gestioncliente/desactivar/{id}
	@GetMapping("/desactivar/{id}")
	public String desactivar(@PathVariable("id") Integer id, RedirectAttributes flash) {
		clienteService.desactivar(id);
		flash.addFlashAttribute("msg", "Cliente desactivado.");
		return "redirect:/gestioncliente/lista";
	}
}
