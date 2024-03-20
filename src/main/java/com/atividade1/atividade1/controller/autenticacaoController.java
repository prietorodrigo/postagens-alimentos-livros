package com.atividade1.atividade1.controller;

import com.atividade1.atividade1.model.usuarioModel;
import com.atividade1.atividade1.service.usuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class autenticacaoController {
    @Autowired
    usuarioService usuarioService;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(){
        return "auth/login";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String register(Model model){
        model.addAttribute("usuario", new usuarioModel());
        return "auth/register";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String registerUser(Model model, @Valid usuarioModel usuario, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute("messagemSuccesso", "Usuário cadastrado com sucesso!");
            model.addAttribute("bindingResult", bindingResult);
            return "auth/register";
        }
        List<Object> userPresentObj = usuarioService.isUserPresent(usuario);
        if((Boolean) userPresentObj.get(0)){
            model.addAttribute("messagemSuccesso", userPresentObj.get(1));
            return "auth/register";
        }

        usuarioService.saveUser(usuario);
        model.addAttribute("messagemSuccesso", "Usuário cadastrado com sucesso!");

        return "auth/login";
    }
}
