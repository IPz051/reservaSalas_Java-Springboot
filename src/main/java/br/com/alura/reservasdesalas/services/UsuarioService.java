package br.com.alura.reservasdesalas.services;

import br.com.alura.reservasdesalas.entities.Usuario;
import br.com.alura.reservasdesalas.exception.BusinessException;
import br.com.alura.reservasdesalas.exception.ResourceNotFoundException;
import br.com.alura.reservasdesalas.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository
    ) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Usuario> listar() {

        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {

        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário não encontrado."
                        ));
    }

    @Transactional
    public Usuario criar(Usuario usuario) {

        validarEmailDuplicado(usuario.getEmail());

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(
            Long id,
            Usuario usuarioAtualizado
    ) {

        Usuario usuarioExistente =
                buscarPorId(id);

        validarEmailDuplicadoAtualizacao(
                id,
                usuarioAtualizado.getEmail()
        );

        usuarioExistente.atualizar(
                usuarioAtualizado.getNome(),
                usuarioAtualizado.getEmail()
        );

        return usuarioRepository.save(
                usuarioExistente
        );
    }

    @Transactional
    public void deletar(Long id) {

        Usuario usuario = buscarPorId(id);

        usuarioRepository.delete(usuario);
    }

    private void validarEmailDuplicado(
            String email
    ) {

        boolean emailExiste =
                usuarioRepository.existsByEmail(email);

        if (emailExiste) {

            throw new BusinessException(
                    "Email já cadastrado."
            );
        }
    }

    private void validarEmailDuplicadoAtualizacao(
            Long id,
            String email
    ) {

        usuarioRepository.findByEmail(email)
                .ifPresent(usuario -> {

                    if (!usuario.getId().equals(id)) {

                        throw new BusinessException(
                                "Email já está em uso."
                        );
                    }
                });
    }
}
