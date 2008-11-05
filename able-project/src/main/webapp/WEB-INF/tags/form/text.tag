<%@ tag trimDirectiveWhitespaces="true" %>
<%@ include file="form-field-header.jspf" %>
<%@ attribute name="formatType" required="false" type="java.lang.String" %>
<form:raw-form-entry name="${name}">
    <s:text id="${id}" name="${name}" class="${fieldClass}" formatType="${formatType}"/>
</form:raw-form-entry>