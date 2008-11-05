<%@ include file="/WEB-INF/jsp/include/page_header.jspf" %>
<html>
<head>
    <title><fmt:message key="login"/></title>
</head>

<body>

<form:form beanclass="able.stripes.LoginActionBean" title="Login">
    <form:text name="user.email"/>
    <form:password name="user.password"/>
    <form:buttons>
        <s:submit name="login"/>
    </form:buttons>
</form:form>

</body>
</html>