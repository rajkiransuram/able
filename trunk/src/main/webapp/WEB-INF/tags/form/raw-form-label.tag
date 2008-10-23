<%--@elvariable id="actionBean" type="able.stripes.AbleActionBean"--%>
<%--@elvariable id="formId" type="java.lang.String"--%>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jspf" %>
<%@ attribute name="name" required="true" type="java.lang.String" %>
<%@ attribute name="left" required="false" type="java.lang.Boolean" %>
<c:set var="id" value="${formId}_${name}"/>
<div class="form_label${left != null && left ? " left" : ""}">
    <s:label name="${name}" for="${id}"/>
</div>
