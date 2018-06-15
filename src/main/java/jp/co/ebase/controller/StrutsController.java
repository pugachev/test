package jp.co.ebase.controller;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import jp.co.ebase.action.ActionForward;
import jp.co.ebase.action.DispCateAction;
import jp.co.ebase.action.LogonAction;
import jp.co.ebase.form.ActionForm;
import jp.co.ebase.form.DispCateForm;
import jp.co.ebase.form.LogonForm;

import jp.co.ebase.model.MappinModel;
import jp.co.ebase.parser.XmlReader;

@Controller
public class StrutsController
{

	@Inject
	ResourceLoader resourceLoader;

	static MappinModel searchURL( List<MappinModel>mList,String url)
	{
		MappinModel mm = new MappinModel();
		for(int i=0;i<mList.size();i++)
		{
			if(mList.get(i).getPath().equals(url))
			{
				return mList.get(i);
			}
		}

		return null;
	}

	private static List<MappinModel> mList;


    @ModelAttribute
    public ActionForm setUpActionForm()
    {
    	ActionForm form = new ActionForm();
        return form;
    }

    @ModelAttribute
    public LogonForm setUpLogonForm()
    {
    	LogonForm form = new LogonForm();
        return form;
    }

    @ModelAttribute
    public DispCateForm setUpDispCateForm()
    {
    	DispCateForm form = new DispCateForm();
        return form;
    }

	@RequestMapping(value = "/{*}",method = {RequestMethod.GET,RequestMethod.POST})
	public String strutsController(@RequestParam(name="file",required=false) MultipartFile file,ActionForm actionform,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws  SAXException, IOException, ParserConfigurationException
	{
		ActionForm formclass=null;

		String rcvId = httpServletRequest.getParameter("id");
		String rcvPw =  httpServletRequest.getParameter("password");

		//mapping.xmlからURLと遷移先の取得
		if(mList==null || mList.size()>0)
		{
			Resource resource = resourceLoader.getResource("classpath:" + "mapping.xml");
		    File xmlFile = resource.getFile();
			XmlReader reader = new XmlReader();
			mList = reader.domRead(xmlFile.getAbsolutePath());
		}

		//{*}で受信したリクエストURLをここで取得する
		String rcvUrl = httpServletRequest.getRequestURI();
	    int index = rcvUrl.indexOf("ebaseTest");
	    index += "ebaseTest".length();
	    String targetUrl = rcvUrl.substring(index);

	    //取得したURLが存在するか確認する。
	    //存在する場合はリストから該当するデータを取得する
	    MappinModel  mapping = searchURL(mList,targetUrl);
	    if(mapping==null)
	    {
	    	return "error";
	    }

	    ActionForward af = null;
        // リフレクション
        try
        {
            // Actionクラスの取得
            Class<?> c = Class.forName(mapping.getType());

            // Actionクラスのインスタンスの生成
            Object myObj = c.newInstance();

            //getFormメソッドの取得
            Method mGetForm =  c.getMethod("getForm", HttpServletRequest.class);

            // executeメソッドの取得
            Method mExecute = c.getMethod("execute", MappinModel.class,ActionForm.class,HttpServletRequest.class,HttpServletResponse.class);

            //getFormメソッドの実行
            formclass = (ActionForm) mGetForm.invoke(myObj, httpServletRequest);

            //executeメソッドの実行
           af = (ActionForward) mExecute.invoke(myObj,mapping,formclass,httpServletRequest,httpServletResponse);
        }
        catch(ReflectiveOperationException e)
        {
            e.printStackTrace();
        }

        return af.getForward();
	}
}