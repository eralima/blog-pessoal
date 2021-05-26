package org.generation.blogPessoal.repository;

import java.util.List;

import org.generation.blogPessoal.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Crie um repository 

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>{
	//Crie um controller com um método findAll
	public List<Postagem> findAllByTituloContainingIgnoreCase(String titulo);
	
}
