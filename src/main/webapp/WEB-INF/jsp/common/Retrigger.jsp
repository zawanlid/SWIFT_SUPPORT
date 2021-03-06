<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<script src="../js/jquery-2.1.1.js"></script>
<script src="../js/jquery.simplePagination.js"></script>
<script type="text/javascript" src="js/jquery.leanModal.min.js"></script>
<link rel="stylesheet"
	href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" />
<link type="text/css" rel="stylesheet" href="../css/style.css" />
<link rel="StyleSheet" href="../css/simplePagination.css"
	type="text/css" media="screen" />
<meta name="generator"
	content="HTML Tidy for Linux/x86 (vers 1st November 2002), see www.w3.org" />
<title>SWIFT SUPPORT</title>
<meta content="text/html; charset=windows-1252"
	http-equiv="Content-Type" />
<meta http-equiv="Cache-Control" content="max-age=0" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="Pragma" content="no-cache" />

<style type="text/css">
#formDiv {
	display: none;
	height: 380px;
	width: 700px;
	background: #FFFFFF;
	left: 400px;
	top: 200px;
	z-index: 100;
	position: fixed;
	margin-left: center;
	margin-top: center;
	border: 2px solid #ff0000;
	padding: 15px;
	font-size: 15px;
	-moz-box-shadow: 0 0 5px #ff0000;
	-webkit-box-shadow: 0 0 5px #ff0000;
	box-shadow: 0 0 5px #ff0000;
}

#formParamDiv {
	display: none;
	height: 200px;
	width: 400px;
	background: #FFFFFF;
	left: 40%;
	top: 300px;
	z-index: 100;
	position: absolute;
	margin-left: center;
	margin-top: center;
	border: 2px solid #ff0000;
	padding: 2px;
	font-size: 13px;
	-moz-box-shadow: 0 0 5px #ff0000;
	-webkit-box-shadow: 0 0 5px #ff0000;
	box-shadow: 0 0 5px #ff0000;
	overflow: auto;
}

#mask {
	display: none;
	background: #000;
	position: fixed;
	left: 0;
	top: 0;
	z-index: 10;
	width: 100%;
	height: 100%;
	opacity: 0;
	z-index: 99;
}

#searchList tbody tr:nth-child(even) {
	background-color: #F4F4F8;
}

#searchList tbody tr:hover td {
	background-color: yellow;
}

#retriggerLink:hover {
	color: blue;
}

#monitorLink:hover {
	color: blue;
}

#logoutLink:hover {
	color: blue;
}
</style>
<script type="text/javascript">
	// mind the slight change below, personal idea of best practices
	jQuery(function($) {
		// consider adding an id to your table,
		// just incase a second table ever enters the picture..?
		var items = $("#searchList").find("tr");

		var numItems = items.length;
		var perPage = 26;

		// only show the first 2 (or "first per_page") items initially
		items.slice(perPage).hide();

		// now setup your pagination
		// you need that .pagination-page div before/after your table
		$(".pagination-page").pagination({
			items : numItems,
			itemsOnPage : perPage,
			cssStyle : "light-theme",
			onPageClick : function(pageNumber) { // this is where the magic happens
				// someone changed page, lets hide/show trs appropriately
				var showFrom = perPage * (pageNumber - 1);
				var showTo = showFrom + perPage;

				items.hide() // first hide everything, then show for the new page
				.slice(showFrom, showTo).show();
			}
		});
	});
</script>

<link
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#dateFromInput").datepicker();
		$("#dateToInput").datepicker();

		$('#closeForm').click(function() {
			unloadPopupBox();
			$('#mask').remove();
		});

		$('#viewParamBtn').click(function() {
			loadParamListPopup();
		});

		$('#closeParamBtn').click(function() {
			unloadParamListPopup();
		});

		function unloadPopupBox() { // TO Unload the Popupbox
			$('#formDiv').fadeOut("fast");
			$("#formDiv").css({ // this is just for style        
				"opacity" : "0.8"
			});
		}

		function loadPopupBox() { // To Load the Popupbox
			$('#formDiv').fadeIn("fast");
			$("#formDiv").css({ // this is just for style
				"opacity" : "0.5"
			});
		}

		function loadParamListPopup() {
			$('#formParamDiv').fadeIn("fast");
			$("#formParamDiv").css({ // this is just for style        
				"opacity" : "0.5"
			});
		}

		function unloadParamListPopup() {
			$('#formParamDiv').fadeOut("fast");
			$("#formParamDiv").css({ // this is just for style        
				"opacity" : "0.8"
			});
		}

		$('#mask').live('click', function() {
			$('#mask , #formDiv').fadeOut(300, function() {
				$('#mask').remove();
			});
		});
	});

	function validate() {
		if ($('#dateFromInput').val() == '') {
			alert('Please select date in \"Date From\" field.');
			return false;
		} else if ($('#dateToInput').val() == '') {
			alert('Please select date in \"Date To\" field.');
			return false;
		} else if ($('#sourceSelect').val() == '') {
			alert('Please select system in \"System\" field.');
			return false;
		} else if ($('#eventNameSelect').val() == '') {
			alert('Please select event name in \"Event Name\" field.');
			return false;
		} else if ($('#paramListTA').val() == '') {
			alert('Please select value in \"Additional Params\" field.');
			return false;
		}
		return true;
	}

	function validate2() {
		if ($('#batchNameText').val() == '') {
			alert('Please enter value in \"Batch Name\" field');
			return false;
		} else if ($('#createdByText').val() == '') {
			alert('Please enter value in \"Created by\" field');
			return false;
		}

		return true;
	}

	function alertme(ctrl) {
		var tableData = $(ctrl).children("td").map(function() {
			return $(this).html();
		}).get();

		loadPopupBox();

		function loadPopupBox() { // To Load the Popupbox
			$('#Main').append('<div id="mask"></div>');
			$('#mask').fadeIn("fast");
			$('#formDiv').fadeIn("fast");
			$("#formDiv").css({ // this is just for style
				"opacity" : "0.5"
			});

			$('#ttLabel').text(tableData[2]);
			$('#eventLabel').text(tableData[3]);
			$('#statusLabel').text(tableData[6]);
			$('#requestTA').text(tableData[8]);
			$('#responseTA').text(tableData[9]);
		}
	}
</script>
</head>
<body>
	<div id="Main">
		<table style="height: 100%; width: 100%">
			<tr bgcolor="#2E2E2E">
				<td><center>
						<h2>
							<font color="white">SWIFT SUPPORT TOOLS</font>
						</h2>
						<font size="2px"><stripes:link
								beanclass="org.tm.swift.web.actions.RetriggerActionBean"
								event="viewMain">
								<font id="retriggerLink" size="2px" color="white">Retrigger
									|</font>
							</stripes:link></font> <font size="2px"><stripes:link
								beanclass="org.tm.swift.web.actions.MonitorActionBean"
								event="viewMain">
								<font id="monitorLink" size="2px" color="white">Monitor |</font>
							</stripes:link></font> <font size="2px"><stripes:link
								beanclass="org.tm.swift.web.actions.LoginActionBean"
								event="logout">
								<font id="logoutLink" size="2px" color="white">Logout </font>
							</stripes:link></font>
					</center></td>
			</tr>
			<tr style="vertical-align: top; height: 100%">
				<td>
					<div id="table1">
						<font color="blue"><b><stripes:messages /></b></font>
						<table style="height: 100%; width: 100%" bgcolor="white">
							<tr>
								<td style="width: 20px"></td>
							</tr>
							<tr>
								<td>
									<fieldset style="height: 100%; margin-bottom: 10px;">
										<legend>Search Criteria</legend>
										<stripes:form
											beanclass="org.tm.swift.web.actions.RetriggerActionBean"
											onsubmit="return validate()">
											<table style="height: 100%; width: 80%">
												<tr>
													<td style="vertical-align: top">Date From:<font
														color="red">&nbsp;*</font></td>
													<td style="vertical-align: top"><input type="text"
														id="dateFromInput" name="searchCriteria.auditDateFrom"
														style="width: 300px"></input></td>
													<td rowspan="2" style="vertical-align: top">TT List:<br />
													<font size="2px">(e.g 1-XXXXXXXX,1-XXXXXXXX)</font></td>
													<td rowspan="2" style="vertical-align: top"><textarea
															name="searchCriteria.troubleTickets" id="troubleTickets"
															style="height: 50px; width: 300px;"></textarea></td>
												</tr>
												<tr>
													<td style="vertical-align: top">Date To:<font
														color="red">&nbsp;*</font></td>
													<td style="vertical-align: top"><input type="text"
														id="dateToInput" name="searchCriteria.auditDateTo"
														style="width: 300px"></input></td>
												</tr>
												<tr>
													<td style="vertical-align: top">System:<font
														color="red">&nbsp;*</font></td>
													<td style="vertical-align: top"><select
														id="sourceSelect" name="searchCriteria.source"
														style="width: 200px;">
															<option value="">Select Source System</option>
															<option>ICP</option>
															<option>NOVA</option>
													</select></td>
													<td rowspan="3" style="vertical-align: top">Param
														List:<font color="red">&nbsp;*</font><br />
													<font size="2px">(Text to match with</font><br />
													<font size="2px">EAI_LOG.AUDIT_PARAM2</font> <br />
													<font size="2px">e.g Text_1|Text_2)</font>
													</td>
													<td rowspan="3" style="vertical-align: top"><textarea
															id="paramListTA" name="paramListTA"
															style="height: 50px; width: 300px;"></textarea></td>
												</tr>
												<tr>
													<td style="vertical-align: top">Event Name:<font
														color="red">&nbsp;*</font></td>
													<td style="vertical-align: top"><select
														id="eventNameSelect" name="searchCriteria.eventName"
														style="width: 200px;">
															<option value="">Select Event Name</option>
															<c:forEach var="item" items="${actionBean.eventNameList}"
																varStatus="theCount">
																<option value="${item}">${item}</option>
															</c:forEach>
													</select></td>
												</tr>
												<tr>
													<td></td>
													<td></td>
												</tr>
												<tr>
													<td style="vertical-align: top"><stripes:submit
															name="getList" value="Search" style="margin-bottom: 5px"></stripes:submit></td>
													<td></td>
													<td></td>
													<td><input type="button" id="viewParamBtn"
														name="viewParamList" value="View Param List"></input></td>
												</tr>
											</table>
										</stripes:form>
									</fieldset>
								</td>
							</tr>
							<tr>
								<td>
									<fieldset style="height: 100%;">
										<legend>Search Result</legend>
										<div>
											<stripes:form
												beanclass="org.tm.swift.web.actions.RetriggerActionBean"
												onsubmit="return validate2()">
												<table style="width: 50%" align="right">
													<c:choose>
														<c:when
															test="${actionBean.eaiList == null || actionBean.eaiList == '[]'}">
															<tr>
																<td>&nbsp;</td>
															</tr>
														</c:when>
														<c:otherwise>
															<tr>
																<td>Batch Name:<font color="red">&nbsp;*</font></td>
																<td><input type="text" id="batchNameText"
																	name="searchCriteria.batchName" style="width: 200px"
																	size="30" maxlength="30"></input></td>
																<td>Created By:<font color="red">&nbsp;*</font></td>
																<td><input type="text" id="createdByText"
																	name="searchCriteria.createdBy" style="width: 200px"
																	size="30" maxlength="30"></input></td>
																<td><stripes:submit name="retriggerErrorList"
																		value="Retrigger"></stripes:submit></td>
															</tr>
														</c:otherwise>
													</c:choose>
												</table>
											</stripes:form>
										</div>
										<div class="pagination-page"></div>
										<div>
											<b>[Total Records: ${actionBean.totalRecord}]</b>
										</div>

										<table style="width: 100%;" id="searchList">
											<tr bgcolor="lightgrey">
												<th style="width: 30px; text-align: left">&nbsp;</th>
												<th style="font-weight: bold; width: 80px; text-align: left">ID</th>
												<th
													style="font-weight: bold; width: 120px; text-align: left">Message</th>
												<th
													style="font-weight: bold; width: 210px; text-align: left">Event
													Name</th>
												<th
													style="font-weight: bold; width: 170px; text-align: left">Date
													Time</th>
												<th
													style="font-weight: bold; width: 500px; text-align: left">End
													Point</th>
												<th
													style="font-weight: bold; width: 150px; text-align: left">Status</th>
												<th
													style="font-weight: bold; width: 120px; text-align: left">CTT
													Number</th>
											</tr>
											<c:forEach var="item" items="${actionBean.eaiList}"
												varStatus="theCount">
												<tr id="cell" onclick="alertme(this);">
													<td style="width: 30px">${theCount.index + 1}</td>
													<td style="width: 80px">${item.eaiId}</td>
													<td style="width: 120px">${item.extMsgId}</td>
													<td style="width: 210px">${item.eventName}</td>
													<td style="width: 170px">${item.auditDateTime}</td>
													<td style="width: 500px">${item.eaiEndpoint}</td>
													<td style="width: 150px">${item.txStatus}</td>
													<td style="width: 120px">${item.cttNumber}</td>
													<td style="display: none">${item.auditParam1}</td>
													<td style="display: none">${item.auditParam2}</td>
												</tr>
											</c:forEach>

											<c:if
												test="${actionBean.eaiList == null || actionBean.eaiList == '[]'}">
												<table style="width: 100%;">
													<tr>
														<td colspan="8" style="text-align: center;">No Record
															Founds</td>
													</tr>
												</table>
											</c:if>
										</table>
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

	<div id="formDiv">
		<stripes:form beanclass="org.tm.swift.web.actions.RetriggerActionBean">
			<table style="height: 100%; width: 100%">
				<tr>
					<th style="font-weight: bold;">TT / Event Name</th>
					<th style="font-weight: bold;">Request</th>
					<th style="font-weight: bold;">Response</th>
					<th style="font-weight: bold;">Status</th>
				</tr>
				<tr>
					<td><label id="ttLabel">TT Number</label><br />
					<label id="eventLabel" style="text-align: center">Event
							Name</label></td>
					<td><textarea name="request" id="requestTA"
							style="height: 300px; width: 200px; margin-right: 10px"></textarea></td>
					<td><textarea name="response" id="responseTA"
							style="height: 300px; width: 200px;"></textarea></td>
					<td><label id="statusLabel" style="text-align: left">Status</label></td>
				</tr>
				<tr>
					<td colspan="4" align="right"><stripes:button id="closeForm"
							name="close"
							style="margin-top: 10px; margin-right: 20px; width: 80px">Close</stripes:button></td>
				</tr>

			</table>
		</stripes:form>
	</div>

	<div id="formParamDiv">
		<stripes:form beanclass="org.tm.swift.web.actions.RetriggerActionBean">
			<input type="button" id="closeParamBtn" style="float: right;"
				value="Close"></input>
			<ul style="list-style-type: disc">
				<c:forEach var="item" items="${actionBean.paramList}"
					varStatus="theCount">
					<c:set var="item1" value="${fn:replace(item, '<', '&lt;')}" />
					<c:set var="item2" value="${fn:replace(item1, '>', '&gt;')}" />
					<li>${item2}</li>
				</c:forEach>
			</ul>
		</stripes:form>
	</div>
</body>
</html>