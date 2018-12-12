package com.umbc.aos.subtract;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface SubtractionInterface {
	@WebMethod
	int subtractNumbers(int a, int b);
}
