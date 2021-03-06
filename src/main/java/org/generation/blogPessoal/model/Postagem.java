package org.generation.blogPessoal.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Crie uma model com todos os atributos necessários, getters e  seters,  anotações

@Entity 
@Table (name = "postagens") 
public class Postagem {
	@Id
	/*A anotation @GeneratedValue é utilizada para informar que a geração do valor do id será gerenciada pelo provedor de persistência (JPA Hibernate)
	Quando não anotamos o campo com essa opção, significa que a responsabilidade de gerar e gerenciar as chaves primárias do nosso código
	Caso optemos por modificar a estratégia de geração da chave primária, passamos essa informação no atributo strategy de @GeneratedValue
	GenerationType.IDENTITY informa ao provedor de persistência que os valores a serem atribuídos a chave primária serão gerados pela coluna de auto incremento do banco de dados. 
	Assim, um valor para o identificador é gerado para cada registro inserido no banco.*/
	
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;
	
	@NotNull (message = "A postagem deve ter um título") @Size(min = 5, max = 100) private String titulo;
	
	@NotNull (message = "A postagem deve ter um texto") @Size(min = 10, max = 500) private String texto;
	
	/*A anotation @Temporal deve ser especificada para campos que são gerenciados pelo provedor de persistência (JPA Hibernate)
	ou propriedades do tipo java.util.Date ou java.util.Calendar.
	O TemporalType.TIMESTAMP indica o tipo*/
	@Temporal(TemporalType.TIMESTAMP)
	private Date data = new java.sql.Date(System.currentTimeMillis());
	
	/*Em um relacionamento OneToMany/ManyToOne, o lado proprietário - o "pai" - é normalmente o lado "many" da relação.
	Normalmente, o lado proprietário - o "pai" - é o possui a foreign key - a "chave estrangeira".*/
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	
	/*A annotation @JoinColumn define qual é a entidade é proprietaria no relacionamento OneToMany/ManyToOne. Essa annotation 
	é utilizado para nomear a coluna que possui a foreign key - a "chave estrangeira - requerida pela associação. No banco de 
	dados, a coluna criada vai receber o nome passado no atributo name
	Nesse caso, a entidade Postagem terá uma coluna de foreign key - a "chave estrangeira - chamada tema_id referindo-se ao 
	atributo id da entidade tema*/
	@JoinColumn (name = "tema_id") 
	@JsonIgnoreProperties("postagens")
	private Tema tema;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Tema getTema() {
		return tema;
	}
	public void setTema(Tema tema) {
		this.tema = tema;
	}
}
