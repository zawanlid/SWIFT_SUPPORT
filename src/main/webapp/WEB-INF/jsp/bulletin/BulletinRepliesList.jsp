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
	    var items = $("table tbody tr");

	    var numItems = items.length;
	    var perPage = 11;

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
	<table style="height: 100%; width: 100%" id="bulletinReplyList">
		<th style="font-weight: bold; background-color: #e2e2e2;">
		<td>ID</td>
		<td>Writer</td>
		<td>Description</td>		
		<td>Created On</td>
		<td>Bulletin ID</td>
		<td>Option</td>

		</th>
		<c:forEach var="item" items="${actionBean.bulletinReplyList}" varStatus="theCount">
			<tr>
				<td>&nbsp;</td>
				<td>${theCount.index + 1}</td>
				<td>${item.name}</td>
				<td>${item.body}</td>
				<td>${item.createdOn}</td>				
				<td>${item.bulletinId}</td>
				<td><stripes:link
						beanclass="org.dnawaz.bulletinboard.web.actions.BulletinActionBean"
						event="viewBulletin">
						<stripes:param name="id" value="${item.id}" />
						<stripes:param name="incrementFlag" value="true" />
				Read
			</stripes:link></td>
			</tr>
		</c:forEach>
	</table>
</div>


