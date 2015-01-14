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
	media="screen" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.4.min.js"></script>
<meta name="generator"
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
	    left: 200px;
	    top: 150px;
	    z-index:100;
	    position: relative;
	    margin-left: 350px; 
	    margin-top: -700px;
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

<script src="http://jqueryjs.googlecode.com/files/jquery-1.2.6.min.js" type="text/javascript"></script>

<script type="text/javascript">
    
    $(document).ready( function() {   
    	
    	$("#rowCell2").click(function() {
    	    var tableData = $(this).children("td").map(function() {
    	        return $(this).text();
    	    }).get();

    	    alert(tableData);
    	});
    
        $('#clickOn').click( function() { 
        	/* $('#Main').append('<div id="mask"></div>');
            $('#mask').fadeIn("fast");  */
            loadPopupBox();   	
        });
        
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
</script>
<script>
	
	// mind the slight change below, personal idea of best practices
	jQuery(function($) {
	    // consider adding an id to your table,
	    // just incase a second table ever enters the picture..?
	    var items = $("table tbody tr");

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
	});
	
	function alertme(ctrl){
		var tableData = $(ctrl).children("td").map(function() {
	        return $(this).text();
	    }).get();
		
		alert($(ctrl).find('#hiddenRequest').val());
		loadPopupBox();
		
		function loadPopupBox() {    // To Load the Popupbox
            $('#formDiv').fadeIn("fast");
            $("#formDiv").css({ // this is just for style
                "opacity": "0.5"  
            });
            
            $('#ttLabel').text(tableData[2]);
            $('#eventLabel').text(tableData[3]);
            $('#statusLabel').text(tableData[6]);
            $('#request').text($(ctrl).find('#hiddenRequest').val());
            $('#response').text($(ctrl).find('#hiddenResponse').val());
        } 
	}
</script>
</head>
<body>
	<div id="Main">
		<stripes:form beanclass="org.dnawaz.bulletinboard.web.actions.RetriggerActionBean">
			<table style="height: 100%; width: 100%">
				<tr>
					<td><center><h2>SWIFT SUPPORT TOOLS</h2></center></td>
				</tr>
				<tr valign=top height=600>
					<td height=600>
						<div id="table1">
							<table style="height: 100%; width: 100%">
								<tr>
									<td width=20></td>
									<td>
										<div id="fieldset">
											<fieldset style="height: 100%; margin-bottom: 10px;">
												<label style="display:block; float: left; font-color: red">*</label>
												<label style="width: 100px; display:block; float: left;">Date From:</label><input type="date" name="searchCriteria.auditDateFrom" style="margin-bottom: 10px"></input><br/>
												<label style="display:block; float: left; font-color: red">*</label>
												<label style="width: 100px; display:block; float: left;">Date To:</label><input type="date" name="searchCriteria.auditDateTo" style="margin-bottom: 10px"></input><br/>
												<label style="display:block; float: left; font-color: red">*</label>
												<label style="width: 100px; display:block; float: left;">System:</label>
												<select id="mySelect" name="searchCriteria.source" style="width: 145px; margin-bottom: 10px">
												  <option>ICP</option>
												  <option>NOVA</option>
												</select><br/>
												<label style="width: 100px; display:block; float: left; margin-left: 350px; margin-top: -95px">TT List:</label><br/>
												<stripes:textarea name="searchCriteria.troubleTickets" id="troubleTickets" style="height: 50px; width: 300px; margin-left: 450px; margin-top: -110px"></stripes:textarea><br/>
												<label style="width: 100px; display:block; float: left; margin-left: 350px; margin-top: -40px">Additional Param:</label><br/>
												<input type="text" name="param1" style="margin-left: 450px; margin-top: -55px; width: 300px"></input><br/>
												<input type="text" name="param2" style="margin-left: 450px; margin-bottom: 10px; margin-top: -25px; width: 300px"></input><br/>
												<input type="text" name="param3" style="margin-left: 450px; width: 300px"></input><br/>
												
														<stripes:submit name="getList" value="Search"></stripes:submit>
														<stripes:button id="clickOn" name="hello">Hello</stripes:button>
												
											</fieldset>
											<fieldset style="height: 100%;">
											<div class="pagination-page"></div>
											<table style="width: 100%;" border="1" >
												<th style="font-weight: bold;">
												<td>ID</td>
												<td>Message</td>
												<td>Event Name</td>		
												<td>Date Time</td>
												<td>End Point</td>
												<td>Status</td>
												<td>CTT Number</td>
												</th>
												
												<c:forEach var="item" items="${actionBean.eaiList}" varStatus="theCount">
													<tr id="cell" onclick="alertme(this);">
														<td>${theCount.index + 1}</td>
														<td>${item.eaiId}</td>
														<td>${item.extMsgId}</td>
														<td>${item.eventName}</td>
														<td>${item.auditDateTime}</td>				
														<td>${item.eaiEndpoint}</td>
														<td>${item.txStatus}</td>
														<td>${item.cttNumber}</td>	
														<input id="hiddenRequest" type="hidden" name="requestParam" value="${item.cttNumber}"></input>
														<input id="hiddenResponse" type="hidden" name="responseParam" value="${item.txStatus}"></input>																		
													</tr>
													
												</c:forEach>
												</table>
											</fieldset>
										</div>
									</td>
									<td width=20></td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td><center><h4>Copyright@TMRND</h4></center></td>
				</tr>
			</table>
		</stripes:form>
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
					<td><stripes:textarea name="request" id="request" style="height: 300px; width: 200px; margin-right: 10px"></stripes:textarea></td>
					<td><stripes:textarea name="response" id="response" style="height: 300px; width: 200px;"></stripes:textarea></td>
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