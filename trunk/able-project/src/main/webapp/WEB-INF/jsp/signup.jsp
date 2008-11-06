<%@ include file="/WEB-INF/jsp/include/page_header.jspf" %>
<html>
<head>
    <title><fmt:message key="signup"/></title>
</head>

<body>

<form:form beanclass="com.acme.stripes.SignupActionBean" title="Signing Up is Easy!">
    <form:multi-column-form-row>
        <jsp:attribute name="field1">
            <form:text name="user.firstName"/>
        </jsp:attribute>
        <jsp:attribute name="field2">
            <form:text name="user.lastName"/>
        </jsp:attribute>
    </form:multi-column-form-row>
    <form:text name="user.email"/>
    <form:multi-column-form-row>
        <jsp:attribute name="field1">
            <form:password name="user.password"/>
        </jsp:attribute>
        <jsp:attribute name="field2">
            <form:password name="passwordAgain"/>
        </jsp:attribute>
    </form:multi-column-form-row>
    <form:buttons>
        <s:submit name="signup"/>
    </form:buttons>
</form:form>

</body>
</html>