package org.generation.blogPessoal.controller;

import java.util.List;

import org.generation.blogPessoal.model.Tema;
import org.generation.blogPessoal.repository.TemaRepository;
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

//Crie uma camada de Controller com o nome de TemaController 
@RestController

/*O CORS (Cross-origin Resource Sharing) é um mecanismo utilizado pelos navegadores para compartilhar recursos 
 entre diferentes origens. O CORS faz uso de headers do HTTP para informar aos navegadores se determinado 
 recurso pode ser ou não acessado. Quando implementado, o CORS permite que um site acesse recursos de outro 
 site mesmo estando em domínios diferentes.
 Os navegadores fazem uso de uma funcionalidade de segurança chamada same-origin policy, que limita que um 
 recurso de  um site só pode ser acessado por outro site se os dois tiverem a mesma origem, ou seja, o mesmo 
 dominio, endereço.
 A origem de um site combinação entre o protocolo (http ou https), o host (painel.treinaweb.com.br) e a porta -
 que pode ser omitida -  de um determinado endereço (http://painel.treinaweb.com.br/).
 Se a origem de duas URLs forem idênticas, a interação entre esses recursos pode acontecer sem problema.*/

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tema")
public class TemaController {
	@Autowired
	private TemaRepository repository;
	
	//Crie um findAllTema, um endPoint com a capacidade de trazer todas os temas
	@GetMapping
	public ResponseEntity<List<Tema>> todosTemas(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	//Crie um findByIDTema, um endPoint com a função de trazer uma unico tema por id.
	@GetMapping("/{id}")
	public ResponseEntity<Tema> temaId(@PathVariable long id){
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	//Crie um findByDescricaoPostagem, um endPoint com a função de trazer um unico tema por descricao.
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Tema>> temaPelaDescricao(@PathVariable String descricao){
		return ResponseEntity.ok(repository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	//Crie um postTema, um endPoint com a função de gravar um novo tema no banco de dados.
	@PostMapping
	public ResponseEntity<Tema> inserirTema (@RequestBody Tema tema){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(repository.save(tema));
	}
	
	//Crie um putTema, um endPoint com a função de atualizar dados de um tema.
	@PutMapping
	public ResponseEntity<Tema> alterarTema (@RequestBody Tema tema){
		return ResponseEntity.ok(repository.save(tema));				
	}
	
	//Crie um deleteTema, um endPoint com a função de apagar um tema do banco de dados.
	@DeleteMapping("/{id}")
	public void deletarTema (@PathVariable long id) {
		repository.deleteById(id);
	}
}
