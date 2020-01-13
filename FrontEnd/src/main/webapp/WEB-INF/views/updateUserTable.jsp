
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.multiselect {
	height: 8em;
	border: solid 1px #c0c0c0;
	overflow: auto;
}

.multiselect label {
	display: table-row;
	font-weight: normal
}

.multiselect-on {
	color: black;
	background-color: #e0e0d1;
}
</style>

<script>
	jQuery.fn.multiselect = function() {
		$(this).each(function() {
			var checkboxes = $(this).find("input:checkbox");
			var flag = false;
			checkboxes.each(function() {
				var checkbox = $(this);
				// Highlight pre-selected checkboxes
				if (checkbox.prop("checked"))
					checkbox.parent().addClass("multiselect-on");

				// Highlight checkboxes that the user selects
				checkbox.click(function() {
					if (checkbox.prop("checked")) {
						checkbox.parent().addClass("multiselect-on");
					} else {
						checkbox.parent().removeClass("multiselect-on");
					}
				});

			});
		});
	};

	$(function() {
		$(".multiselect").multiselect();
	});

	$(document).ready(
			function() {
				$('#userEmail')
						.blur(
								function(event) {
									var mailId = document
											.getElementById('userEmail').value;
									var className = 'UserDetail';
									var id = $
									{
										user.id
									}
									;
									$.get("checkEmailOnUpdate?emailId="
											+ mailId + "&className="
											+ className + "&id=" + id,
											function(responseText) {
												if (responseText != "") {
													$("#mailError").text(
															responseText);
													$('#userEmail').val('');
													$("#userEmail").focus();
												}
											});
								});
			});
</script>
<table id="example1" class="table table-bordered table-striped">
	<tr>
		<td><input type="hidden" name="id" id="id"
			style="width: 200px; margin-left: 20px;"
			value="<c:out value="${user.id}"/>"></td>
	</tr>
	<tr>
		<!-- <td style="padding-right: 8px; width: 225px;">First Name:</td> -->
		<td><input type="hidden" name="firstName"
			style="width: 200px; margin-left: 20px;"
			value="<c:out value="${user.firstName}"/>"></td>
	</tr>
	<tr>
		<!-- 	<td style="padding-right: 8px; width: 225px;">Last Name:</td> -->
		<td><input type="hidden" name="lastName"
			style="width: 200px; margin-left: 20px;"
			value="<c:out value="${user.lastName}"/>"></td>
	</tr>

	<tr>
		<td style="padding-right: 8px; width: 225px;">User Name:</td>
		<td><input type="text" name="userName"
			style="width: 200px; margin-left: 20px;background-color: #e5dee4;"
			value="<c:out value="${user.userName}"/>" readonly="readonly"></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Email Id <span
			style="color: red">*</span> :
		</td>
		<td><input type="email" name="emailId" id="userEmail"
			oninput="checkEmail()" style="width: 200px; margin-left: 20px;"
			value="<c:out value="${user.emailId}"/>"></td>
		<td><span id="mailError" style="color: red; font-size: 13px"></span></td>
	</tr>
	<tr>
		<!-- <td style="padding-right: 8px; width: 225px;">Password :</td> -->
		<td><input type="hidden" name="password"
			style="width: 200px; margin-left: 20px;"
			value="<c:out value="${user.password}"/>"></td>
	</tr>

	<tr>
		<td style="padding-right: 8px; width: 225px;">Status <span
			style="color: red">*</span> :
		</td>
		<td><c:if test="${user.status eq 'active'}">
				<input type="radio" name="status" checked="checked" value="active"
					onclick="checkStatus()" style="width: 30px;"
					style="width: 50px;margin-left:0px;"> Active<br>
				<input type="radio" name="status" value="inactive"
					onclick="checkStatus()" style="width: 30px;"
					style="width: 50px;margin-left:0px;"> InActive
										</c:if> <c:if test="${user.status eq 'inactive'}">
				<input type="radio" name="status" value="active"
					onclick="checkStatus()" style="width: 30px;"
					style="width: 50px;margin-left:0px;"> Active<br>
				<input type="radio" name="status" value="inactive"
					onclick="checkStatus()" checked="checked" style="width: 30px;"
					style="width: 50px;margin-left:0px;"> InActive
										</c:if></td>
		<td><span id="statusError" style="color: red; font-size: 13px"></span></td>
	</tr>


	<tr>
		<td style="padding-right: 8px; width: 225px;">User Type <span
			style="color: red">*</span> :
		</td>
		<td><%-- <select name="userType" id="userType" onkeyup="checkType()"
			style="width: 200px; margin-left: 20px;">
				<c:forEach items="${typeList}" var="userType">
					<c:if test="${userType.id eq user.userType.id}">
						<option value="${user.userType.id}" selected="selected"> ${user.userType.userTypeName} </option>
					</c:if>
					<c:if test="${userType.id != user.userType.id}">
						<option value="${userType.id}"> ${userType.userTypeName}</option>
					</c:if>
				</c:forEach>
		</select> --%>
		
		<input type="text" name="userTypeDisplay"
											value="<c:out value = "${userType.userTypeName}"/>"
											style="width: 200px;margin-left:20px;	background-color: #e5dee4;"
											readonly="readonly">
											<input type="hidden" name="userType"
														value="<c:out value = "${userType.id}"/>">
		</td>
		<td><span id="typeError" style="color: red; font-size: 13px"></span></td>
	</tr>

	<tr>
		<td style="padding-right: 8px; width: 225px;">Organization <span
			style="color: red">*</span> :
		</td>
		<td><input type="text" name="organization"
			style="width: 200px; margin-left: 20px;background-color: #e5dee4;"
			value="<c:out value="${organizationName}"/>" readonly="readonly"></td>
	</tr>

	<tr>
		<td style="padding-right: 8px; width: 225px;">Project <span
			style="color: red">*</span> :
		</td>
		<td>
			<div class="multiselect" style="width: 200px; margin-left: 20px;">
				<c:forEach items="${allProjectList}" var="allProject">
					<c:set var="flag" value="true" />
					<c:forEach items="${userProjectList}" var="project">
						<c:if test="${allProject.name eq project.name}">
							<label><input type="checkbox" name="project"
								value="${project.name}" checked="checked"
								onkeyup="userValidatorForUpdate()" onclick="checkProject()" />${project.name}</label>
							<c:set var="flag" value="false" />
						</c:if>
					</c:forEach>
					<c:if test="${flag eq true}">
						<label><input type="checkbox" name="project"
							onkeyup="userValidatorForUpdate()" onclick="checkProject()"
							value="${allProject.name}" />${allProject.name}</label>
					</c:if>
				</c:forEach>

			</div>
		</td>
		<td><span id="projectError" style="color: red; font-size: 13px"></span></td>
	</tr>

	<tr>
		<td style="padding-right: 8px; width: 225px;">Assigned Group <span
			style="color: red">*</span> :
		</td>
		<td>
			<div class="multiselect" style="width: 200px; margin-left: 20px;">
				<c:forEach items="${availablegroupList}" var="availablegroup">
					<c:set var="flag" value="true" />

					<c:forEach items="${groupList}" var="userAssignedGroup">
						<c:if test="${availablegroup.name eq userAssignedGroup.name}">
							<label><input type="checkbox" name="assignedGroup"
								id="assignedGroup" value="${userAssignedGroup.name}"
								checked="checked" onclick="checkAssignGroup()" />${userAssignedGroup.name}</label>
							<c:set var="flag" value="false" />
						</c:if>

					</c:forEach>

					<c:if test="${flag eq true}">
						<label><input type="checkbox" name="assignedGroup"
							id="assignedGroup" value="${availablegroup.name}"
							onclick="checkAssignGroup()" />${availablegroup.name}</label>
					</c:if>

				</c:forEach>

			</div>
		</td>

		<td><span id="asGrpError" style="color: red; font-size: 13px"></span></td>
	</tr>
	<tr>
		<td><input type="hidden" name="createdBy"
			style="width: 200px; margin-left: 20px;"
			value="<c:out value="${user.createdBy}"/>"></td>
	</tr>
	<tr>
		<td><input type="hidden" name="createdDate"
			style="width: 200px; margin-left: 20px;"
			value="<c:out value="${user.createdDate}"/>"></td>
	</tr>
	<tr>
		<td colspan="3" align="center"><input type="submit"
			name="updateUser" value="Update user" style="width: 150px;"
			onclick="return checkselection(); return userValidatorForUpdate(); ">
			<input type="submit" name="resetPassword" value="Reset Password"
			style="width: 150px;" onclick="return confirm('Are you sure you want to reset password for this user?')"> 
			
			<input type="submit"
			name="deleteUser" value="Delete User" style="width: 150px;" onclick="return confirm('Are you sure you want to delete?')">

		</td>
	</tr>
</table>

