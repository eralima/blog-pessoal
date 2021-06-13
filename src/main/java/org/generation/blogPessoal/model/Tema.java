package org.generation.blogPessoal.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Crie uma model Tema com todos os atributos necessários, getters e  seters,  anotações

@Entity
@Table (name = "temas")
public class Tema {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "Insira uma descrição no tema da postagem") private String descricao;
	
	/*O atributo mappedBy é utilizado quando temos um relacionamento bidirecional entre duas classes. É um atributo utilizado
	nas annotations @OneToMany, @OneToOne e @ManyToMany. Utilizamos o atributo mappedBy para definir o lado de referência - o 
	lado "filho".
	O valor do atributo mappedBy é o nome do atributo de mapeamento de associação no lado do proprietário.*/
	
	/*O atributo fetch */
	@OneToMany(mappedBy = "tema", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("tema")
	private List<Postagem> postagens = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Postagem> getPostagem() {
		return postagens;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagens = postagem;
	}
}
