<%--@elvariable id="actionBean" type="able.stripes.AbleActionBean"--%>
<%--@elvariable id="formId" type="java.lang.String"--%>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jspf" %>
<%@ attribute name="name" required="true" type="java.lang.String" %>
<form:raw-form-label name="${name}"/>
<form:raw-form-field name="${name}">
    <jsp:doBody/>
</form:raw-form-field>
