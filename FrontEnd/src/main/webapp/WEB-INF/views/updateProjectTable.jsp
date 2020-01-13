<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table id="example1" class="table table-bordered table-striped">
	<tr>
		<td><input type="hidden" name="id" id="id"
			style="width: 200px; margin-left: 20px;"
			value="<c:out value="${project.id}"/>"></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Project Name:</td>
		<td><input type="text" name="name" id="pName"
			style="width: 200px; margin-left: 20px;background-color: #e5dee4;"
			value="<c:out value="${project.name}"/>" readonly="readonly"></td>
		<!-- 	<td><span id="pnError" style="color: red"></span></td> -->
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Project Type <span
			style="color: red">*</span> :
		</td>
		<td><select name="type" style="width: 200px; margin-left: 20px;">
				<c:forEach items="${projectTypeList}" var="projectType">
					<c:if test="${projectType eq project.type}">
						<option value="${project.type}" selected="selected"> ${project.type} </option>
					</c:if>
					<c:if test="${projectType != project.type}">
						<option value="${projectType}"> ${projectType}</option>
					</c:if>
				</c:forEach>
		</select></td>
		<!-- <td><span id="orgError" style="color: red"></span></td> -->
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Status <span
			style="color: red">*</span> :
		</td>
		<td><c:if test="${project.status eq 'active'}">
				<input type="radio" name="status" checked="checked" value="active"
					style="width: 50px; margin-left: 0px;"> Active<br>
				<input type="radio" name="status" value="inactive"
					style="width: 50px; margin-left: 0px;"> In Active
			</c:if> <c:if test="${project.status eq 'inactive'}">
				<input type="radio" name="status" value="active"
					style="width: 50px; margin-left: 0px;"> Active<br>
				<input type="radio" name="status" value="inactive" checked="checked"
					style="width: 50px; margin-left: 0px;"> In Active
			</c:if></td>
		<!-- <td><span id="statusError" style="color: red"></span></td> -->
	</tr>
	<tr>
		<td><input type="hidden" name="createdBy"
			style="width: 200px; margin-left: 20px;"
			value="<c:out value="${project.createdBy}"/>"></td>
	</tr>
	<tr>
		<td><input type="hidden" name="createdDate"
			style="width: 200px; margin-left: 20px;"
			value="<c:out value="${project.createdDate}"/>"></td>
	</tr>
	<tr>
		<td><input type="hidden" name="modifiedBy"
			style="width: 200px; margin-left: 20px;"
			value="<c:out value="${project.modifiedBy}"/>"></td>
	</tr>
	<tr>
		<td><input type="hidden" name="modifiedDate"
			style="width: 200px; margin-left: 20px;"
			value="<c:out value="${project.modifiedDate}"/>"></td>
	</tr>
	<tr>
		<td colspan="3" align="center"><input type="submit"
			name="updateProject" value="Update Project" style="width: 150px;">
			<input type="submit" name="deleteProject" value="Delete Project"
			style="width: 150px;"
			onclick="return confirm('Are you sure you want to delete?')">
		</td>
	</tr>
</table>