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
	<link rel="StyleSheet" href="../css/simplePagination.css" type="text/css"
	media="screen" /><meta name="generator"
	content="HTML Tidy for Linux/x86 (vers 1st November 2002), see www.w3.org" />
<title>Bulletin Board Demo</title>
<meta content="text/html; charset=windows-1252"
	http-equiv="Content-Type" />
<meta http-equiv="Cache-Control" content="max-age=0" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="Pragma" content="no-cache" />

<style type="text/css">
	#formDiv{
		display:none;
		height:380px;  
	    width:700px;  
	    background:#FFFFFF;  
	    left: 400px;
	    top: 200px;
	    z-index:100;
	    position: fixed;
	    margin-left: center; 
	    margin-top: center;
	    border:2px solid #ff0000;      
	    padding:15px;  
	    font-size:15px;  
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
	    opacity: 0.7;
	    z-index: 999;
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
        items: numItems,
        itemsOnPage: perPage,
        cssStyle: "light-theme",
        onPageClick: function(pageNumber) { // this is where the magic happens
            // someone changed page, lets hide/show trs appropriately
            var showFrom = perPage * (pageNumber - 1);
            var showTo = showFrom + perPage;

            items.hide() // first hide everything, then show for the new page
                 .slice(showFrom, showTo).show();
        }
    });
});</script>

<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>

<script type="text/javascript">
    $(document).ready( function() {
    	$("#dateFromInput").datepicker();
    	$("#dateToInput").datepicker();
    	
    	$("table#searchList tr:odd").css("background-color", "#F4F4F8");
    	 /* $("table#id2 tr:odd").css("background-color", "#EFF1F1"); */
    	 
        $('#closeForm').click(function(){
        	unloadPopupBox();
        });

        function unloadPopupBox() {    // TO Unload the Popupbox
            $('#formDiv').fadeOut("fast");
            $("#formDiv").css({ // this is just for style        
                "opacity": "0.8"  
            }); 
        }    
        
        function loadPopupBox() {    // To Load the Popupbox
            $('#formDiv').fadeIn("fast");
            $("#formDiv").css({ // this is just for style
                "opacity": "0.5"  
            });
        } 
        
        $('#mask').live('click', function() { 
        	  $('#mask , #formDiv').fadeOut(300 , function() {
        	    $('#mask').remove();  
        	}); 
        });
    });
    
    function validate(){
    	if($('#dateFromInput').val() == ''){
    		alert('Please select date in \"Date From\" field.');
    		return false;
    	}else if($('#dateToInput').val() == ''){
    		alert('Please select date in \"Date To\" field.');
    		return false;
    	}else if($('#sourceSelect').val() == ''){
    		alert('Please select system in \"System\" field.');
    		return false;
    	}
    	return true;
    }
    
    function validate2(){
    	if($('#batchNameText').val() == ''){
    		alert('Please enter value in \"Batch Name\" field');
    		return false;
    	}else if($('#createdByText').val() == ''){
    		alert('Please enter value in \"Created by\" field');
    		return false;
    	}
    	return true;
    }
    
	function alertme(ctrl){
		var tableData = $(ctrl).children("td").map(function() {
	        return $(this).html();
	    }).get();
		
		loadPopupBox();
		
		function loadPopupBox() {    // To Load the Popupbox
			/* $('#Main').append('<div id="mask"></div>');
            $('#mask').fadeIn("fast");  */
            $('#formDiv').fadeIn("fast");
            $("#formDiv").css({ // this is just for style
                "opacity": "0.5"  
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
				<tr bgcolor="black">
					<td><center><h2><font color="white">SWIFT SUPPORT TOOLS</font></h2></center></td>
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
										<stripes:form beanclass="org.dnawaz.bulletinboard.web.actions.RetriggerActionBean"
											onsubmit="return validate()">
											<table style="height: 100%; width: 80%">
												<tr>
													<td style="vertical-align: top">Date From:<font color="red">*</font></td>
													<td style="vertical-align: top"><input type="text" id="dateFromInput" name="searchCriteria.auditDateFrom" style="width: 300px"></input></td>
													<td rowspan="2" style="vertical-align: top">TT List:<br/>(e.g 1-XXXXXXXX,1-XXXXXXXX)</td>
													<td rowspan="2" style="vertical-align: top"><stripes:textarea name="searchCriteria.troubleTickets" id="troubleTickets" style="height: 50px; width: 300px;"></stripes:textarea></td>
												</tr>
												<tr>
													<td style="vertical-align: top">Date To:<font color="red">*</font></td>
													<td style="vertical-align: top"><input type="text" id="dateToInput" name="searchCriteria.auditDateTo" style="width: 300px"></input></td>
												</tr>
												<tr>
													<td style="vertical-align: top">System:<font color="red">*</font></td>
													<td style="vertical-align: top "><select id="sourceSelect" name="searchCriteria.source" style="width: 200px;">
														  <option>ICP</option>
														  <option>NOVA</option>
														</select>
													</td>
													<td style="vertical-align: top">Additional Params:</td>
													<td style="vertical-align: top"><input type="text" name="param1" style="width: 300px"></input></td>
												</tr>
												<tr>
													<td></td>
													<td></td>
													<td>(Additional text for</td>
													<td style="vertical-align: top"><input type="text" name="param2" style="width: 300px"></input></td>
												</tr>
												<tr>
													<td></td>
													<td></td>
													<td>AUDIT_PARAM2 matching)</td>
													<td style="vertical-align: top"><input type="text" name="param3" style="width: 300px"></input></td>
												</tr>
												<tr>
													<td style="vertical-align: top"><stripes:submit name="getList" value="Search" style="margin-bottom: 5px"></stripes:submit></td>
													<td></td>
													<td></td>
													<td style="vertical-align: top"><input type="checkbox" name="searchCriteria.saveParam">Save Param</input></td>
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
										<stripes:form beanclass="org.dnawaz.bulletinboard.web.actions.RetriggerActionBean"
											onsubmit="return validate2()">
											<table style="width: 50%" align="right">
												<tr>
													<td>Batch Name:<font color="red">*</font></td>
													<td><input type="text" id="batchNameText" name="searchCriteria.batchName" style="width: 200px"></input></td>
													<td>Created By:<font color="red">*</font></td>
													<td><input type="text" id="createdByText" name="searchCriteria.createdBy" style="width: 200px"></input></td>
													<td><stripes:submit name="retriggerErrorList" value="Retrigger"></stripes:submit></td>
												</tr>
											</table>
										</stripes:form>
										</div>
											<div class="pagination-page"></div><div><b>[Total Records: ${actionBean.totalRecord}]</b></div>
											<table style="width: 100%; border-width: 2px">
												<tr bgcolor="lightgrey">
													<th style="width: 30px; text-align: left">&nbsp;</th>
													<th style="font-weight: bold; width: 100px; text-align: left">ID</th>
													<th style="font-weight: bold; width: 150px; text-align: left">Message</th>
													<th style="font-weight: bold; width: 230px; text-align: left">Event Name</th>
													<th style="font-weight: bold; width: 100px; text-align: left">Date Time</th>
													<th style="font-weight: bold; width: 500px; text-align: left">End Point</th>
													<th style="font-weight: bold; width: 150px; text-align: left">Status</th>
													<th style="font-weight: bold; width: 120px; text-align: left">CTT Number</th>
												</tr>
											</table>
											<table style="width: 100%;" id="searchList" >
												<c:forEach var="item" items="${actionBean.eaiList}" varStatus="theCount">
													<tr id="cell" onclick="alertme(this);" onmouseover="this.bgColor='yellow'" onmouseout="this.bgColor='white'">
														<td style="width: 30px">${theCount.index + 1}</td>
														<td style="width: 100px">${item.eaiId}</td>
														<td style="width: 150px">${item.extMsgId}</td>
														<td style="width: 230px">${item.eventName}</td>
														<td style="width: 100px">${item.auditDateTime}</td>				
														<td style="width: 500px">${item.eaiEndpoint}</td>
														<td style="width: 150px">${item.txStatus}</td>
														<td style="width: 120px">${item.cttNumber}</td>
														<td style="display: none">${item.auditParam1}</td>
														<td style="display: none">${item.auditParam2}</td>														
													</tr>
												</c:forEach>
												
												<c:if test="${actionBean.eaiList == null}">
													<table  style="width: 100%;">
														<tr>
															<td colspan="8" style="text-align: center;">No Record Founds</td>
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
	    <stripes:form beanclass="org.dnawaz.bulletinboard.web.actions.RetriggerActionBean">
	        <table style="height: 100%; width: 100%">
	        <tr>
				<th style="font-weight: bold;">TT / Event Name</th>
				<th style="font-weight: bold;">Request</th>
				<th style="font-weight: bold;">Response</th>
				<th style="font-weight: bold;">Status</th></tr>
				<tr>
					<td><label id="ttLabel">TT Number</label><br/><label id="eventLabel"  style="text-align: center">Event Name</label></td>
					<td><stripes:textarea name="request" id="requestTA" style="height: 300px; width: 200px; margin-right: 10px"></stripes:textarea></td>
					<td><stripes:textarea name="response" id="responseTA" style="height: 300px; width: 200px;"></stripes:textarea></td>
					<td><label id="statusLabel"  style="text-align: left">Status</label></td>
				</tr>
				<tr>
					<td colspan="4" align="right"><stripes:button id="closeForm" name="close" style="margin-top: 10px; margin-right: 20px; width: 80px">Close</stripes:button></td>
				</tr>
	
			</table>
	    </stripes:form>
	</div>
</body>	
</html>