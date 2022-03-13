package br.com.licenca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.licenca.entity.response.LicencaResponse;
import br.com.licenca.exception.PreConditionFailedException;
import br.com.licenca.service.LicencaBusiness;

@RestController
@CrossOrigin(origins = "*")
public class LicencaController {
    @Autowired
    LicencaBusiness licencaBusiness;

    @RequestMapping(value = "/licenca", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LicencaResponse> solicitarLicenca(@RequestHeader Integer usuarioId) throws PreConditionFailedException {
    	try {
            String licencaCodigo = licencaBusiness.solicitarLicenca(usuarioId);
            return new ResponseEntity<LicencaResponse>(new LicencaResponse(licencaCodigo), HttpStatus.OK);
        } catch(PreConditionFailedException e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/licenca/renovar", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> renovarLicenca(@RequestHeader Integer usuarioId, @RequestHeader String licencaCodigo) {
    	try {
            licencaBusiness.renovarLicenca(usuarioId, licencaCodigo);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch(PreConditionFailedException e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/licenca/devolver", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> devolverLicenca(@RequestHeader Integer usuarioId, @RequestHeader String licencaCodigo) {
        try {
            licencaBusiness.devolverLicenca(usuarioId, licencaCodigo);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch(PreConditionFailedException e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, e.getMessage(), e);
        }
    }
}
