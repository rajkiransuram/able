<%@ tag trimDirectiveWhitespaces="true" %>
<%@ include file="form-field-header.jspf" %>
<form:raw-form-entry name="${name}">
    <s:file id="${id}" name="${name}" class="${fieldClass}"/>
</form:raw-form-entry>