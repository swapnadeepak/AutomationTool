
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">
		<!-- sidebar menu: : style can be found in sidebar.less -->
		<ul class="sidebar-menu">
			<c:forEach items="${menu}" var="menuMap">
				<li class="treeview"><a href="#"> <i
						class="fa fa-dashboard"></i> 
						<%-- <c:if test="${menuMap.key.id == 3}">
						<span><b>Testing</b></span> 
						</c:if>
						<c:if test="${menuMap.key.id != 3}">
						<span><b>${menuMap.key.name}</b></span> 
						</c:if> --%>
							<span><b>${menuMap.key}</b></span> 
						<span
						class="pull-right-container"> <i
							class="fa fa-angle-left pull-right"></i>
					</span>
				</a>
					<ul class="treeview-menu">
						<c:forEach items="${menuMap.value}" var="role">
							<li><a href="${role.name}"><i class="fa fa-circle-o"></i>${role.displayName}</a></li>
						</c:forEach>
					</ul>
			</c:forEach>
		</ul>
	</section>
	<!-- /.sidebar -->
</aside>
<!-- /.Left side column. contains the logo and sidebar -->