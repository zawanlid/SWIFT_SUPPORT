<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<script src="../js/jquery-2.1.1.js"></script>
<script src="../js/jquery.simplePagination.js"></script>
<script type="text/javascript" src="js/jquery.leanModal.min.js"></script>
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" />
<link type="text/css" rel="stylesheet" href="../css/style.css" />
	<link rel="StyleSheet" href="../css/simplePagination.css" type="text/css"
	media="screen" /><meta name="generator"
	content="HTML Tidy for Linux/x86 (vers 1st November 2002), see www.w3.org" />
<title>SWIFT SUPPORT</title>
<meta content="text/html; charset=windows-1252"
	http-equiv="Content-Type" />
<meta http-equiv="Cache-Control" content="max-age=0" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="Pragma" content="no-cache" />
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>

<script type="text/javascript">
$(document).ready( function() {
	 
    $('#login').focus();

});
</script>
</head>
<body>
	<div id="Main">
			<table style="height: 100%; width: 100%">
				<tr bgcolor="#2E2E2E">
					<td><center><h2><font color="white">SWIFT SUPPORT TOOLS</font></h2></center></td>
				</tr>
				<tr style="vertical-align: top; height: 100%">
					<td>
						<div id="table1">
						<font color="blue"><b><stripes:messages /></b></font>
							<table style="height: 100%; width: 100%" bgcolor="white">
								<tr>
									<td style="width: 20px"></td>
								</tr>
								<tr style="height: 100%; width: 100%" align="center">
									<td>
										<fieldset style="height: 100%; margin-bottom: 10px;">
										<legend><b>&nbsp;Login Form&nbsp;</b></legend>
										<stripes:form beanclass="org.tm.swift.web.actions.LoginActionBean"
											onsubmit="return validate()">
											<table>		
											<tr>
													<td colspan="2"> &nbsp;</td>
												</tr>										
												<tr>
													<td style="vertical-align: top">Login ID:<font color="red">&nbsp;*</font></td>
													<td style="vertical-align: top"><input type="text" name="login" style="width: 300px;" tabindex="1" id="login">
													</td>
												</tr>
												<tr>
													<td style="vertical-align: top">Password:<font color="red">&nbsp;*</font></td>
													<td style="vertical-align: top"><input type="password" name="password" style="width: 300px;" tabindex="2">
													</td>
												</tr>
												<tr>
													<td colspan="2"> &nbsp;</td>
												</tr>
												<tr>
												<td>&nbsp;</td>
												<td style="vertical-align: top"><stripes:submit name="login" value="Login" style="margin-bottom: 5px" tabindex="3"></stripes:submit></td>
													
												</tr>
												<tr>
													<td colspan="2"> &nbsp;</td>
												</tr>
											</table>
											</stripes:form>												
										</fieldset>
									</td>
								</tr>
								
								<tr>
									<td style="width: 20px"></td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
	</div>
</body>	
</html>