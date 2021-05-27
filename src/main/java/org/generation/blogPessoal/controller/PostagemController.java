package org.generation.blogPessoal.controller;

import java.util.List;


import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//Crie um  controller

@RestController
@RequestMapping("/postagens")

//
@CrossOrigin("*")
public class PostagemController {

	@Autowired
	private PostagemRepository repository;

	// Crie um método findAll

	@GetMapping
	
	//ResponseEntity representa toda a resposta HTTP, ou seja, devolve como resposta o status code - informa o que aconteceu com a requisisição através de um valor que varia de 100 a 500 - headers 
	//e body - onde geralmente enviamos dados que queremos gravar no banco de dados
	public ResponseEntity<List<Postagem>> todasPostagens() {
		return ResponseEntity.ok(repository.findAll());
	}

	// Crie um método findById
	@GetMapping("/{id}")
	//A anotação @PathVariable indica que o valor da variável virá da URL
	public ResponseEntity<Postagem> postagemID(@PathVariable long id) {
		return repository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	// Crie um método findById
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> postagemPeloTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity<Postagem> post (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	}
	
	@PutMapping
	public ResponseEntity<Postagem> alterar (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
	}
	
	
	
}
