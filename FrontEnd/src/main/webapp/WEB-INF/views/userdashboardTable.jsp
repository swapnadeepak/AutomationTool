
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${masterPlan == null}">
	<h4 style="color: green">
		<b>${message}</b>
	</h4>
	<span style="color: green"> <b>Please upload the MasterPlan
			to proceed.</b></span>
	<a href="UploadMasterPlan">Upload Master Plan</a>
</c:if>

<c:if test="${masterPlan != null}">
	<table id="example1" class="table table-bordered table-striped">
		<thead>
			<tr>
				<th>SL No</th>
				<th>Business Requirement</th>
				<th>Description</th>
				<th>Impacted BRs</th>
				<th>Modules</th>
				<th>Test Case</th>
				<th>Test Case Id</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="data" items="${masterPlan}">
				<tr>
					<td>${data.getId()}</td>
					<td>${data.getBusinessRequirement()}</td>
					<td>${data.getDescription()}</td>
					<td>${data.getImpactedBRs()}</td>
					<td>${data.getModules()}</td>
					<td>${data.getTestCases()}</td>
					<td>${data.getTestCaseId()}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>