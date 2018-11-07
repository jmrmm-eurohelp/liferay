package com.startup.portlet;

import com.startup.constants.StartupPortletKeys;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourceLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
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
		"javax.portlet.name=" + StartupPortletKeys.Startup,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)

public class StartupPortlet extends MVCPortlet {
	
	private LayoutLocalServiceUtil layoutLocalService;
	private ResourceLocalServiceUtil resourceLocalService;
	
	@Override
	public void doView(RenderRequest request, RenderResponse response) throws IOException, PortletException {

		ThemeDisplay themeDisplay =(ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);

		try {
			
			ServiceContext serviceContext = new ServiceContext();

			Layout layout = LayoutLocalServiceUtil.addLayout(themeDisplay.getLayout().getUserId(), themeDisplay.getLayout().getGroupId(), false, 
			        0, "Homepage", "Homepage", "Homepage", 
			        LayoutConstants.TYPE_PORTLET, false, "/home", serviceContext);

            layout.setTypeSettings("layout-template-id=1_column");
            updateLayout(layout);
			addPortletId(layout, "homepage", "column-1");
			
			layout = LayoutLocalServiceUtil.addLayout(themeDisplay.getLayout().getUserId(), themeDisplay.getLayout().getGroupId(), false, 
			        0, "About Us", "About Us", "About Us", 
			        LayoutConstants.TYPE_PORTLET, false, "/aboutus", serviceContext);

			layout.setTypeSettings("layout-template-id=1_column");
            updateLayout(layout);
			addPortletId(layout, "aboutus", "column-1");

			layout = LayoutLocalServiceUtil.addLayout(themeDisplay.getLayout().getUserId(), themeDisplay.getLayout().getGroupId(), false, 
			        0, "Production", "Production", "Production", 
			        LayoutConstants.TYPE_PORTLET, false, "/production", serviceContext);

			layout.setTypeSettings("layout-template-id=1_column");
            updateLayout(layout);
			addPortletId(layout, "production", "column-1");
			
			layout = LayoutLocalServiceUtil.addLayout(themeDisplay.getLayout().getUserId(), themeDisplay.getLayout().getGroupId(), false, 
			        0, "Products", "Products", "Products", 
			        LayoutConstants.TYPE_PORTLET, false, "/products", serviceContext);

			layout.setTypeSettings("layout-template-id=1_column");
            updateLayout(layout);
			addPortletId(layout, "products", "column-1");
			
			layout = LayoutLocalServiceUtil.addLayout(themeDisplay.getLayout().getUserId(), themeDisplay.getLayout().getGroupId(), false, 
			        0, "Social Responsibility", "Social Responsibility", "Social Responsibility", 
			        LayoutConstants.TYPE_PORTLET, false, "/socialresponsibility", serviceContext);

			layout.setTypeSettings("layout-template-id=1_column");
            updateLayout(layout);
			addPortletId(layout, "socialresponsibility", "column-1");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.doView(request, response);

	}
	
	public String addPortletId(Layout layout, String portletId, String columnId) throws Exception {
		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) layout.getLayoutType();
		portletId = layoutTypePortlet.addPortletId(layout.getUserId(), portletId, columnId, -1, false);

		addResources(layout, portletId);
		updateLayout(layout);

		return portletId;
	}

	/**
	 * Adapted from original SevenCogs sample code
	 * 
	 * @param layout
	 * @param portletId
	 * @throws Exception
	 */

	@SuppressWarnings("static-access")
	public void addResources(Layout layout, String portletId) throws Exception {
		@SuppressWarnings("deprecation")
		String rootPortletId = PortletConstants.getRootPortletId(portletId);
		String portletPrimaryKey = PortletPermissionUtil.getPrimaryKey(layout.getPlid(), portletId);

		resourceLocalService.addResources(layout.getCompanyId(), layout.getGroupId(), layout.getUserId(), rootPortletId,
				portletPrimaryKey, true, true, true);
	}

	/**
	 * Adapted from original SevenCogs sample code
	 * 
	 * @param layout
	 * @throws Exception
	 */

	@SuppressWarnings("static-access")
	protected void updateLayout(Layout layout) throws Exception {
		layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}
	
}