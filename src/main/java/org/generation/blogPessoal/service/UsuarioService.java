 package org.generation.blogPessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.model.UsuarioLogin;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	public Usuario cadastrarUsuario(Usuario usuario) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaEncoder = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaEncoder);

		return repository.save(usuario);
	}

	public Optional<UsuarioLogin> logarUsuario(Optional<UsuarioLogin> user) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByUsuario(user.get().getUsuario());

		if (usuario.isPresent()) {
			if (encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {

				/* O cliente envia uma reqquisição HTTP com o Authorization Headers: Basic {credenciais em base 64 no formato 
				usuário:senha}*/
				String credencial = user.get().getUsuario() + ":" + user.get().getSenha(); //credencial no formato usuário:senha
				//codificando a credencial para Base64
				byte[] autorizacaoBase64 = Base64.encodeBase64(credencial.getBytes(Charset.forName("US-ASCII"))); 
				//criando o Authorization Headers que vai ser mandado nas requisições do cliente
				String autorizacaoHeader = "Basic " + new String(autorizacaoBase64); 

				user.get().setToken(autorizacaoHeader);				
				user.get().setSenha(usuario.get().getSenha());

				return user;

			}
		}
		return null;
	}

}