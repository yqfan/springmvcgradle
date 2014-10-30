<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<body>
	<h2>Spring MVC Gift Detail View</h2>
 
	Title : "
	<strong> ${title} </strong>" <br>
	Description : "
	<strong> ${desc } </strong>" <br>
	<img src="imagedisplay?id=${id}" alt="image is not available" style="width:400px;height:228px"/>
 	<div><a href="giftchain">View Gift Chain</a></div>
 	<div><a href="home">Home</a></div>
</body>
</html>