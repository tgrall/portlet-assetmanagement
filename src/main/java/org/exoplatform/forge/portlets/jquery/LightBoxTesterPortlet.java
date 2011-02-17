/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.exoplatform.forge.portlets.jquery;



import java.io.IOException;
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
 *
 * @author tgrall
 */
public class LightBoxTesterPortlet  extends GenericPortlet {



   private static final String viewPage = "/WEB-INF/jsp/LightBoxTesterPortlet/view.jsp";
    private static final String editPage = "/WEB-INF/jsp/LightBoxTesterPortlet/edit.jsp";
    private static final String helpPage = "/WEB-INF/jsp/LightBoxTesterPortlet/help.jsp";


    @Override
    protected void doHeaders(RenderRequest request, RenderResponse response) {
        super.doHeaders(request, response);

        // all hardcoded for the tester

        Element jqueryJsFile = response.createElement("script");
        jqueryJsFile.setAttribute("type", "text/javascript");
        jqueryJsFile.setAttribute("src", request.getContextPath() + "/js/jquery/jquery-1.5.min.js");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, jqueryJsFile);

        Element lightboxCssFile = response.createElement("link");
        lightboxCssFile.setAttribute("type", "text/css");
        lightboxCssFile.setAttribute("href", request.getContextPath() + "/css/jquery/balupton-lightbox/jquery.lightbox.min.css");
        lightboxCssFile.setAttribute("rel","stylesheet");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, lightboxCssFile);

        Element colorJsFile = response.createElement("script");
        colorJsFile.setAttribute("type", "text/javascript");
        colorJsFile.setAttribute("src", request.getContextPath() + "/js/jquery/balupton-lightbox/jquery.color.min.js");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, colorJsFile);

        Element lightboxJsFile = response.createElement("script");
        lightboxJsFile.setAttribute("type", "text/javascript");
        lightboxJsFile.setAttribute("src", request.getContextPath() + "/js/jquery/balupton-lightbox/jquery.lightbox.min.js");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, lightboxJsFile);

    }



    @Override
    protected void doEdit(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
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



}
