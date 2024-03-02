package com.sebasgoy.util;

import jakarta.servlet.http.HttpServletRequest;

public class Tools {

	
	public static String paginaAnterior (HttpServletRequest request) {
		return request.getHeader("referer");
	}
	
}
