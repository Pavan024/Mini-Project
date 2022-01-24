package com.ecommerce.admin.header;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

/**
 * Header Generator Class for Different HTTP methods
 * @author saipavan
 */
@Service
public class HeaderGenerator {
	
	/**
	 * This method is for Success Get Method
	 * @return HTTP Header
	 */
	public HttpHeaders getHeadersForSuccessGetMethod() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
		return httpHeaders;
	}
	
	/**
	 * This method is for Errors
	 * @return HTTP Header
	 */
	public HttpHeaders getHeadersForError() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/problem+json; charset=UTF-8");
		return httpHeaders;
	}
	
	/**
	 * This method is for Success Post Method
	 * @return HTTP Header
	 */
	public HttpHeaders getHeadersForSuccessPostMethod(HttpServletRequest request, String string) {
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			httpHeaders.setLocation(new URI(request.getRequestURI() + "/" + string));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
		return httpHeaders;
	}
}