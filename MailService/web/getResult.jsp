<%-- 
    Document   : getResult
    Created on : 2011/5/1, 下午 01:43:55
    Author     : leo
--%>


<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<% String ContextPath = request.getContextPath(); %>
<%
            List resultList = (List) request.getAttribute("resultList");
%>

<list>
<%
    if ( null != resultList ) {
        for(int i = 0 ; i< resultList.size() ; i++){
            out.println("<result>");
            HashMap<String, String> resultHM = (HashMap)resultList.get(i);
            int size = resultHM.keySet().size();
            for (String key : resultHM.keySet()) {
                out.print("<"+key+">");
                out.print( resultHM.get(key) );
                out.println("</"+key+">");
            }
            out.println("</result>");
        }
    }
%>
</list>
