<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>

<html>
	<head>
		<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
		<title>Flashcards - submit data file</title>
		<script type="text/javascript">
		<!--//
		function validateFileExtension(fld) {
			if(!/(\.ods)$/i.test(fld.value)) {
				alert("Invalid ods file type.");
				fld.form.reset();
				fld.focus();
				return false;
			}
			return true;
		}
		//-->
		</script>
	</head>
	<body>
	
		<form:form method="post" modelAttribute="odsFile" enctype="multipart/form-data" 
			onsubmit="return validateFileExtension(this.odsFileForm)">
			<fieldset>
				<legend>Upload Flashcards spreadsheet(ods)</legend>
 				<form:input path="fileData" type="file" name="odsFileForm" onchange="return validateFileExtension(this)" /> 
 				<input type="submit" value="upload" />
			</fieldset>
		</form:form> 

	</body>
</html>
