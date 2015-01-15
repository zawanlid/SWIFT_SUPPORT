<%@ include file="../common/IncludeTop.jsp"%>
<script>
	$(document).ready(
			function() {

				$("#WelcomeContent").html(
						"<b>Welcome in Bulletin Board!&nbsp;&nbsp;</b>");
			});
	
	// mind the slight change below, personal idea of best practices
	jQuery(function($) {
	    // consider adding an id to your table,
	    // just incase a second table ever enters the picture..?
	    var items = $("#bulletinlist").find("tr");

	    var numItems = items.length;
	    var perPage = 10;

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
</script>
<div id="Main">
<div class="pagination-page"></div>
<table  style="height: 100%; width: 100%"><td></td>header</tr><tr><td style="height: 100%; width: 100%">
<table  style="height: 10%; width: 100%">
	<tr>
		
		<td>ID</td>
		<td>Writer</td>
		<td>Title</td>		
		<td>Created On</td>
		<td>Read Count</td>
		<td>Option</td>
		</tr></table>
	<table  style="height: 100%; width: 100%" id="bulletinlist">

		<c:forEach var="item" items="${actionBean.bulletinlist}" varStatus="theCount">
			<tr>
				
				<td>${theCount.index + 1}</td>
				<td>${item.name}</td>
				<td>${item.title}</td>
				<td>${item.createdOn}</td>				
				<td>${item.readCount}</td>
				<td><stripes:link
						beanclass="org.dnawaz.bulletinboard.web.actions.BulletinActionBean"
						event="viewBulletin">
						<stripes:param name="id" value="${item.id}" />
						<stripes:param name="incrementFlag" value="true" />
				Read
			</stripes:link></td>
			</tr>
		</c:forEach>
	</table></td></tr>
	<tr><td></td>footer</tr></table>
</div>

<%@ include file="../common/IncludeBottom.jsp"%>

