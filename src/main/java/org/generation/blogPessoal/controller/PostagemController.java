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
import org.springframework.web.bind.annotation.RestController;

//Crie uma camada de Controller chamada PostagemController 

@RestController
@RequestMapping("/postagens")
@CrossOrigin("*")
public class PostagemController {

	@Autowired
	private PostagemRepository repository;

	// Crie um método findAllPostagem, um endPoint com a capacidade de trazer todas as Postagem.
	
	@GetMapping
	//ResponseEntity representa toda a resposta HTTP, ou seja, devolve como resposta o status code - informa o que aconteceu com a requisisição através de um valor que varia de 100 a 500 - headers 
	//e body - onde geralmente enviamos dados que queremos gravar no banco de dados
	public ResponseEntity<List<Postagem>> todasPostagens() {
		return ResponseEntity.ok(repository.findAll());
	}

	// Crie um método findByIDPostagem, um endPoint com a função de trazer uma única postagem por id.

	@GetMapping("/{id}")
	//A anotação @PathVariable indica que o valor da variável virá da URL
	public ResponseEntity<Postagem> postagemID(@PathVariable long id) {
		return repository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> postagemPeloTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	//Crie um método postPostagem, um endPoint com a função de gravar uma nova postagem no banco de dados.

	@PostMapping
	public ResponseEntity<Postagem> postPostagem (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	}
	
	//Crie um método putPostagem, um endPoint com a função de atualizar dados de uma postagem.

	@PutMapping
	public ResponseEntity<Postagem> alterarPostagem (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
	}
	
	//Crie um método deletePostagem, um endPoint com a função de apagar uma Postagem do banco de dados.
	@DeleteMapping("/{id}")
	public void deletarPostagem(@PathVariable long id) {
		repository.deleteById(id);
	}
	

}
