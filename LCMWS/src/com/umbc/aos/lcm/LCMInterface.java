package com.umbc.aos.lcm;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface LCMInterface {
	@WebMethod
	int lcmOfTwoNumbers(int a, int b);
}
