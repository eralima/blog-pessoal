package org.generation.blogPessoal.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*Ao adicionar a aplicação a dependência do SpringSecurity, automaticamente todos os endpoints da aplicação serão bloqueados.
 Assim, além da dependencia, é necessário uma classe de configuração que define as configurações de segurança da aplicação, ou seja,
 vai definir os protocolos de autenticação, autorização, proteção e armazenamento.*/
@Configuration
@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

	/*A interface UserDetailsService é usada para recuperar dados do usuário. Possui um método loadUserByUsername () que pode ser 
	substituído para personalizar o processo de localização do usuário.*/
	@Autowired
	private UserDetailsService service;

	/**/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service);
		
		auth.inMemoryAuthentication()
		.withUser("Generation")
		.password(passwordEncoder().encode("generation"));
	}
	


	/*O sPRING VAI GERENCIA ESSA CLASSE DE UMA BIBLIOTECA EXTERNA */
	@Bean /*Normalmente usar em classes de configuração*/
	
	/*Definindo o codificador de senha*/
	public PasswordEncoder passwordEncoder() {
		/**/
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*Definir para quais métodos de quais endpoints é necessário ou não autenticação*/
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/usuarios/login").permitAll()
		.antMatchers(HttpMethod.POST, "/usuarios/cadastro").permitAll()
		.anyRequest().authenticated()
		
		//Definição dos protocolos de autenticação
		/* O SpringSecurity disponibiliza alguns mecanismos para realizar a autenticação. Um deles é utlizando os dados de 
		usuário e senha para fazer login. Nesse caso, a forma utilizada é o Basic Authentication (httpBasic()) - além dessa, existe
		o Form login e o Digest Authentication.
		O Basic Authentication é um esquema de autenticação simples integrado ao protocolo HTTP. O cliente envia uma reqquisição 
		HTTP com o Authorization Headers Basic {credenciais em base 64 no formato usuário:senha}*/
		.and().httpBasic()
		
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		
		//Definição dos protocolos de proteção
		.and().cors()
		.and().csrf().disable();
	}
}