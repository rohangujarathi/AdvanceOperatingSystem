package com.umbc.aos.cube;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface CubeInterface {
	@WebMethod
	int cubeNumbers(int a);
}
