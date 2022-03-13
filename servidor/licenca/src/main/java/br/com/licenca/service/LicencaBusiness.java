package br.com.licenca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.licenca.ConfigValores;
import br.com.licenca.entity.ContratacaoLicenca;
import br.com.licenca.exception.PreConditionFailedException;
import static br.com.licenca.enums.StatusContratacaoLicenca.DISPONIVEL;

@Component
public class LicencaBusiness {
    @Autowired
    private LicencaGerenciador licencaGerenciador;
    @Autowired
    private ConfigValores config;

    public String solicitarLicenca(Integer usuarioId) throws PreConditionFailedException {
        validarUsuarioExiste(usuarioId);

        return licencaGerenciador.obterLicenca(usuarioId);
    }   
    public void renovarLicenca(Integer usuarioId, String codigoLicenca) throws PreConditionFailedException {
        validarUsuarioExiste(usuarioId);
        validarLicencaExiste(codigoLicenca);
        validarUsuarioContratouLicenca(usuarioId, codigoLicenca);   
        licencaGerenciador.renovarLicenca(codigoLicenca);
    }   
    public void devolverLicenca(Integer usuarioId, String codigoLicenca) throws PreConditionFailedException {
        validarUsuarioExiste(usuarioId);
        validarLicencaExiste(codigoLicenca);
        validarUsuarioContratouLicenca(usuarioId, codigoLicenca);   
        licencaGerenciador.devolverLicenca(codigoLicenca);
    }   

    private void validarUsuarioExiste(Integer usuarioId) throws PreConditionFailedException {
        if(!config.getIdUsuariosExistentes().contains(usuarioId)) {
            throw new PreConditionFailedException("Usuário " + usuarioId + " não existe");
        }
    }   
    private void validarLicencaExiste(String codigoLicenca) throws PreConditionFailedException {
        if(!config.getCodigoLicencasExistentes().contains(codigoLicenca)) {
            throw new PreConditionFailedException("Licença " + codigoLicenca + " não existe");
        }
    }   
    private void validarUsuarioContratouLicenca(Integer usuarioId, String codigoLicenca) throws PreConditionFailedException {
        ContratacaoLicenca contratacao = licencaGerenciador.getLicencas().get(codigoLicenca);
        if(contratacao.getStatus().equals(DISPONIVEL) || !usuarioId.equals(contratacao.getContratante().getId())) {
            throw new PreConditionFailedException("Usuário " + usuarioId + " não está com a licença " + codigoLicenca +  " contratada atualmente");
        }
    }

}
