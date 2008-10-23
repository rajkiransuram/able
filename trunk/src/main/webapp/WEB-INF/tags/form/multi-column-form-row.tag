<%@ include file="/WEB-INF/jsp/include/taglibs.jspf" %>
<%@ tag dynamic-attributes="dynaMap" %>
<%@ tag import="java.util.Map" %>
<table class="multi_column_form" <c:if test="${dynaMap.id != null}">id="${dynaMap.id}"</c:if>>
    <tbody>
        <tr>
            <%
                int i = 1;
                Map<String, String> map = (Map<String, String>) jspContext.getAttribute("dynaMap");
                while (map.containsKey("field" + i)) {
                    String content = map.get("field" + i);
            %>
            <td valign="top"<%= i == 1 ? " class='first'" : ""%>>
                <%= content %>
            </td>
            <%
                    i++;
                }
            %>
        </tr>
    </tbody>
</table>