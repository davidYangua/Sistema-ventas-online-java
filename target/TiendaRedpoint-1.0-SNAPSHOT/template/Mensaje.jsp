<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%

    if (request.getSession().getAttribute("error") != null) {
%>
<div class="alert alert-danger alert-dismissible fade show" role="alert">
    <%=request.getSession().getAttribute("error")%>
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>
<%
    }
%>

<%

    if (request.getSession().getAttribute("success") != null) {
%>
<div class="alert alert-success alert-dismissible fade show" role="alert">
    <%=request.getSession().getAttribute("success")%>
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>
<%
    }

request.getSession().setAttribute("error", null);
request.getSession().setAttribute("success", null);
%>