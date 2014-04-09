package com.digitalrpg.web.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalrpg.domain.model.User;
import com.digitalrpg.web.controller.model.MessageVO;
import com.digitalrpg.web.service.MessageService;

@Controller
@RequestMapping("/messages")
public class MessageController {

	SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
	
	@Autowired
	private MessageService messageService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<MessageVO>> getUserMessages(
			Principal principal, @RequestHeader(required = false, value = "If-Modified-Since") String ifModifiedSince) throws ParseException {
		if (principal == null)
			return new ResponseEntity<Collection<MessageVO>>(HttpStatus.OK);
		User user = (User) ((AbstractAuthenticationToken) principal)
				.getPrincipal();

		Collection<MessageVO> userMessages = messageService
				.getUserMessages(user, StringUtils.isNotEmpty(ifModifiedSince) ? format.parse(ifModifiedSince) : null);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add("Last-Modified", format.format(new Date()));
		if(userMessages != null) {
			return new ResponseEntity<Collection<MessageVO>>(userMessages, headers,
				HttpStatus.OK);
		} else {
			return new ResponseEntity<>(headers, HttpStatus.NOT_MODIFIED);
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteMessage(@PathVariable Long id) {
		messageService.deleteMessage(id);
	}

}
