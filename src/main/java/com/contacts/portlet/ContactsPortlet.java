package com.contacts.portlet;


import com.contacts.constants.ContactsPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DefaultLayoutPrototypesUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;



/**
 * @author maild
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/view.jsp",
			"javax.portlet.name=" + ContactsPortletKeys.Contacts,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)

	public class ContactsPortlet extends MVCPortlet {
	
	@Override
	public void doView(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {
		
		ThemeDisplay themeDisplay =(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		System.out.println(themeDisplay.getLayout());

			try {
				LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)themeDisplay.getLayout().getLayoutType();
				layoutTypePortlet.addPortletId(themeDisplay.getUserId(), themeDisplay.getPortletDisplay().getId());
				//String layoutPrototypeId = DefaultLayoutPrototypesUtil.addPortletId(themeDisplay.getLayout(), themeDisplay.getPortletDisplay().getId(), themeDisplay.getPortletDisplay().getColumnId());
				System.out.println(layoutTypePortlet.addPortletId(themeDisplay.getUserId(), themeDisplay.getPortletDisplay().getId()));
				LayoutPrototype pageTemplate = LayoutPrototypeLocalServiceUtil.getLayoutPrototype(96184);
				String templateUuid = pageTemplate.getUuid();
				
				ServiceContext serviceContext = new ServiceContext();
				serviceContext.setAttribute("layoutPrototypeUuid", templateUuid);
				serviceContext.setAttribute("layoutPrototypeLinkEnabled", true);
				
				Layout myPage = LayoutLocalServiceUtil.addLayout(themeDisplay.getLayout().getUserId(), themeDisplay.getLayout().getGroupId(), false, 
				        0, "My Page", "Products", "My sample page", 
				        LayoutConstants.TYPE_PORTLET, false, "/myPage", serviceContext);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		super.doView(request, response);
	}
	}