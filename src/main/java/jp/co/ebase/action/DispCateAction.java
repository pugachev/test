package jp.co.ebase.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.ebase.form.ActionForm;
import jp.co.ebase.form.DispCateForm;
import jp.co.ebase.form.LogonForm;
import jp.co.ebase.model.MappinModel;

public class DispCateAction
{
	public ActionForward execute(MappinModel mappingURL,ActionForm actionform,HttpServletRequest httpServletRquest,HttpServletResponse httpServletResponse)
	{
		ActionForward forward =null;

		//未使用(ダウンキャストしただけ)
		DispCateForm dispCateForm = (DispCateForm)actionform;

		//色々な処理を行う
		forward = new ActionForward(mappingURL.getSuccess());

		return forward;

	}

	public DispCateForm getForm(HttpServletRequest httpServletRquest)
	{
		DispCateForm dc = new DispCateForm();

		return dc;
	}
}
