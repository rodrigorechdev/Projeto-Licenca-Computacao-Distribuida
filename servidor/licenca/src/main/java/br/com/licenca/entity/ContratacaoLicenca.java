package br.com.licenca.entity;

import java.time.LocalDateTime;

import br.com.licenca.enums.StatusContratacaoLicenca;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import static br.com.licenca.enums.StatusContratacaoLicenca.DISPONIVEL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContratacaoLicenca {
    private Usuario contratante;
    private LocalDateTime dataContratacao;
    private StatusContratacaoLicenca status = DISPONIVEL;
}
