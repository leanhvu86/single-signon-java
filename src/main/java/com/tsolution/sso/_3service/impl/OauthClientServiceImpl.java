package com.tsolution.sso._3service.impl;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tsolution.sso._1entities.OauthClientDetails;
import com.tsolution.sso._2repository.OauthClientRepository;
import com.tsolution.sso._3service.OauthClientService;
import com.tsolution.sso.exceptions.BusinessException;
import com.tsolution.sso.utils.Translator;

@Service
@Transactional
public class OauthClientServiceImpl implements OauthClientService {

	private static final Logger log = LogManager.getLogger(OauthClientServiceImpl.class);

	@Autowired
	private OauthClientRepository oauthClientRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Translator translator;

	@Override
	public ResponseEntity<Object> getAllClientId(String filterShowOnClient) {
		return new ResponseEntity<>(this.oauthClientRepository.getAllClientId(filterShowOnClient), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getOne(String clientId) {
		Optional<OauthClientDetails> res = this.oauthClientRepository.findById(clientId);
		if (res.isPresent()) {
			String secret = "";
			OauthClientServiceImpl.log.info(secret);
		}

		return new ResponseEntity<>(this.oauthClientRepository.findById(clientId), HttpStatus.OK);

	}

	@Override
	public ResponseEntity<Object> getAll() {
		return new ResponseEntity<>(this.oauthClientRepository.findAll(), HttpStatus.OK);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> create(OauthClientDetails oauthClientDetails) throws BusinessException {
		Optional<OauthClientDetails> oauthClientEntityTemp = this.oauthClientRepository
				.findById(oauthClientDetails.getClientId());
		if (!oauthClientEntityTemp.isPresent()) {
			String encodedClientSecret = this.passwordEncoder.encode(oauthClientDetails.getClientSecret());
			oauthClientDetails.setClientSecret(encodedClientSecret);
			return new ResponseEntity<>(this.oauthClientRepository.save(oauthClientDetails), HttpStatus.OK);
		}
		throw new BusinessException(this.translator.toLocale("client.id.is.existed"));

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> update(String clientId, OauthClientDetails oauthClientEntityInput)
			throws BusinessException {
		Optional<OauthClientDetails> oauthClientDetails = this.oauthClientRepository.findById(clientId);
		if (oauthClientDetails.isPresent()) {
			oauthClientEntityInput.setClientId(clientId);
			return new ResponseEntity<>(this.oauthClientRepository.save(oauthClientEntityInput), HttpStatus.OK);
		}
		throw new BusinessException(this.translator.toLocale("client.id.is.not.existed"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> delete(String clientId) throws BusinessException {
		Optional<OauthClientDetails> oauthClientDetails = this.oauthClientRepository.findById(clientId);
		if (!oauthClientDetails.isPresent()) {
			throw new BusinessException(this.translator.toLocale("client.id.is.not.existed"));
		}
		this.oauthClientRepository.deleteById(clientId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> find(OauthClientDetails oauthClientDetails, Pageable pageable) {
		return new ResponseEntity<>(this.oauthClientRepository.findOauthClients(oauthClientDetails, pageable),
				HttpStatus.OK);
	}

}
