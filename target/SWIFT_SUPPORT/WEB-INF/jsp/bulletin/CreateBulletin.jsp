<%@ include file="../common/IncludeTop.jsp"%>

<script type="text/javascript">
	function validate() {

		if ($("#userId").val() == '') {
			alert('Please enter value in \"User ID\" field.');
			$("#userId").focus();
			return false;
		} else if ($("#password").val() == '') {
			alert('Please enter value in \"Password\" field.');
			$("#password").focus();
			return false;
		} else if ($("#name").val() == '') {
			alert('Please enter value in \"Name\" field.');
			$("#name").focus();
			return false;
		} else if ($("#email").val() == '') {
			alert('Please enter value in \"Email\" field.');
			$("#email").focus();
			return false;
		} else if ($("#title").val() == '') {
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

	$(document)
			.ready(
					function onload() {

						$("#WelcomeContent")
								.html(
										"<b>Please enter below details to create bulletin. Please rember User ID and Passwor which would be required to edit or delete bulletin.</b>");

						$("#userId").val('');
						$("#name").val('');
						$("#password").val('');
						$("#email").val('');
						$("#title").val('');
						$("#body").val('');

					});
</script>
<div id="Main">
	<stripes:form
		beanclass="org.dnawaz.bulletinboard.web.actions.BulletinActionBean"
		onsubmit="return validate()">
		<table style="height: 100%; width: 100%">

			<tr>
				<td><b>User ID:</b></td>
				<td><stripes:text name="bulletin.userId" id="userId" size="50"></stripes:text></td>
			</tr>
			<tr>
				<td><b>Password:</b></td>
				<td><stripes:password name="bulletin.password" id="password" size="50"></stripes:password></td>
			</tr>
			<tr>
				<td><b>Name:</b></td>
				<td><stripes:text name="bulletin.name" id="name" size="50"></stripes:text></td>
			</tr>
			<tr>
				<td><b>Email:</b></td>
				<td><stripes:text name="bulletin.email" id="email" size="50"></stripes:text></td>
			</tr>
			<tr>
				<td><b>Title:</b></td>
				<td><stripes:text name="bulletin.title" id="title" size="50"></stripes:text></td>
			</tr>

			<tr>
				<td><b>Body:</b></td>
				<td><stripes:textarea name="bulletin.body" cols="100" rows="15"
						id="body"></stripes:textarea></td>
			</tr>

			<tr>
				<td><b>&nbsp;</b></td>
				<td><b><stripes:submit name="createBulletin" value="Create"></stripes:submit></b></td>
			</tr>

		</table>
	</stripes:form>
</div>

<%@ include file="../common/IncludeBottom.jsp"%>

