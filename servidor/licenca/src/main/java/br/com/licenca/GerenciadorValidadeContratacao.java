package br.com.licenca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.licenca.service.LicencaGerenciador;
 
@EnableScheduling
@Component
public class GerenciadorValidadeContratacao {
    @Autowired
    LicencaGerenciador licencaGerenciador;
    
    @Scheduled(fixedDelay = 10 * 1000) //método executado de 10 em 10 segundos
    public void GerenciarValidadeContratacao() {
        licencaGerenciador.gerenciarValidade();
    }
}
