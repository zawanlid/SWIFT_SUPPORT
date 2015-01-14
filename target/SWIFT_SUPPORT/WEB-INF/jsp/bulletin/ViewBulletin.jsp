<%@ include file="../common/IncludeTop.jsp"%>

<script type="text/javascript">
	function clearName() {
		var nameVal = $("#name").val();
		if (nameVal == 'enter your name') {
			$("#name").val('');
		}
	}

	function validate() {
		if ($("#name").val() == '' || $("#name").val() == 'enter your name') {
			alert('Please enter value in \"Name\" field.');
			$("#name").focus();
			return false;
		} else if ($("#body").val() == '') {
			alert('Please enter some text in \"Reply\" box.');
			$("#body").focus();
			return false;
		}
		return true;
	}

	function validateUserForm() {
		if ($("#userId").val() == '') {
			alert('Please enter value in \"User ID\" field.');
			$("#userId").focus();
			return false;
		} else if ($("#password").val() == '') {
			alert('Please enter some text in \"password\" box.');
			$("#password").focus();
			return false;
		}

		if ($("#actionType").val() == '2') {
			var confirmStatus = confirm('Are you sure to delete this bulletin!');
			if (!confirmStatus) {
				return false;
			}
		}
		return true;
	}
	function showReply() {
		$("#name").val('enter your name');
		$("#body").val('');
		$("#reply").show();
		$("#replybutton").hide();

	}
	function hideReply() {
		$("#reply").hide();
		$("#replybutton").show();

	}
	function showValidateUser(actionType) {

		$("#userId").val('');
		$("#password").val('');
		$("#login").show();
		$("#editordelete").hide();
		$("#actionType").val(actionType);
		return false;
	}
	function hideValidateUser() {
		$("#login").hide();
		$("#editordelete").show();
		return false;
	}
	$(document)
			.ready(
					function() {
						$("#WelcomeContent")
								.html(
										"<b>This is bulletin reading mode. You can perform Edit or Delete Bulltein actions.<br/>To reply this bulletin plese click on reply button</b>");
						$("#reply").hide();
						$("#replybutton").show();
						$("#login").hide();
						$("#editordelete").show();
						if ($("#name").val() != 'enter your name') {
							$("#name").val('');
							$("#body").val('');
						}
					});
</script>
<div id="Main">
	<table style="height: 100%; width: 100%">

		<tr id="login">
			<td width="100"><b>Login:</b></td>
			<td><stripes:form
					beanclass="org.dnawaz.bulletinboard.web.actions.BulletinActionBean"
					onsubmit="return validateUserForm()">
					User ID:&nbsp;<stripes:text name="bulletin.userId" id="userId"></stripes:text>
					<br />
					<stripes:hidden name="id" value="actionBean.id"></stripes:hidden>
					<stripes:hidden name="actionType" id="actionType"></stripes:hidden>
					Password:&nbsp;<stripes:password name="bulletin.password"
						id="password"></stripes:password>
					<br />
					<stripes:submit name="validateUser">Validate</stripes:submit>
					<stripes:button name="cancel" onclick="hideValidateUser()">Cancel</stripes:button>
				</stripes:form></td>
		</tr>
		<tr id="editordelete">
			<td width="100"><b>Options:</b></td>
			<td><stripes:link
					beanclass="org.dnawaz.bulletinboard.web.actions.BulletinActionBean"
					event="updateBulletinView" onclick="return showValidateUser(1)">Edit</stripes:link>
				&nbsp;&nbsp;<stripes:link
					beanclass="org.dnawaz.bulletinboard.web.actions.BulletinActionBean"
					event="deleteBulletin" onclick="return showValidateUser(2)">Delete</stripes:link></td>
		</tr>

		<tr>
			<td><b>Writer:</b></td>
			<td>${actionBean.bulletin.name}</td>
		</tr>
		<tr>
			<td><b>Created Date:</b></td>
			<td>${actionBean.bulletin.createdOn}</td>
		</tr>
		<tr>
			<td><b>No. of Views:</b></td>
			<td>${actionBean.bulletin.readCount}</td>
		</tr>
		<tr>
			<td><b>Title:</b></td>
			<td>${actionBean.bulletin.title}</td>
		</tr>

		<tr>
			<td><b>Body:</b></td>
			<td>${actionBean.bulletin.body}</td>
		</tr>

		<c:if test="${actionBean.bulletinReplyList != null}">
			<tr>
				<td><b>Replies</b></td>
				<td><c:forEach var="item"
						items="${actionBean.bulletinReplyList}">
			&nbsp;&nbsp;>&nbsp;&nbsp;<b>${item.name}:</b>&nbsp;&nbsp;${item.createdOn}<br />&nbsp;&nbsp;${item.body}<br />
					</c:forEach></td>
			</tr>
		</c:if>
		<tr>
			<td><b>&nbsp;</b></td>
			<td><b>&nbsp;</b></td>
		</tr>
		<tr id="replybutton">
			<td><b>Reply</b></td>
			<td><button onclick="showReply()" value="Reply"
					name="replybutton">&nbsp;Reply&nbsp;</button></td>
		</tr>
		<tr id="reply">
			<td><b>Reply</b></td>
			<td><stripes:form
					beanclass="org.dnawaz.bulletinboard.web.actions.BulletinActionBean"
					onsubmit="return validate()">
					Name:&nbsp;<stripes:text name="bulletinReply.name" size="50"
						value="enter your name" onclick="clearName()"
						onkeypress="clearName()" id="name"></stripes:text>
					<br />
					<stripes:hidden name="id" value='1'></stripes:hidden>
					Text:&nbsp;&nbsp;&nbsp;&nbsp;<stripes:textarea
						name="bulletinReply.body" rows="5" cols="70" id="body"></stripes:textarea>
					<br />&nbsp;<br />
					<stripes:submit name="createBulletinReply">Reply</stripes:submit>&nbsp;<stripes:button
						name="cancel" onclick="hideReply()">Cancel</stripes:button>
				</stripes:form></td>
		</tr>
	</table>
</div>

<%@ include file="../common/IncludeBottom.jsp"%>

