package com.clsaa.ms.hermes.result.exception;

import org.springframework.http.HttpStatus;

/**
 * 未授权,此类异常将被转换为Http 401
 *
 * @author 任贵杰
 */
public class UnauthorizedException extends AbstractResultException {

	/**
	 * @param code    业务错误码
	 * @param message 业务提示信息
	 */
	public UnauthorizedException(int code, String message) {
		super(code, message, HttpStatus.UNAUTHORIZED);
	}


	/**
	 * 业务错误码为 {@link com.clsaa.ms.hermes.result.RestResult.Codes#UNKNOWN}
	 *
	 * @param message 业务提示信息
	 */
	public UnauthorizedException(String message) {
		super(message, HttpStatus.UNAUTHORIZED);
	}


}