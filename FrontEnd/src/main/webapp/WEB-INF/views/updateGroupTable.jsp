<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.multiselect {
    height:8em;
    border:solid 1px #c0c0c0;
    width: 280px; 
    margin-left: 20px;
    overflow:auto;
}
 
.multiselect label {
    display:list-item;
    font-weight:normal;	
   
}
 
.multiselect-on {
    color:black;
    background-color:#e0e0d1;
}
</style>

<script type="text/javascript">
jQuery.fn.multiselect = function() {
    $(this).each(function() {
        var checkboxes = $(this).find("input:checkbox");
        checkboxes.each(function() {
            var checkbox = $(this);
            // Highlight pre-selected checkboxes
            if (checkbox.prop("checked"))
                checkbox.parent().addClass("multiselect-on");
 
            // Highlight checkboxes that the user selects
            checkbox.click(function() {
                if (checkbox.prop("checked"))
                    checkbox.parent().addClass("multiselect-on");
                else
                    checkbox.parent().removeClass("multiselect-on");
            });
        });
    });
};

$(function() {
    $(".multiselect").multiselect();
});

$(document).ready(
		function() {
			$('#groupName').blur(
					function(event) {
						var name = document.getElementById('groupName').value;
						var className = 'Group';
						var id = ${group.id};
						$.get("checkNameOnUpdate?name=" + name + "&className="
								+ className+"&id="+id, function(responseText, status) {
							if (responseText.trim() != "") {
								$("#gnError").text(responseText);
								$('#groupName').val('');
								$("#groupName").focus();
							}
						});
					});
		});

</script>

<table id="example1" class="table table-bordered table-striped">
	<tr>
		<td><input type="hidden" name="id" id="id"
			style="width: 280px; margin-left: 20px;"
			value="<c:out value="${group.id}"/>"></td>
	</tr>
	<tr>
		<td style="padding-right: 0px; width: 225px;">Group Name <span style="color: red">*</span> :</td>
		<td><input type="text" name="name" id="groupName"
			style="width: 280px; margin-left: 20px;"
			value="<c:out value="${group.name}"/>" oninput="checkGrpName()" ></td>
			<td><div id="gnError" style="color: red;font-size: 13px"></div></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Group Type <span style="color: red">*</span> :</td>
		<td>
		<c:if test="${group.type eq 'link type'}">
		<input type="radio" name="type"
			checked="checked" style="width: 15px; margin-left: 20px;"
			value="link type">  Link Type
			<input type="radio" name="type"
			style="width: 15px; margin-left: 20px;"
			value="featured type">  Featured Type
		</c:if>
		<c:if test="${group.type eq 'featured type'}">
		<input type="radio" name="type"
			  style="width: 15px; margin-left: 20px;"
			value="link type">  Link Type
		<input type="radio" name="type" checked="checked"
			style="width: 15px; margin-left: 20px;"
			value="featured type">  Featured Type
			</c:if>
			</td>
			<td><span id="linkError" style="color: red;font-size: 13px"></span></td>
	</tr>
	<tr>
		<td style="padding-right: 8px; width: 225px;">Assigned Roles <span style="color: red">*</span> :</td>
		<td>
      <div class="multiselect" >
	<c:forEach items="${roleList}" var="availableRole">
	 <c:set var = "flag"  value = "true"/>
				  <c:forEach items="${assignedRoleList}" var="assignedRole">
					<c:if test="${availableRole.name eq assignedRole.name}">
    <label><input type="checkbox" id="roleSelected" name="assignedRoles" value="${assignedRole.name}" checked="checked" onclick="checkRole()" />${assignedRole.name}</label>
					 <c:set var = "flag"  value = "false"/>			
					</c:if>
					</c:forEach>
						<c:if test="${flag eq true}">
						<label><input type="checkbox" id="roleSelected" name="assignedRoles" value="${availableRole.name}"  onclick="checkRole()"/>${availableRole.name}</label>
				</c:if>
				</c:forEach>
  
</div>		
	</td>
		<td><span id="roleError" style="color: red;font-size: 13px"></span></td>
	</tr>

	<tr>
		<td colspan="3" align="center">
		<input type="submit" name=deleteGroup
			value="Delete Group" style="width: 150px;" onclick="return confirm('Are you sure you want to delete?')">
		<input type="submit" name="updateGroup"
		value="Update Group" onclick="return updateGroupValidator();" style="width: 150px;"></td>
	</tr>
</table>
