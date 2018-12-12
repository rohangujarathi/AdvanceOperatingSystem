package com.umbc.aos.multiply;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface MultiplicationInterface {
	@WebMethod
	int multiplyNumbers(int a, int b);
}
