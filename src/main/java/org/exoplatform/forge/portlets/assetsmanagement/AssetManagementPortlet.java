/*
 * Copyright (C) 2003-20011 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */

package org.exoplatform.forge.portlets.assetsmanagement;

import java.io.IOException;
import java.io.PrintWriter;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.w3c.dom.Element;



/**
 * Generic portlet that manage and show Javascript extension based on the preferences
 * of the portlet
 *
 * Note: the view mode is just an empty page since the content is a set of resources
 * added in the HTML header in the doHeader method
 *
 * @author tugdual.grall@exoplatform.com
 */
public class AssetManagementPortlet extends GenericPortlet {

    private static final String viewPage = "/WEB-INF/jsp/AssetManagementPortlet/view.jsp";
    private static final String editPage = "/WEB-INF/jsp/AssetManagementPortlet/edit.jsp";
    private static final String helpPage = "/WEB-INF/jsp/AssetManagementPortlet/help.jsp";


    @Override
    protected void doHeaders(RenderRequest request, RenderResponse response) {
        super.doHeaders(request, response);



        {
        Element jsFile = response.createElement("script");
        jsFile.setAttribute("type", "text/javascript");
        jsFile.setAttribute("src", request.getContextPath() + "/js/jquery/jquery-1.5.min.js");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, jsFile);
        }



        {
        Element cssFile = response.createElement("link");
        cssFile.setAttribute("type", "text/css");
        cssFile.setAttribute("href", request.getContextPath() + "/css/jquery/ui/smoothness/jquery-ui-1.8.9.custom.css");
        cssFile.setAttribute("rel","stylesheet");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, cssFile);
        }

        {
        Element jsFile = response.createElement("script");
        jsFile.setAttribute("type", "text/javascript");
        jsFile.setAttribute("src", request.getContextPath() + "/js/jquery/ui/jquery-ui-1.8.9.custom.min.js");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, jsFile);
        }


        // calculate all the value we want to add in the HTML Header from the preferences
        {
        Element cssFile = response.createElement("link");
        cssFile.setAttribute("type", "text/css");
        cssFile.setAttribute("href", request.getContextPath() + "/css/jquery/balupton-lightbox/jquery.lightbox.min.css");
        cssFile.setAttribute("rel","stylesheet");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, cssFile);
        }

        {
        Element jsFile = response.createElement("script");
        jsFile.setAttribute("type", "text/javascript");
        jsFile.setAttribute("src", request.getContextPath() + "/js/jquery/balupton-lightbox/jquery.color.min.js");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, jsFile);
        }
        {
        Element jsFile = response.createElement("script");
        jsFile.setAttribute("type", "text/javascript");
        jsFile.setAttribute("src", request.getContextPath() + "/js/jquery/balupton-lightbox/jquery.lightbox.min.js");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, jsFile);
        }


    }



    @Override
    protected void doEdit(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {

        // due to bug https://issues.jboss.org/browse/GTNPORTAL-1387
        processRequest(request, response);
    }

    @Override
    protected void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        processRequest(request, response);
    }

    /**
     * Method use to manage the various mode
     *
     * @param request
     * @param response
     * @throws PortletException
     * @throws IOException
     */
    private void processRequest(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {

        PortletMode mode = request.getPortletMode();
        String pageToShow = viewPage;

        if (mode.equals(PortletMode.VIEW)) {
            pageToShow = viewPage;
        } else if (mode.equals(PortletMode.EDIT)) {
            pageToShow = editPage;
        } else if (mode.equals(PortletMode.HELP)) {
            pageToShow = helpPage;
        }
        // set response content-type to value in request
        response.setContentType(request.getResponseContentType());
        if (getPortletContext().getResourceAsStream(pageToShow) != null) {
            try {
                // dispatch edit request to edit.jsp
                PortletRequestDispatcher dispatcher = getPortletContext().getRequestDispatcher(pageToShow);
                dispatcher.include(request, response);
            } catch (IOException e) {
                throw new PortletException("Exception in Portlet", e);
            }
        } else {
            throw new PortletException("Exception JSP is missing.");
        }

    }

    

    /**
     * @see javax.portlet.GenericPortlet#processAction(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
     **/
    public void processAction(ActionRequest request, ActionResponse response)
            throws PortletException, IOException {


        if (request.getPortletMode().equals(PortletMode.EDIT)) {

            // retrieve parameters
            String time = request.getParameter("target");

            // demonstrates portlet preferences storage
            PortletPreferences pref = request.getPreferences();
            pref.setValue("target", time);
            pref.store();

            // back to view mode
            response.setPortletMode(PortletMode.VIEW);
        }
    }

}
