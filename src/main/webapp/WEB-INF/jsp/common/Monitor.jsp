<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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
<title>SWIFT_SUPPORT</title>
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
		var perPage = 20;

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

		$('#closeForm').click(function() {
			unloadPopupBox();
			$('#mask').remove();
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

		$('#mask').live('click', function() {
			$('#mask , #formDiv').fadeOut(300, function() {
				$('#mask').remove();
			});
		});
	});

	function validate() {
		if ($('#batchSelect').val() == '') {
			alert('Please select value in \"Batch Name\" field.');
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
			$('#endPointLabel').html(
					'<p><b>End Point:&nbsp;</b>' + tableData[5] + '</p>');
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
						<table style="height: 100%; width: 100%" bgcolor="white">
							<tr>
								<td style="width: 20px"></td>
							</tr>
							<tr>
								<td>
									<fieldset style="height: 100%; margin-bottom: 10px;">
										<legend>Search Criteria</legend>
										<stripes:form
											beanclass="org.tm.swift.web.actions.MonitorActionBean"
											onsubmit="return validate()">
											<table style="height: 100%; width: 80%">
												<tr>
													<td style="vertical-align: top">Batch Name:<font
														color="red">&nbsp;*</font></td>
													<td style="vertical-align: top"><select
														id="batchSelect" name="searchCriteria.batchName"
														style="width: 300px;">
															<option value="">Select Batch Name</option>
															<c:forEach var="item" items="${actionBean.batchList}"
																varStatus="theCount">
																<option value="${item.id}">${item.name}&nbsp;[${item.createDateTime}]</option>
															</c:forEach>
													</select></td>
													<td style="vertical-align: top">TT Number:</td>
													<td style="vertical-align: top"><textarea
															id="ttNumber" name="searchCriteria.troubleTickets"
															style="width: 300px"></textarea></td>
												</tr>
												<tr>
													<td colspan="2" style="vertical-align: top;"><stripes:submit
															name="getList" value="Search" style="margin-bottom: 5px"></stripes:submit></td>
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
												beanclass="org.tm.swift.web.actions.MonitorActionBean">
												<table id="batchDetailTable" style="width: 65%"
													align="right">
													<tr bgcolor="lightgrey">
														<td><b>Batch Name:</b></td>
														<td><b>Source:</b></td>
														<td><b>Created By:</b></td>
														<td><b>Created On:</b></td>
														<td><b>Is Active:</b></td>
														<td><b>Status:</b></td>
														<td><b>Last Update on:</b></td>

													</tr>
													<tr bgcolor="#F2F2F2">
														<td>${actionBean.batch.name}</td>
														<td>${actionBean.batch.source}</td>
														<td>${actionBean.batch.createdBy}</td>
														<td>${actionBean.batch.createDateTime}</td>
														<td>${actionBean.batch.isActive}</td>
														<td>${actionBean.batch.status}</td>
														<td>${actionBean.batch.lastUpdateDateTime}</td>
													</tr>
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
													style="font-weight: bold; width: 100px; text-align: left">Status</th>
												<th
													style="font-weight: bold; width: 120px; text-align: left">CTT
													Number</th>
												<th
													style="font-weight: bold; width: 170px; text-align: left; background-color: #F6D8CE"><font
													color="black">Retrigger Date Time</font></th>
												<th
													style="font-weight: bold; width: 100px; text-align: left; background-color: #F6D8CE">Status</th>
												<th
													style="font-weight: bold; width: 280px; text-align: left; background-color: #F6D8CE">Remarks</th>
											</tr>
											<c:forEach var="item" items="${actionBean.monitorList}"
												varStatus="theCount">
												<tr id="cell" onclick="alertme(this);">
													<td style="width: 30px">${theCount.index + 1}</td>
													<td style="width: 80px">${item.eaiLog.eaiId}</td>
													<td style="width: 120px">${item.eaiLog.extMsgId}</td>
													<td style="width: 210px">${item.eaiLog.eventName}</td>
													<td style="width: 170px">${item.eaiLog.auditDateTime}</td>
													<td style="display: none">${item.eaiLog.eaiEndpoint}</td>
													<td style="width: 150px">${item.eaiLog.txStatus}</td>
													<td style="width: 120px">${item.eaiLog.cttNumber}</td>
													<td style="display: none">${item.eaiLog.auditParam1}</td>
													<td style="display: none">${item.eaiLog.auditParam2}</td>
													<td style="width: 170">${item.batchDetail.lastUpdateDateTime}</td>
													<td style="width: 150px">${item.batchDetail.status}</td>
													<td style="width: 180px">${item.batchDetail.remarks}</td>
												</tr>
											</c:forEach>

											<c:if
												test="${actionBean.monitorList == null || actionBean.monitorList == '[]'}">
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
		<stripes:form beanclass="org.tm.swift.web.actions.MonitorActionBean">
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
					<td colspan="3"><label id="endPointLabel">endPoint</label></td>
					<td align="right"><stripes:button id="closeForm" name="close"
							style="margin-top: 10px; margin-right: 20px; width: 80px">Close</stripes:button></td>
				</tr>

			</table>
		</stripes:form>
	</div>
</body>
</html>
