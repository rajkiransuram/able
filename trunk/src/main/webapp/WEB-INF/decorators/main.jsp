<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%--@elvariable id="actionBean" type="able.stripes.AbleActionBean"--%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jspf" %>
<decorator:usePage id="pageContent" />
<fmt:setBundle basename="StripesResources"/><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/DTD/strict.dtd">
<html>
<head>
    <title><decorator:title default="Able"/></title>
    <link rel="stylesheet" type="text/css" href="/styles/main.css"/>
    <script type="text/javascript">
        document.cookie = encodeURIComponent("timezoneOffset") + "=" + (-(new Date()).getTimezoneOffset());
    </script>
    <decorator:head/>
</head>

<body>

<div id="main">

    <div id="header">
        <div id="logo">
            A cool Logo could go here!
        </div>
        <div id="navlinks">
            <c:if test="${actionBean.context.loggedIn}">
                <s:link beanclass="able.stripes.HomeActionBean"><fmt:message key="home"/></s:link>
                |
                <s:link beanclass="able.stripes.LogoutActionBean"><fmt:message key="logout"/></s:link>
            </c:if>
            <c:if test="${!actionBean.context.loggedIn}">
                <s:link beanclass="able.stripes.SignupActionBean"><fmt:message key="signup"/></s:link>
                |
                <s:link beanclass="able.stripes.LoginActionBean"><fmt:message key="login"/></s:link>
            </c:if>
        </div>
    </div>

    <div id="bread_crumbs">
        &nbsp;
        <%
        String crumbs = pageContent.getProperty("page.crumbs");
        if (crumbs != null) {
            out.print(crumbs);
        }
        %>
    </div>

    <div id="content">
        <s:messages/>

        <decorator:body />
    </div>

    <div id="footer">
        &copy; Acme Corp, Inc - All rights reserved
    </div>

</div>

</body>
</html>