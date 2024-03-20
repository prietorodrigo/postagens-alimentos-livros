package com.atividade1.atividade1.controller;

import com.atividade1.atividade1.model.livroModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.atividade1.atividade1.repository.livroRepository;
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
import java.util.List;
import java.util.Optional;

@Controller
public class livroController {
    @Autowired livroRepository livroRepository;

    @RequestMapping(value="/inicioL", method = RequestMethod.GET)
    public String inicio() { return "home"; }

    @RequestMapping(value="/novoLivro", method=RequestMethod.GET)
    public String novoLivro() {
        return "cadastrarLivro";
    }

    @RequestMapping(value="/novoLivro", method=RequestMethod.POST)
    public String cadastroLivro(@Valid livroModel livro, BindingResult result, RedirectAttributes msg, @RequestParam("file") MultipartFile imagem) {
        if(result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao cadastrar. Por favor, preencha todos os campos");
            return "redirect:/novoLivro";
        }

        try {
            if (!imagem.isEmpty()) {
                byte[] bytes = imagem.getBytes();
                Path caminho = Paths.get("./src/main/resources/static/img/"+imagem.getOriginalFilename());
                Files.write(caminho, bytes);
                livro.setImagem(imagem.getOriginalFilename());
            }
        } catch (IOException e) {
            System.out.println("Erro imagem");
        }

        livroRepository.save(livro);
        msg.addFlashAttribute("sucesso", "Livro cadastrado.");

        return "redirect:/novoLivro";
    }

    @RequestMapping(value="/imagem/{imagem}", method=RequestMethod.GET)
    @ResponseBody
    public byte[] getImagens(@PathVariable("imagem") String imagem) throws IOException {
        File caminho = new File ("./src/main/resources/static/img/"+imagem);
        if (imagem != null || imagem.trim().length() > 0) {
            return Files.readAllBytes(caminho.toPath());
        }
        return null;
    }

    @RequestMapping(value="/listarLivros", method=RequestMethod.GET)
    public ModelAndView getLivros() {
        ModelAndView mv = new ModelAndView("listarLivros");
        List<livroModel> livros = livroRepository.findAll();
        mv.addObject("livros", livros);
        return mv;
    }

    @RequestMapping(value="/editarLivro/{id}", method=RequestMethod.GET)
    public ModelAndView editar(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("editarLivro");
        Optional<livroModel> livro = livroRepository.findById(id);
        mv.addObject("titulo", livro.get().getTitulo());
        mv.addObject("autor", livro.get().getAutor());
        mv.addObject("editorial", livro.get().getEditorial());
        mv.addObject("id", livro.get().getId());
        return mv;
    }

    @RequestMapping(value="/editarLivro/{id}", method=RequestMethod.POST)
    public String editarLivroBanco(livroModel livro, RedirectAttributes msg) {
        livroModel livroExistente = livroRepository.findById(livro.getId()).orElse(null);
        livroExistente.setTitulo(livro.getTitulo());
        livroExistente.setAutor(livro.getAutor());
        livroExistente.setEditorial(livro.getEditorial());
        livroRepository.save(livroExistente);
        return "redirect:/listarLivros";
    }

    @RequestMapping(value="/excluirLivro/{id}", method=RequestMethod.GET)
    public String excluirLivro(@PathVariable("id") Long id) {
        livroRepository.deleteById(id);
        return "redirect:/listarLivros";
    }

    @RequestMapping(value="/vermaisLivro/{id}", method=RequestMethod.GET)
    public ModelAndView vermaisLivro(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("vermaisLivro");
        Optional<livroModel> livros = livroRepository.findById(id);
        mv.addObject("imagem", livros.get().getImagem());
        mv.addObject("titulo", livros.get().getTitulo());
        mv.addObject("autor", livros.get().getAutor());
        mv.addObject("editorial", livros.get().getEditorial());
        return mv;
    }

}
