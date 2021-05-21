package com.tsolution.sso._4controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tsolution.sso._1entities.OauthClientDetails;
import com.tsolution.sso._3service.OauthClientService;
import com.tsolution.sso.exceptions.BusinessException;
import com.tsolution.sso.utils.Translator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/oauthClient")
@Api(tags = "Client Rest Controller")
public class OauthClientController {

	@Autowired
	private OauthClientService oauthClientService;

	@Autowired
	private Translator translator;

	@GetMapping("/{id}")
	@ApiOperation(value = "Get one client entity", response = ResponseEntity.class)
	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	public ResponseEntity<Object> getOne(@PathVariable("id") String clientId) {
		return this.oauthClientService.getOne(clientId);
	}

	@GetMapping("/find")
	@ApiOperation(value = "Find Client with params", response = ResponseEntity.class)
	@ApiParam(name = "text")
	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	public ResponseEntity<Object> findAll(@RequestParam(value = "text", required = false) String text,
			@RequestParam(value = "pageNumber", required = true) Integer pageNumber,
			@RequestParam(value = "pageSize", required = true) Integer pageSize) {
		text = text == null ? "" : text;
		OauthClientDetails oauthClientDetails = new OauthClientDetails();
		oauthClientDetails.setClientId(text);
		oauthClientDetails.setScope(text);
		oauthClientDetails.setAuthorities(text);

		return this.oauthClientService.find(oauthClientDetails, PageRequest.of(pageNumber - 1, pageSize));
	}

	@GetMapping("/getAll")
	@ApiOperation(value = "get all Client", response = ResponseEntity.class)
	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	public ResponseEntity<Object> getAll() {
		return this.oauthClientService.getAll();
	}

	@GetMapping("/getClientIds")
	@ApiOperation(value = "get all Client Ids", response = ResponseEntity.class)
	@ApiResponses(value = @ApiResponse(code = 200, message = "OK"))
	public ResponseEntity<Object> getClientId(@RequestParam(required = false) String filterShowOnClient) {
		return this.oauthClientService.getAllClientId(filterShowOnClient);
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Optional<OauthClientDetails> oOauthClientEntity)
			throws BusinessException {
		if (oOauthClientEntity.isPresent()) {
			return this.oauthClientService.create(oOauthClientEntity.get());
		}
		throw new BusinessException(this.translator.toLocale(BusinessException.COMMON_INPUT_INFO_INVALID));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable("id") String clientId,
			@RequestBody Optional<OauthClientDetails> oOauthClientEntity) throws BusinessException {
		if (oOauthClientEntity.isPresent()) {
			return this.oauthClientService.update(clientId, oOauthClientEntity.get());
		}
		throw new BusinessException(this.translator.toLocale(BusinessException.COMMON_INPUT_INFO_INVALID));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") String clientId) throws BusinessException {
		return this.oauthClientService.delete(clientId);
	}
}
