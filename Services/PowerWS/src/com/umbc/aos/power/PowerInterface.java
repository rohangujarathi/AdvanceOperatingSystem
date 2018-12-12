package com.umbc.aos.power;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface PowerInterface {
	@WebMethod
	double squareNumbers(double a, double b);
}
