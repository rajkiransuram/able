<%--@elvariable id="actionBean" type="able.stripes.AbleActionBean"--%>
<%@ tag import="java.util.HashSet" %>
<%@ tag import="able.stripes.AbleActionBean" %>
<%@ tag import="net.sourceforge.stripes.action.ActionBean" %>
<%@ tag import="net.sourceforge.stripes.validation.ValidationErrors" %>
<%@ tag trimDirectiveWhitespaces="true"%>
<%@include file="/WEB-INF/jsp/include/taglibs.jspf" %>
<%@ attribute name="beanclass" required="true" type="java.lang.String" %>
<%@ attribute name="title" required="false" type="java.lang.String" %>
<%
    String formId = beanclass.substring(beanclass.lastIndexOf('.') + 1);
    if (formId.endsWith("ActionBean")) {
        formId = formId.substring(0, formId.length() - "ActionBean".length());
    }
    request.setAttribute("formId", formId);
    HashSet<String> displayedErrors = new HashSet<String>();
    request.setAttribute("displayedErrors", displayedErrors);
%>
<div class="form_container">
    <s:form id="${formId}" beanclass="${beanclass}">
        <jsp:doBody var="output"/>
        <div class="form_title">
            ${title}
        </div>
        <%
            ActionBean ac = (ActionBean) request.getAttribute("actionBean");
            ValidationErrors validationErrors = ac.getContext().getValidationErrors();
            if (!validationErrors.isEmpty()) {
                boolean showList = false;
                for (String key : validationErrors.keySet()) {
                    if (!displayedErrors.contains(key)) {
                        showList = true;
                        break;
                    }
                }
        %>
        <div class="form_errors">
            Please correct the following:

            <% if (showList) { %>
            <ul>
                <c:forEach var="v" items="${actionBean.context.validationErrors}">
                    <c:if test="${!af:contains(requestScope.displayedErrors, v.key)}">
                        <li><s:errors field="${v.key}"/></li>
                    </c:if>
                </c:forEach>
            </ul>
            <% } %>
        </div>
        <%
            }
        %>
        ${output}
    </s:form>
</div>

