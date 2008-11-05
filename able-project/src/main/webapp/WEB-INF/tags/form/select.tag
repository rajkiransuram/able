<%@ tag trimDirectiveWhitespaces="true" %>
<%@ include file="form-field-header.jspf" %>
<form:raw-form-entry name="${name}">
    <s:select id="${id}" name="${name}" class="${fieldClass}">
        <jsp:doBody/>
    </s:select>
</form:raw-form-entry>