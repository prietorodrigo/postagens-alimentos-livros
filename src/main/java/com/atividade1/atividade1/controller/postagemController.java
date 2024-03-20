package com.atividade1.atividade1.controller;

import com.atividade1.atividade1.model.alimentoModel;
import com.atividade1.atividade1.model.postagemModel;
import com.atividade1.atividade1.repository.postagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class postagemController {
    @Autowired
    postagemRepository postagemRepository;

    @RequestMapping(value="/inicio", method= RequestMethod.GET)
    public String inicio() {
        return "home";
    }

    @RequestMapping(value="/novaPostagem", method=RequestMethod.GET)
    public String novaPostagem() {
        return "cadastrarPostagem";
    }

    @RequestMapping(value="/novaPostagem", method=RequestMethod.POST)
    public String cadastroPostagem(@Valid postagemModel postagem, BindingResult result, RedirectAttributes msg, @RequestParam("file") MultipartFile imagem) {
        if(result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao cadastrar. Por favor, preencha todos os campos");
            return "redirect:/novaPostagem";
        }
        postagem.setData(LocalDate.now());

        try {
            if (!imagem.isEmpty()) {
                byte[] bytes = imagem.getBytes();
                Path caminho = Paths.get("./src/main/resources/static/img/"+imagem.getOriginalFilename());
                Files.write(caminho, bytes);
                postagem.setImagem(imagem.getOriginalFilename());
            }
        } catch (IOException e) {
            System.out.println("Erro imagem");
        }

        postagemRepository.save(postagem);
        msg.addFlashAttribute("sucesso", "Postagem cadastrada.");

        return "redirect:/novaPostagem";
    }

    @ResponseBody
    public byte[] getImagens(@PathVariable("imagem") String imagem) throws IOException {
        File caminho = new File ("./src/main/resources/static/img/"+imagem);
        if (imagem != null || imagem.trim().length() > 0) {
            return Files.readAllBytes(caminho.toPath());
        }
        return null;
    }

    @RequestMapping(value="/listarPostagens", method=RequestMethod.GET)
    public ModelAndView getPostagens() {
        ModelAndView mv = new ModelAndView("listarPostagens");
        List<postagemModel> postagens = postagemRepository.findAll();
        mv.addObject("postagens", postagens);
        return mv;
    }

    @RequestMapping(value="/editarPostagem/{id}", method=RequestMethod.GET)
    public ModelAndView editar(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("editarPostagem");
        Optional<postagemModel> postagem = postagemRepository.findById(id);
        mv.addObject("autor", postagem.get().getAutor());
        mv.addObject("titulo", postagem.get().getTitulo());
        mv.addObject("texto", postagem.get().getTexto());
        mv.addObject("id", postagem.get().getId());
        return mv;
    }

    @RequestMapping(value="/editarPostagem/{id}", method=RequestMethod.POST)
    public String editarPostagemBanco(postagemModel postagem, RedirectAttributes msg) {
        postagemModel postagemExistente = postagemRepository.findById(postagem.getId()).orElse(null);
        postagemExistente.setAutor(postagem.getAutor());
        postagemExistente.setTitulo(postagem.getTitulo());
        postagemExistente.setTexto(postagem.getTexto());
        postagemRepository.save(postagemExistente);
        return "redirect:/listarPostagens";
    }

    @RequestMapping(value="/excluirPostagem/{id}", method=RequestMethod.GET)
    public String excluirPostagem(@PathVariable("id") Long id) {
        postagemRepository.deleteById(id);
        return "redirect:/listarPostagens";
    }

    @RequestMapping(value="/vermaisPostagem/{id}", method=RequestMethod.GET)
    public ModelAndView vermaisPostagem(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("vermaisPostagem");
        Optional<postagemModel> postagens = postagemRepository.findById(id);
        mv.addObject("imagem", postagens.get().getImagem());
        mv.addObject("autor", postagens.get().getAutor());
        mv.addObject("titulo", postagens.get().getTitulo());
        mv.addObject("texto", postagens.get().getTexto());
        mv.addObject("data", postagens.get().getData());
        return mv;
    }

}
