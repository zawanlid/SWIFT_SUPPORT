<%@ include file="../common/IncludeTop.jsp"%>

<script type="text/javascript">
	function validate(form) {
		if ($("#title").val() == '') {
			alert('Please enter value in \"Title\" field.');
			$("#title").focus();
			return false;
		} else if ($("#body").val() == '') {
			alert('Please enter some text in \"Body\" box.');
			$("#body").focus();
			return false;
		}
		return true;
	}

	function skipValidate() {
		$("#BulletinActionBeanForm").attr('onsubmit', '');
	}
</script>
<div id="Main">
	<stripes:form
		beanclass="org.dnawaz.bulletinboard.web.actions.BulletinActionBean"
		onsubmit="return validate(this)" id="BulletinActionBeanForm">
		<table style="height: 100%; width: 100%">


			<tr>
				<td><b>Title:</b></td>
				<td><stripes:text name="bulletin.title"
						value="${actionBean.bulletin.title}" id="title" size="100"></stripes:text></td>
			</tr>
			<stripes:hidden name="id" value="actionBean.id"></stripes:hidden>
			<tr>
				<td><b>Body:</b></td>
				<td><stripes:textarea name="bulletin.body" cols="100" rows="15"
						value="${actionBean.bulletin.body}" id="body"></stripes:textarea></td>
			</tr>

			<tr>
				<td><b>&nbsp;</b></td>
				<td><b><stripes:submit name="updateBulletin" value="Update"></stripes:submit>&nbsp;<stripes:submit
							name="viewBulletin" onclick="skipValidate()">Cancel</stripes:submit>
				</b></td>
			</tr>

		</table>
	</stripes:form>
</div>

<%@ include file="../common/IncludeBottom.jsp"%>

