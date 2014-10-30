<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table>
  <c:forEach items="${gifts}" var="item">
    <tr>
      <td>
    	<a class="textLink" style="color: #CC0000" href="<c:url value="/giftdetail?id=${item.id }" />" ><c:out value="${item.title}" /></a>
	  </td>
    </tr>
  </c:forEach>
</table>

<div><a href="home">Home</a></div>