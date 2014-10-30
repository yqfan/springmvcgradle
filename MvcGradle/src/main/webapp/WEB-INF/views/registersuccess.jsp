<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
	<h2>Spring MVC Register Success</h2>
 
	
	<strong> ${name} </strong>: - ${message}.
	
    <a class="textLink" href="<c:url value="${redirect}" />" ><c:out value="${redirect}" /></a>
 
</body>
</html>