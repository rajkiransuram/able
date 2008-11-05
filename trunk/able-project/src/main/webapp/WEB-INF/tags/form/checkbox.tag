<%@ tag trimDirectiveWhitespaces="true" %>
<%@ include file="form-field-header.jspf" %>
<%@ attribute name="hint" required="false" type="java.lang.String" %>
<form:raw-form-entry name="${name}">
    <s:checkbox id="${id}" name="${name}" class="${fieldClass}"/>
    <c:if test="${hint != null}">
        <span class="checkbox_hint">${hint}</span>

    </c:if>
</form:raw-form-entry>