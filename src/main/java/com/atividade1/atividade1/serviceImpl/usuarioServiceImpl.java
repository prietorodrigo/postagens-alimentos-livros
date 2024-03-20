package com.atividade1.atividade1.serviceImpl;

import com.atividade1.atividade1.model.usuarioModel;
import com.atividade1.atividade1.service.usuarioService;
import com.atividade1.atividade1.repository.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class usuarioServiceImpl implements usuarioService, UserDetailsService {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    usuarioRepository usuarioRepository;

    @Override
    public void saveUser(usuarioModel usuario) {
        String encodedPassword = bCryptPasswordEncoder.encode(usuario.getPassword());
        usuario.setPassword(encodedPassword);
//        user.setRole(Role.USER);
        usuarioRepository.save(usuario);
    }

    @Override
    public List<Object> isUserPresent(usuarioModel usuario) {
        boolean userExists = false;
        String message = null;
        Optional<usuarioModel> existingUserEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if(existingUserEmail.isPresent()){
            userExists = true;
            message = "E-mail já presente!";
        }
        Optional<usuarioModel> existingUserCelular = usuarioRepository.findByCelular(usuario.getCelular());
        if(existingUserCelular.isPresent()){
            userExists = true;
            message = "Número de celular já presente!";
        }
        if (existingUserEmail.isPresent() && existingUserCelular.isPresent()) {
            message = "E-mail e número de celular já estão presentes!";
        }
        System.out.println("existingUserEmail.isPresent() - "+existingUserEmail.isPresent()+"existingUserMobile.isPresent() - "+existingUserCelular.isPresent());
        return Arrays.asList(userExists, message);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException(
                        String.format("USER_NOT_FOUND", email)
                ));
    }

}
