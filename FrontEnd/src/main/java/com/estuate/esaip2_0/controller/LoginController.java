package com.estuate.esaip2_0.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estuate.esaip2_0.entity.AttemptCount;
import com.estuate.esaip2_0.entity.Group;
import com.estuate.esaip2_0.entity.Organization;
import com.estuate.esaip2_0.entity.Port;
import com.estuate.esaip2_0.entity.Project;
import com.estuate.esaip2_0.entity.Role;
import com.estuate.esaip2_0.entity.UserDetail;
import com.estuate.esaip2_0.exception.ShowMessage;
import com.estuate.esaip2_0.model.AssociationTypeEnum;
import com.estuate.esaip2_0.service.ApplicationService;
import com.estuate.esaip2_0.util.Execute;
import com.estuate.esaip2_0.util.Util;
import static com.estuate.esaip2_0.util.Constants.*;


/**
 * <ul>
 * <li>Title: LoginController</li>
 * <li>Description: Handles requests for the application login page and
 * validating the user and involves tracking the login attempts.</li>
 * <li>Created by: Radhika Gopalraj</li>
 * </ul>
 */
@Controller
public class LoginController {
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	/**
	 * The applicationService
	 */
	@Autowired
	ApplicationService applicationService;
	/**
	 * The execute
	 */
	@Autowired
	private Execute execute;
	
	@Autowired
	private Util util;
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String login(HttpServletRequest request, @RequestParam(value = "message", required = false) String message,
			Model model) {
		String appHeader = util.getProjectDataProperty("applicationHeader");
		request.getSession().setAttribute("appHeader", appHeader);
		String appTitle = util.getProjectDataProperty("applicationTitle");
		request.getSession().setAttribute("appTitle", appTitle);
		model.addAttribute("message", message);
		return "index";
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "validateLogin", method = RequestMethod.GET)
	public String loginValidate() {
		return "redirect:/";
	}

	/**
	 * 
	 * @param emailId
	 * @param password
	 * @param request
	 * @param response
	 * @param model
	 * @param redir
	 * @return
	 */
	@RequestMapping(value = "/validateLogin", method = RequestMethod.POST)
	public String loginValidate(@RequestParam("emailId") String emailId, @RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response, ModelMap model, RedirectAttributes redir) {
		logger.info("start executing loginValidate  method");
		UserDetail user = null;
		try {
			if (emailId != null && !emailId.trim().isEmpty() && password != null && !password.trim().isEmpty()) {
				user = applicationService.validateEmailPassword(emailId, password);
				if (user == null) {
					UserDetail userToUpdate = (UserDetail) applicationService.getEntityByEmail(UserDetail.class,
							emailId);
					if (userToUpdate == null) {
						redir.addAttribute("message", ShowMessage.INVALID_EMAIL_ID);
						return "redirect:/";
					}
					// updating in the attempt count table
					int count = util.updateAttemptCount(userToUpdate, request);
					if (count == 0 || count == MAX_ATTEMPT_COUNT) {
						redir.addAttribute("message", ShowMessage.LOGIN_ATTEMPT_COMPLETED);
					} else {
						redir.addAttribute("message",
								ShowMessage.processErrorMessage(ShowMessage.INVALID_LOGIN_CREDENTIALS, count));
					}
					return "redirect:/";
				} else {
					String userName = user.getFirstName().concat(" " + user.getLastName());
					if(user.getUserType().getId() == 3){
						Port port = (Port) applicationService.getEntityById(Port.class, user.getId());
						request.getSession().setAttribute("userPort", String.valueOf(port.getPort()));
					}
					request.getSession().setAttribute("userName", util.stringCapital(userName));
					request.getSession().setAttribute("userMailId", user.getEmailId());
					request.getSession().setAttribute("headerUserType", user.getUserType().getDisplayName());
					int count = applicationService.getTotalLoginAttempts(AttemptCount.class, user);
					if (count != 0) {
						if (count < MAX_ATTEMPT_COUNT) {
							try {
								// Deleting the attempt counts if user logins successfully
								String deletedValue = util.deleteAttemptCount(user);
								if (deletedValue.equals("failure")) {
									logger.error(ShowMessage.ATTEMPTCOUNT_RECORD_NOT_DELETED);
								}
							} catch (Exception e) {
								logger.error("Exception : " + ShowMessage.getStackTrace(e));
							}
						}
						if (count == MAX_ATTEMPT_COUNT) {
							redir.addAttribute("message", ShowMessage.LOGIN_ATTEMPT_COMPLETED);
							return "redirect:/";
						}
					}
					Map<String, List<Role>> menuMap = new HashMap<String, List<Role>>();
		            List<Integer> groupIdList = applicationService.getAssociatedList(AssociationTypeEnum.UGA.toString(), user.getId(), 0);
					if (groupIdList == null || groupIdList.isEmpty()) {
						redir.addAttribute("message", ShowMessage.GROUP_NOT_ASSOCIATED_TO_ROLE);
						return "redirect:/";
					}
					if (groupIdList != null && !groupIdList.isEmpty()) {
						for (int id : groupIdList) {
							Group group = (Group) applicationService.getEntityById(Group.class, id);
							if (group.getType().equalsIgnoreCase("link type")) {
								List<Integer> roleIdList = applicationService
										.getAssociatedList(AssociationTypeEnum.GRA.toString(), group.getId(), 0);
								if (roleIdList == null || roleIdList.isEmpty()) {
									redir.addAttribute("message", ShowMessage.GROUP_NOT_ASSOCIATED_TO_ROLE);
									return "redirect:/";
								}
								List<Role> roleList = applicationService.getRolesByIdList(roleIdList);
								if (roleList == null || roleList.isEmpty()) {
									redir.addAttribute("message", ShowMessage.GROUP_NOT_ASSOCIATED_TO_ROLE);
									return "redirect:/";
								}
								menuMap.put(group.getDisplayName(), roleList);
							}
						}
					}
					// Setting roles for that particular user
					request.getSession().setAttribute("menu", menuMap);
					Organization org = null;
					if (user.getUserType().getId() == 3) {
						List<Integer> orgList = applicationService.getAssociatedList(AssociationTypeEnum.UOA.toString(),
								user.getId(), 0);
						if (orgList != null && !orgList.isEmpty()) {
							org = (Organization) applicationService.getEntityById(Organization.class, orgList.get(0));
							request.getSession().setAttribute("userOrganization", org.getName());
						}

						try {
							String clientIp = request.getRemoteAddr();
							execute.updateProgressProperty(org.getName(), userName, "ClientIP", clientIp,
									request);
							execute.updateProgressProperty(org.getName(), userName, "ServerIP", serverIp,
									request);
							/* Hub server should be running whenever user logins to execute test case */
							util.runHubServer(org.getName(), userName, user.getId(), request);
						} catch (Exception e) {
							logger.error("Exception : " + ShowMessage.getStackTrace(e));
						}

						List<Integer> projectIdList = applicationService
								.getAssociatedList(AssociationTypeEnum.UPA.toString(), user.getId(), 0);
						List<Project> projectList = new ArrayList<Project>();
						if (projectIdList != null && !projectIdList.isEmpty()) {
							for (int id : projectIdList) {
								Project project = (Project) applicationService.getEntityById(Project.class, id);
								projectList.add(project);
							}
							request.getSession().setAttribute("userProjectsList", projectList);
						}
						if (projectList != null && !projectList.isEmpty()) {
							if (projectList.size() == 1) {
								request.getSession().setAttribute("userProject", projectList.get(0));
								return "superadmindashboard";
							} else if (projectList.size() > 1) {
								model.addAttribute("proList", projectList);
								model.addAttribute("projectList", projectList);
								return "projectselection";
							}
						}
					}
					return "superadmindashboard";
				}
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		redir.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "redirect:/";
	}
}
