<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<title>Upload File Request Page</title>
</head>
<body>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
	   <h2>Welcome : ${pageContext.request.userPrincipal.name} 
           | <a href="<c:url value="/j_spring_security_logout" />" > Logout</a></h2>  
	 
    <form method="POST" action="uploadGift?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
        Image: <input type="file" name="file"><br /> 
        Title: <input type="text" name="title"><br />
        Description: <input type="text" name="desc"><br/> <br /> 
        <input type="submit" value="Upload"> Press here to upload the gift!
        
    </form>
    <div><a href="home">Home</a></div>
    
    </c:if> 
</body>
</html>