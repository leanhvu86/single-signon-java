package com.tsolution.sso.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Translator {
	private MessageSource messageSource;

	@Autowired
	private Translator(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String toLocale(String msgCode, String lang) {
		Locale locale = new Locale(lang);
		return this.messageSource.getMessage(msgCode, null, locale);
	}

	public String toLocale(String msgCode) {
		Locale locale = LocaleContextHolder.getLocale();
		return this.messageSource.getMessage(msgCode, null, locale);
	}

	public String toLocaleByFormatString(String msgCode, Object... msg) {
		Locale locale = LocaleContextHolder.getLocale();
		return String.format(this.messageSource.getMessage(msgCode, null, locale), msg);
	}
}
