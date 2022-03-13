package br.com.licenca.service;

import static br.com.licenca.enums.StatusContratacaoLicenca.DISPONIVEL;
import static br.com.licenca.enums.StatusContratacaoLicenca.EM_USO;
import static br.com.licenca.enums.StatusContratacaoLicenca.EM_VENCIMENTO;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.licenca.ConfigValores;
import br.com.licenca.entity.ContratacaoLicenca;
import br.com.licenca.entity.Usuario;
import br.com.licenca.exception.PreConditionFailedException;
import lombok.Getter;

@Component
@Getter
public class LicencaGerenciador {
    private HashMap<String, ContratacaoLicenca> licencas = new HashMap<>();
    private ConfigValores config;

    @Autowired
    private void preencheLicencas(ConfigValores config) {
        this.config = config;        
        config.getCodigoLicencasExistentes().forEach((codigoLicenca) -> licencas.put(codigoLicenca, new ContratacaoLicenca()));
    }

    public String obterLicenca(Integer idUsuario) throws PreConditionFailedException {
        String codigoLicencaDisponivel = encontrarLicencaDisponivel();
        if(codigoLicencaDisponivel == null) {
            System.out.println("Nenhuma licença disponível encontrada no momento!");
            throw new PreConditionFailedException("Nenhuma licença disponível no momento");
        } else {
            licencas.put(codigoLicencaDisponivel, new ContratacaoLicenca(new Usuario(idUsuario), LocalDateTime.now(), EM_USO));
            System.out.println("Contratação da licença " + codigoLicencaDisponivel + " foi realizada com sucess pelo usuário " + idUsuario + " status da contratação definido como EM_USO!");
            return codigoLicencaDisponivel;
        }
    }
    
    public void renovarLicenca(String codigoLicenca) {
        ContratacaoLicenca contratacao = licencas.get(codigoLicenca);
        contratacao.setDataContratacao(LocalDateTime.now());
        licencas.put(codigoLicenca, contratacao);
        System.out.println("Contratação da licenca " + codigoLicenca + " renovada com sucesso!");
    }
    
    public void devolverLicenca(String codigoLicenca) {
        licencas.put(codigoLicenca, new ContratacaoLicenca());//reseta a contratação pra DISPONÍVEL
        System.out.println("Licenca " + codigoLicenca + " devolvida com sucesso!");
    }
    
    /**
     * existe alguma licença EM_USO que passou do tempo de validade, 
     * caso tenha passado o estado da contratação é alterado para EM_VENCIMENTO.
     * Ele também analisa se contratações com estados EM_VENCIMENTO já venceram a mais 
     * de 2 minutos. Caso sim, o estado é alterado da contratacao passa a ser DISPONIVEL.
     */
    public void gerenciarValidade() {
        licencas.forEach((codigoLicenca, contratacao) -> {
                if(!contratacao.getStatus().equals(DISPONIVEL)) {
                    long minutosDesdeContratacao = ChronoUnit.MINUTES.between(contratacao.getDataContratacao(), LocalDateTime.now());
                    if(contratacao.getStatus().equals(EM_USO) && minutosDesdeContratacao >= 1) {
                        contratacao.setStatus(EM_VENCIMENTO);
                        System.out.println("Status de contratação da licenca " + codigoLicenca + " alterado para EM_VENCIMENTO, pois o tempo de 1 minuto de ativação foi alcançado!");
                    }
                    if(contratacao.getStatus().equals(EM_VENCIMENTO) && minutosDesdeContratacao >= 3){
                        contratacao.setStatus(DISPONIVEL);
                        System.out.println("Status de contratação da licenca " + codigoLicenca + " alterado para DISPONIVEL, pois o tempo de vencimento de 2 minutos foi alcançado!");
                    }
                }
            }
        );
    }

    private String encontrarLicencaDisponivel() {
        for(Map.Entry<String, ContratacaoLicenca> licenca : licencas.entrySet()) {
            String codigoLicenca = licenca.getKey();
            ContratacaoLicenca contratacaoLicenca = licenca.getValue();
            if(contratacaoLicenca.getStatus().equals(DISPONIVEL)) {
                System.out.println("Licença disponível encontrada, sua chave é" + codigoLicenca + " !");
                return codigoLicenca;
            }
        }
        
        return null;
    }
}
