/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gb4w20.gb4w20.filters;

import com.gb4w20.gb4w20.backingbeans.UserSessionBean;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter checks if LoginBean has loginIn property set to true. If it is not set
 * then request is being redirected to the login.xhml page.
 *
 * @author itcuties http://www.itcuties.com/j2ee/jsf-2-login-filter-example/
 * @author Jeffrey Boisvert
 * 
 * Changed to annotation from web.xml but uses mapping from the web.xml
 *
 */
@WebFilter(filterName = "ManagerFilter")
public class ManagerFilter implements Filter {
    
     private final static Logger LOG = LoggerFactory.getLogger(ManagerFilter.class);

    @Inject
    private UserSessionBean userSessionBean;

    /**
     * Checks if user is logged in as a manager. 
     * If not it redirects to the login.xhtml page.
     * 
     * @param request
     * @param response
     * @param chain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     * @author Jeffrey Boisvert however heavily based on Ken Fogel's LoginFilter at https://gitlab.com/omniprof/JSFSample30Filter.git
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        LOG.info("In the manager filter");
        if (!userSessionBean.isLoggedInManager()) {
            LOG.info("User not logged in as a manager");
            String contextPath = ((HttpServletRequest) request)
                    .getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath
                    + "/login.xhtml");
        } else {
            LOG.info("Manager user logged in: "
                    + userSessionBean.getUser().getEmail());
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Nothing to do here!
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nothing to do here but must be overloaded
    }
    
}
