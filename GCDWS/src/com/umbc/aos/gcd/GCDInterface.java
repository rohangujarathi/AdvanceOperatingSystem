package com.umbc.aos.gcd;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface GCDInterface {
	@WebMethod
	int gcdOfTwoNumbers(int a, int b);
}
