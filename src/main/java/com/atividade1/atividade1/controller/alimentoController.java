package com.atividade1.atividade1.controller;

import com.atividade1.atividade1.model.alimentoModel;
import com.atividade1.atividade1.model.livroModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.atividade1.atividade1.repository.alimentoRepository;
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
public class alimentoController {
    @Autowired alimentoRepository alimentoRepository;

    @RequestMapping(value="/inicioA", method = RequestMethod.GET)
    public String inicio() { return "home"; }

    @RequestMapping(value="/novoAlimento", method=RequestMethod.GET)
    public String novoAlimento() {
        return "cadastrarAlimento";
    }

    @RequestMapping(value="/novoAlimento", method=RequestMethod.POST)
    public String cadastroAlimento(@Valid alimentoModel alimento, BindingResult result, RedirectAttributes msg, @RequestParam("file") MultipartFile imagem) {
        if(result.hasErrors()) {
            msg.addFlashAttribute("erro", "Erro ao cadastrar. Por favor, preencha todos os campos");
            return "redirect:/novoAlimento";
        }

        try {
            if (!imagem.isEmpty()) {
                byte[] bytes = imagem.getBytes();
                Path caminho = Paths.get("./src/main/resources/static/img/"+imagem.getOriginalFilename());
                Files.write(caminho, bytes);
                alimento.setImagem(imagem.getOriginalFilename());
            }
        } catch (IOException e) {
            System.out.println("Erro imagem");
        }

        alimentoRepository.save(alimento);
        msg.addFlashAttribute("sucesso", "Alimento cadastrado.");

        return "redirect:/novoAlimento";
    }


    @ResponseBody
    public byte[] getImagens(@PathVariable("imagem") String imagem) throws IOException {
        File caminho = new File ("./src/main/resources/static/img/"+imagem);
        if (imagem != null || imagem.trim().length() > 0) {
            return Files.readAllBytes(caminho.toPath());
        }
        return null;
    }

    @RequestMapping(value="/listarAlimentos", method=RequestMethod.GET)
    public ModelAndView getAlimentos() {
        ModelAndView mv = new ModelAndView("listarAlimentos");
        List<alimentoModel> alimentos = alimentoRepository.findAll();
        mv.addObject("alimentos", alimentos);
        return mv;
    }

    @RequestMapping(value="/editarAlimento/{id}", method=RequestMethod.GET)
    public ModelAndView editar(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("editarAlimento");
        Optional<alimentoModel> alimento = alimentoRepository.findById(id);
        mv.addObject("nome", alimento.get().getNome());
        mv.addObject("categoria", alimento.get().getCategoria());
        mv.addObject("calorias", alimento.get().getCalorias());
        mv.addObject("id", alimento.get().getId());
        return mv;
    }

    @RequestMapping(value="/editarAlimento/{id}", method=RequestMethod.POST)
    public String editarAlimentoBanco(alimentoModel alimento, RedirectAttributes msg) {
        alimentoModel alimentoExistente = alimentoRepository.findById(alimento.getId()).orElse(null);
        alimentoExistente.setNome(alimento.getNome());
        alimentoExistente.setCategoria(alimento.getCategoria());
        alimentoExistente.setCalorias(alimento.getCalorias());
        alimentoRepository.save(alimentoExistente);
        return "redirect:/listarAlimentos";
    }

    @RequestMapping(value="/excluirAlimento/{id}", method=RequestMethod.GET)
    public String excluirAlimento(@PathVariable("id") Long id) {
        alimentoRepository.deleteById(id);
        return "redirect:/listarAlimentos";
    }

    @RequestMapping(value="/vermaisAlimento/{id}", method=RequestMethod.GET)
    public ModelAndView vermaisAlimento(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("vermaisAlimento");
        Optional<alimentoModel> alimentos = alimentoRepository.findById(id);
        mv.addObject("imagem", alimentos.get().getImagem());
        mv.addObject("nome", alimentos.get().getNome());
        mv.addObject("categoria", alimentos.get().getCategoria());
        mv.addObject("calorias", alimentos.get().getCalorias());
        return mv;
    }

    @RequestMapping(value="/calorias/{calorias}", method=RequestMethod.GET)
    public ModelAndView getAlimentos(@PathVariable("calorias") int calorias) {
        ModelAndView mv = new ModelAndView("listarAlimentos");
        List<alimentoModel> alimentos = alimentoRepository.findAlimentosByCalorias(calorias);
        mv.addObject("alimentos", alimentos);
        return mv;
    }

    @RequestMapping(value={"/pesquisar", "/calorias/{calorias}"}, method=RequestMethod.POST)
    public ModelAndView getAlimentosByNome(@RequestParam("texto") String pesquisar) {
        ModelAndView mv = new ModelAndView("listarAlimentos");
        List<alimentoModel> alimentos = alimentoRepository.findAlimentosByNomeLike("%"+pesquisar+"%");
        mv.addObject("alimentos", alimentos);
        return mv;
    }

}
