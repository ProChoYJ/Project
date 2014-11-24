package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import TestURLBean.URLBean;

public class ScreenAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		URLBean ub = new URLBean();
		ub.setnScreen(Integer.parseInt(request.getParameter("count")));
		ub.setTvURL(request.getParameterValues("ckb"));
		request.setAttribute("testAir", ub);
		return "Main.jsp";
	}

}
