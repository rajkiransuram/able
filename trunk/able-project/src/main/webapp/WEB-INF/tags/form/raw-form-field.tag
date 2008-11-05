<%--@elvariable id="actionBean" type="able.stripes.AbleActionBean"--%>
<%--@elvariable id="formId" type="java.lang.String"--%>
<%@ tag import="java.util.Set" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/include/taglibs.jspf" %>
<%@ attribute name="name" required="true" type="java.lang.String" %>
<div class="form_field">
    <jsp:doBody/>
    <c:if test="${actionBean.context.validationErrors[name] != null}">
        <%
            Set<String> displayedErrors = (Set<String>) request.getAttribute("displayedErrors");
            displayedErrors.add(name);
        %>
        <div class="form_error">
            <span style="color: #f00; font-weight: bold">***</span> <s:errors field="${name}"/>
        </div>
    </c:if>
</div>
