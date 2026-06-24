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
import com.gym.entity.Usuario;
import com.gym.service.UsuarioService;

@Controller
@RequestMapping("/gestionusuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/lista")
	public String listar(Model model) {
		model.addAttribute("usuarios", usuarioService.listar());
		// objeto vacio para el modal; inicializamos el Rol para th:field="*{idRol.idRol}"
		Usuario usuario = new Usuario();
		usuario.setIdRol(new Rol());
		usuario.setEstado((byte) 1);
		model.addAttribute("usuario", usuario);
		model.addAttribute("roles", usuarioService.listarRoles());
		return "usuario/mantUsuarios";
	}

	@PostMapping("/registrar")
	public String registrar(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes flash) {
		try {
			usuarioService.registrar(usuario);
			flash.addFlashAttribute("msg", "Usuario registrado correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo registrar: el username ya existe.");
		}
		return "redirect:/gestionusuario/lista";
	}

	@PostMapping("/actualizar")
	public String actualizar(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes flash) {
		try {
			usuarioService.actualizar(usuario);
			flash.addFlashAttribute("msg", "Usuario actualizado correctamente.");
		} catch (IllegalArgumentException e) {
			flash.addFlashAttribute("error", e.getMessage());
		} catch (DataIntegrityViolationException e) {
			flash.addFlashAttribute("error", "No se pudo actualizar: el username ya existe.");
		}
		return "redirect:/gestionusuario/lista";
	}

	@GetMapping("/buscar/{id}")
	@ResponseBody
	public Usuario buscarPorId(@PathVariable("id") Integer id) {
		return usuarioService.buscarPorId(id);
	}

	@GetMapping("/desactivar/{id}")
	public String desactivar(@PathVariable("id") Integer id, RedirectAttributes flash) {
		usuarioService.desactivar(id);
		flash.addFlashAttribute("msg", "Usuario desactivado.");
		return "redirect:/gestionusuario/lista";
	}
}
