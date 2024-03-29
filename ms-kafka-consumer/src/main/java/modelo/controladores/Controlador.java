package modelo.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import modelo.entidades.Usuario;
import modelo.gestion.GestorUsuario;

@RestController
public class Controlador {

	@Autowired
	private GestorUsuario gu;
	
	public void guardarUsuario(Usuario usuario) {
		gu.insertarUsuario(usuario);
	}
	
	@GetMapping("/listar")
	@CrossOrigin("http://localhost:4200")
	public  List<Usuario> listarUsuarios() {
		return gu.listar();
	}
	
	@GetMapping("/buscar")
	@CrossOrigin("http://localhost:4200")
	public  List<Usuario> buscarUsuariosApellido(@RequestParam String apellido) {
		return gu.buscarApellido(apellido);
	}
	
	@DeleteMapping("/borrar/{id}")
	@CrossOrigin("http://localhost:4200")
	public void borrarUsuario(@PathVariable("id") String id) {
		gu.borrar(id);
		
	}

}
