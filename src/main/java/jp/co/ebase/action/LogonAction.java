package jp.co.ebase.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.ebase.form.ActionForm;
import jp.co.ebase.form.LogonForm;
import jp.co.ebase.model.MappinModel;

public class LogonAction
{

	public ActionForward execute(MappinModel mappingURL,ActionForm form,HttpServletRequest httpServletRquest,HttpServletResponse httpServletResponse)
	{
		ActionForward forward =null;
		LogonForm logonForm = (LogonForm)form;
		//ActionFormから受信したデータでパスワードチェックを行う
		String rcvID = logonForm.getId();
		String rcvPW = logonForm.getPassword();

		if((rcvID!=null && rcvPW!=null)&&(rcvID.equals("ebase") && rcvPW.equals("ebase")))
		{
			forward = new ActionForward(mappingURL.getSuccess());
		}
		else
		{
			forward = new ActionForward(mappingURL.getInput());
		}

		return forward;

	}

	public LogonForm getForm(HttpServletRequest httpServletRquest)
	{
		LogonForm lf = new LogonForm();
		//ログインID
		String rcvId = httpServletRquest.getParameter("id");
		//ログインパスワード
		String rcvPassword = httpServletRquest.getParameter("password");
		//LogonFormにID/PWをセットする
		lf.setId(rcvId);
		lf.setPassword(rcvPassword);

		return lf;
	}
}
