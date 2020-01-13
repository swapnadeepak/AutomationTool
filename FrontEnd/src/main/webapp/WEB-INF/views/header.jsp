<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<header class="main-header"> <!-- Logo --> <a href="#" class="logo" style="padding-bottom: 30px"> <!-- mini logo for sidebar mini 50x50 pixels -->
 <span	class="logo-mini"><img src="resources/images/index.png"
				alt="logo" /></span> <!-- logo for regular state and mobile devices --> <span
			class="logo-lg"><img
				src="resources/images/estuate-inc-logo.png"
				alt="logo" style="padding-bottom: 9px;"/></span>
		</a> <!-- Header Navbar: style can be found in header.less -->
		 <nav class="navbar navbar-static-top"> <!-- Sidebar toggle button-->
		<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
			role="button"> <span class="sr-only">Toggle navigation</span> <span
			class="icon-bar"></span> <span class="icon-bar"></span> <span
			class="icon-bar"></span>
		</a> 
		<div class="navbar-custom-menu" style="margin-right: 10px;">
			<ul class="nav navbar-nav navbar-center">
						<li style="color: white;font-family: Arial Bold Italic">${appHeader}</li>
					</ul>
			<ul class="nav navbar-nav">
				<!-- User Account: style can be found in dropdown.less -->
				<li class="dropdown user user-menu"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown" style="padding-bottom: 31px;"> <img
						src="resources/images/avatar5.png"
						class="user-image" alt="User Image"> <!--<span class="hidden-xs">User</span>-->
				</a>
					<ul class="dropdown-menu">
						<!-- User image -->
						<li class="user-header"><img
							src="resources/images/avatar5.png"
							class="img-circle" alt="User Image">
							<p>
								<b>${userName}</b><br>
								<label>Role:</label>
									<c:if test="${headerUserType != null}">
										<b>&nbsp; ${headerUserType}</b><br>
									</c:if>
									<c:if test="${headerUserType == null}">
										<b>&nbsp; NA</b><br>
									</c:if>
								<label>Project: </label>
									<c:if test="${userProject != null}">
										<b>&nbsp; ${userProject.name}</b><br>
									</c:if>
									<c:if test="${userProject == null}">
										<b>&nbsp; NA</b><br>
									</c:if>
							</p></li>
						<!-- Menu Body -->
						<!-- Menu Footer-->
						<li class="user-footer">
							<div style="text-align: center;">
								<a href="logout" class="btn btn-default btn-flat">Log out</a>
							</div>
						</li>
					</ul></li>
			</ul>
		</div>
		</nav> 
		</header>