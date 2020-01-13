/**
 * 
 */
package com.estuate.esaip2_0.controller;

import static com.estuate.esaip2_0.util.Constants.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estuate.esaip2_0.dto.UserDetailDTO;
import com.estuate.esaip2_0.entity.Association;
import com.estuate.esaip2_0.entity.AttemptHistory;
import com.estuate.esaip2_0.entity.Group;
import com.estuate.esaip2_0.entity.Organization;
import com.estuate.esaip2_0.entity.Port;
import com.estuate.esaip2_0.entity.Project;
import com.estuate.esaip2_0.entity.Role;
import com.estuate.esaip2_0.entity.SuperEntity;
import com.estuate.esaip2_0.entity.UserDetail;
import com.estuate.esaip2_0.entity.UserType;
import com.estuate.esaip2_0.exception.ShowMessage;
import com.estuate.esaip2_0.model.AssociationTypeEnum;
import com.estuate.esaip2_0.model.PropertyFileEnum;
import com.estuate.esaip2_0.service.ApplicationService;
import com.estuate.esaip2_0.util.Util;

/**
 * @author rgopalraj
 *
 */
@Controller
public class ApplicationController {
	
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
	
	@Autowired
	ApplicationService appService;

	@Autowired
	private Util util;
	
	/**
	 * To bind the Date according to the required format
	 * 
	 * @param binder
	 * @param locale
	 */
	@InitBinder
	public final void initBinderUsuariosFormValidator(final WebDataBinder binder, final Locale locale) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", locale);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "CreateOrganizationLink", method = RequestMethod.GET)
	public String createOrganization(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "message", required = false) String message) {
		try {
			Map<String, List<String>> dropDownmap = util.getOrgDropDownValues();
			if (dropDownmap != null && !dropDownmap.isEmpty()) {
				for (String key : dropDownmap.keySet()) {
					if (key.equals("orgTypeList"))
						model.addAttribute("orgTypeList", dropDownmap.get(key));
					if (key.equals("billingRateList"))
						model.addAttribute("billingRateList", dropDownmap.get(key));
					if (key.equals("billingPeriodList"))
						model.addAttribute("billingPeriodList", dropDownmap.get(key));
					if (key.equals("billingTypeList"))
						model.addAttribute("billingTypeList", dropDownmap.get(key));
					if (key.equals("billingCurrencyList"))
						model.addAttribute("billingCurrencyList", dropDownmap.get(key));
					if (key.equals("licenceNumList"))
						model.addAttribute("licenceNumList", dropDownmap.get(key));
				}
				if (message != null && !message.trim().isEmpty()) {
					model.addAttribute("message", message);
				}
				return "addorganization";
			}
			logger.error(ShowMessage.DROPDOWN_VALUES_EMPTY);
			return "addorganization";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "superadmindashboard";
	}

	/**
	 * 
	 * @param organization
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveOrg", method = RequestMethod.POST)
	public String saveOrganization(@ModelAttribute("organization") Organization organization,
			RedirectAttributes redir) {
		logger.info("Entered saveOrganization method");
		String orgFolderPath = null;
		boolean result = false;
		String folder = "";
		try {
			if (organization != null)
				result = appService.addEntity(organization);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		try {
			if (result) {
				orgFolderPath = util.getFolderNameForOrganization(organization.getName());
				folder = util.createFolder(orgFolderPath);
				if (folder != null && !folder.isEmpty()) {
					redir.addAttribute("message", ShowMessage.ORG_CREATE_SUCCESS);
					return "redirect:CreateOrganizationLink";
				} else {
					organization = (Organization) appService.getEntityById(Organization.class, organization.getId());
					appService.deleteEntity(organization);
					util.deleteCopiedFolder(folder);
					redir.addAttribute("message", ShowMessage.ORG_FOLDER_CREATE_FAILED);
					return "redirect:CreateOrganizationLink";
				}
			} else {
				redir.addAttribute("message", ShowMessage.ORG_CREATE_FAILED);
				return "redirect:CreateOrganizationLink";
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		logger.info("Execution completed successfully of saveOrganization method");
		redir.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "redirect:CreateOrganizationLink";
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "CreateAdminLink", method = RequestMethod.GET)
	public String createAdmin(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "message", required = false) String message) {
		try {
			List<SuperEntity> userTypeList = appService.getEntityList(UserType.class);
			if (userTypeList != null && !userTypeList.isEmpty()) {
				for(SuperEntity superType : userTypeList) {
					UserType userType = (UserType)superType;
					if(userType.getId() == 2) {
						model.addAttribute("userType", userType);
					}
				}
			} else {
				logger.warn("userTypeList is empty");
			}
			List<SuperEntity> orgList = appService.getEntityList(Organization.class);
			if (orgList != null && !orgList.isEmpty()) {
				model.addAttribute("orgList", orgList);
			} else {
				logger.warn("orgList is empty");
			}
			List<SuperEntity> availablegroupList = appService.getEntityList(Group.class);
			if (availablegroupList != null && !availablegroupList.isEmpty()) {
				model.addAttribute("availablegroupList", availablegroupList);
			} else {
				logger.warn("availablegroupList is empty");
			}
			if (message != null && !message.trim().isEmpty()) {
				model.addAttribute("message", message);
			}
			return "addadmin";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		logger.error(ShowMessage.DROPDOWN_VALUES_EMPTY);
		return "addadmin";
	}

	/**
	 * 
	 * @param user
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "saveAdmin", method = RequestMethod.POST)
	public String saveAdmin(@ModelAttribute("userDto") UserDetailDTO userDto, ModelMap model,
			HttpServletRequest request, BindingResult results, RedirectAttributes redir) {
		logger.info("Entered saveAdmin method");
		if (userDto.getAssignedGroup() == null || userDto.getAssignedGroup().isEmpty()) {
			redir.addAttribute("message", ShowMessage.ASSIGNED_GROUP_EMPTY);
			return "redirect:CreateAdminLink";
		}
		boolean result = false;
		UserDetail userDetail = null;
		try {
			userDetail = util.mapDtoWithCommonFeildsOnCreate(userDto, request);
			if (userDetail != null) {
				result = appService.addEntity(userDetail);
			} else {
				logger.error(ShowMessage.USERDETAIL_OBJECT_NULL);
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		if (result) {
			try {
				userDetail = (UserDetail) appService.getEntityById(UserDetail.class, userDetail.getId());
				boolean isCompleteCreateAdmin = false;
				List<Association> associationListToremove = null;
				// adding UOA to association table
				Map<String, Object> resultUOAMap = util.addUOA(userDto, userDetail.getId());
				if (resultUOAMap != null && !resultUOAMap.isEmpty()) {
					for (String key : resultUOAMap.keySet()) {
						if (key.equals("ASSOCIATION_TO_BE_DELETED"))
							associationListToremove = (List<Association>) resultUOAMap.get(key);
						if (key.equals("PROCEED_CREATE_USER"))
							isCompleteCreateAdmin = (boolean) resultUOAMap.get(key);
					}
				}
				// adding UGA to association table
				Map<String, Object> resultUGAMap = util.addUGA(userDto, userDetail.getId(), associationListToremove);
				if (resultUGAMap != null && !resultUGAMap.isEmpty()) {
					for (String key : resultUGAMap.keySet()) {
						if (key.equals("ASSOCIATION_TO_BE_DELETED"))
							associationListToremove = (List<Association>) resultUGAMap.get(key);
						if (key.equals("PROCEED_CREATE_USER"))
							isCompleteCreateAdmin = (boolean) resultUGAMap.get(key);
					}
				}
				if (isCompleteCreateAdmin) {
					redir.addAttribute("message", ShowMessage.ADMIN_CREATE_SUCCESS);
					return "redirect:CreateAdminLink";
				} else {
					try {
						appService.deleteEntity(userDetail);
						if (associationListToremove != null && !associationListToremove.isEmpty()) {
							for (Association remove : associationListToremove) {
								appService.deleteEntity(remove);
							}
						}
					} catch (Exception e) {
						logger.error("Exception : " + ShowMessage.getStackTrace(e));
					}
					redir.addAttribute("message", ShowMessage.ASSOCIATION_CREATE_FAILED_FOR_ADMIN);
					return "redirect:CreateAdminLink";
				}
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
			redir.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
			return "redirect:CreateAdminLink";
		}
		redir.addAttribute("message", ShowMessage.ADMIN_CREATE_FAILED);
		return "redirect:CreateAdminLink";
	}

	/**
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "CreateUserLink", method = RequestMethod.GET)
	public String createUser(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "message", required = false) String message) {
		try {
			
			//To get userType
			List<SuperEntity> userTypeList = appService.getEntityList(UserType.class);
			if (userTypeList != null && !userTypeList.isEmpty()) {
				for(SuperEntity superType : userTypeList) {
					UserType userType = (UserType)superType;
					if(userType.getId() == 3) {
						model.addAttribute("userType", userType);
					}
				}
			} 
			Organization org = null;
			List<String> projNamelist = new ArrayList<String>();
			
			//To get Organization Name
			UserDetail user = (UserDetail) appService.getEntityByEmail(UserDetail.class,
					(String) request.getSession().getAttribute("userMailId"));
			if (user != null) {
				List<Integer> orgList = appService.getAssociatedList(AssociationTypeEnum.UOA.toString(), user.getId(),
						0);
				if (orgList != null && !orgList.isEmpty()) {
					org = (Organization) appService.getEntityById(Organization.class, orgList.get(0));
					model.addAttribute("organization", org.getName());
				}
			}

			if (org != null) {
				//To get projects associated to the organization
				List<Integer> projectIdList = appService.getAssociatedList(AssociationTypeEnum.OPA.toString(),
						org.getId(), 0);
				if (projectIdList != null && !projectIdList.isEmpty()) {
					for (int id : projectIdList) {
						Project project = (Project) appService.getEntityById(Project.class, id);
						projNamelist.add(project.getName());
					}
				}
				model.addAttribute("projectList", projNamelist);
			}
			//To get all group list
			List<SuperEntity> availablegroupList = appService.getEntityList(Group.class);
			if (availablegroupList != null && !availablegroupList.isEmpty()) {
				model.addAttribute("availablegroupList", availablegroupList);
			}
			if (message != null && !message.trim().isEmpty()) {
				model.addAttribute("message", message);
			}
			return "adduser";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		logger.error(ShowMessage.DROPDOWN_VALUES_EMPTY);
		return "adduser";
	}

	/**
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("userDto") UserDetailDTO userDto, HttpServletRequest request,
			BindingResult results, RedirectAttributes redir) {
		logger.info("Entered saveUser method");
		if (userDto.getAssignedGroup() == null || userDto.getAssignedGroup().isEmpty()) {
			redir.addAttribute("message", ShowMessage.ASSIGNED_GROUP_EMPTY);
			return "redirect:CreateUserLink";
		}
		boolean result = false;
		boolean isFilesCopied = false;
		boolean isUserPortCreated = false;
		String orgName = "";
		UserDetail userDetail = null;
		Port port = null;
		String userFolderPath = "";
		try {
			orgName = userDto.getOrganization();
			userDetail = util.mapDtoWithCommonFeildsOnCreate(userDto, request);
			if (userDetail != null) {
				result = appService.addEntity(userDetail);
			} else {
				logger.error(ShowMessage.USERDETAIL_OBJECT_NULL);
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		if (result) {
			boolean isCompleteCreateUser = false;
			List<Association> associationListToremove = null;
			try {
				userDetail = (UserDetail) appService.getEntityById(UserDetail.class, userDetail.getId());
				// adding UOA to association table
				Map<String, Object> resultUOAMap = util.addUOA(userDto, userDetail.getId());
				if (resultUOAMap != null && !resultUOAMap.isEmpty()) {
					for (String key : resultUOAMap.keySet()) {
						if (key.equals("ASSOCIATION_TO_BE_DELETED"))
							associationListToremove = (List<Association>) resultUOAMap.get(key);
						if (key.equals("PROCEED_CREATE_USER"))
							isCompleteCreateUser = (boolean) resultUOAMap.get(key);
					}
				}
				// adding UGA to association table
				Map<String, Object> resultUGAMap = util.addUGA(userDto, userDetail.getId(), associationListToremove);
				if (resultUGAMap != null && !resultUGAMap.isEmpty()) {
					for (String key : resultUGAMap.keySet()) {
						if (key.equals("ASSOCIATION_TO_BE_DELETED"))
							associationListToremove = (List<Association>) resultUGAMap.get(key);
						if (key.equals("PROCEED_CREATE_USER"))
							isCompleteCreateUser = (boolean) resultUGAMap.get(key);
					}
				}
				// adding UPA to association table
				Map<String, Object> resultUPAMap = util.addUPA(userDto, userDetail.getId(), associationListToremove);
				if (resultUPAMap != null && !resultUPAMap.isEmpty()) {
					for (String key : resultUPAMap.keySet()) {
						if (key.equals("ASSOCIATION_TO_BE_DELETED"))
							associationListToremove = (List<Association>) resultUPAMap.get(key);
						if (key.equals("PROCEED_CREATE_USER"))
							isCompleteCreateUser = (boolean) resultUPAMap.get(key);
					}
				}
				// creating unique port for the user
				try {
					port = new Port();
					port.setUser(userDetail);
					isUserPortCreated = appService.addEntity(port);
				} catch (Exception e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
				// creating user folder and copying necessary files
				String userFolderName = userDetail.getFirstName() + " " + userDetail.getLastName();
				String folderName = util.getFolderNameForUsers(orgName).concat("\\" + userFolderName);
				userFolderPath = util.createFolder(folderName);
				isFilesCopied = util.copyUserFiles(request, userFolderPath, port.getPort());  //TODO port should be passed
				// updating in generic.properties
				util.updatePropertyOnUserCreate(orgName, userDetail.getFirstName().concat(" " + userDetail.getLastName()),
						request);
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
			if (isCompleteCreateUser && isFilesCopied && isUserPortCreated) {
				redir.addAttribute("message", ShowMessage.USER_CREATE_SUCCESS);
				return "redirect:CreateUserLink";
			} else {
				try {
					port = (Port) appService.getEntityById(Port.class, port.getUser().getId());
					if (port != null) {
						appService.deleteEntity(port);
					}
					appService.deleteEntity(userDetail);
					if (associationListToremove != null && !associationListToremove.isEmpty()) {
						for (Association remove : associationListToremove) {
							appService.deleteEntity(remove);
						}
					}
					util.deleteCopiedFolder(userFolderPath);
				} catch (Exception e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
				redir.addAttribute("message", ShowMessage.USER_FOLDER_CREATE_FAILED);
				return "redirect:CreateUserLink";
			}
		}
		redir.addAttribute("message", ShowMessage.USER_CREATE_FAILED);
		return "redirect:CreateUserLink";
	}

	/**
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "projectList", headers = "Accept=*/*", method = RequestMethod.GET) // NOT USED
	public @ResponseBody List<String> getProjectByOrganization(
			@RequestParam(value = "orgName", required = true) String orgName) {
		List<String> projNamelist = new ArrayList<String>();
		Project project = null;
		try {
			Organization organization = (Organization) appService.getEntityByName(Organization.class, orgName);
			if (organization != null) {
				List<Integer> projectIdList = appService.getAssociatedList(AssociationTypeEnum.OPA.toString(),
						organization.getId(), 0);
				if (projectIdList != null && !projectIdList.isEmpty()) {
					for (int id : projectIdList) {
						project = (Project) appService.getEntityById(Project.class, id);
						projNamelist.add(project.getName());
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return projNamelist;
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "CreateProjectLink", method = RequestMethod.GET)
	public String createProject(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "message", required = false) String message) {
		List<String> projectTypeList = null;
		try {
			Properties prop = util
					.getProperties(PropertyFileEnum.UTILITY_PROPERTY_FILENAME.getProperty() + ".properties");
			if (prop.entrySet() != null && !prop.entrySet().isEmpty()) {
				for (Map.Entry<Object, Object> entry : prop.entrySet()) {
					if (entry.getKey().equals("projectType")) {
						String str = (String) entry.getValue();
						projectTypeList = Arrays.asList(str.split("\\s*,\\s*"));
					}
				}
			}
			if (message != null && !message.trim().isEmpty()) {
				model.addAttribute("message", message);
			}
			model.addAttribute("projectTypeList", projectTypeList);
			return "addproject";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		logger.error(ShowMessage.DROPDOWN_VALUES_EMPTY);
		return "addproject";
	}

	/**
	 * 
	 * @param project
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "save_project", method = RequestMethod.POST)
	public String saveProject(@ModelAttribute("project") Project project, ModelMap model, HttpServletRequest request,
			RedirectAttributes redir) {
		logger.info("Entered saveProject method");
		boolean result = false;
		boolean isOPACreated = false;
		boolean isProjectFoldersCreated = false;
		String folderName = "";
		Date date = new Date();
		try {
			project.setCreatedBy((String) request.getSession().getAttribute("userName"));
			project.setCreatedDate(date);
			project.setModifiedBy((String) request.getSession().getAttribute("userName"));
			project.setModifiedDate(date);
			result = appService.addEntity(project);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		if (result) {
			boolean isCompleteCreateProject = true;
			List<Association> associationListToremove = new ArrayList<Association>();
			try {
				project = (Project) appService.getEntityById(Project.class, project.getId());
				Association association = null;
				UserDetail user = (UserDetail) appService.getEntityByEmail(UserDetail.class,
						(String) request.getSession().getAttribute("userMailId"));
				if (user != null) {
					// adding OPA to association table
					List<Integer> orgId = appService.getAssociatedList(AssociationTypeEnum.UOA.toString(), user.getId(),
							0);
					if (orgId != null && !orgId.isEmpty()) {
						association = new Association(AssociationTypeEnum.OPA.toString(), project.getId(),
								orgId.get(0));
						isOPACreated = appService.addEntity(association);
						if (isOPACreated) {
							associationListToremove.add(association);
						} else {
							isCompleteCreateProject = false;
						}
						logger.info("Association OPA done");
					}
					try {
						Organization organization = (Organization) appService.getEntityById(Organization.class,
								orgId.get(0));
						folderName = util.getFolderNameForProjects(organization.getName())
								.concat("\\" + project.getName());
						isProjectFoldersCreated = util.createFoldersForProject(folderName);
					} catch (Exception e) {
						logger.error("Exception : " + ShowMessage.getStackTrace(e));
					}
				}
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
			if (isCompleteCreateProject && isProjectFoldersCreated) {
				redir.addAttribute("message", ShowMessage.PROJECT_CREATE_SUCCESS);
				return "redirect:CreateProjectLink";
			} else {
				try {
					appService.deleteEntity(project);
					if (associationListToremove != null && !associationListToremove.isEmpty()) {
						for (Association remove : associationListToremove) {
							appService.deleteEntity(remove);
						}
					}
					util.deleteCopiedFolder(folderName);
				} catch (Exception e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
				redir.addAttribute("message", ShowMessage.PROJECT_FOLDER_CREATE_FAILED);
				return "redirect:CreateProjectLink";
			}
		} else {
			redir.addAttribute("message", ShowMessage.PROJECT_CREATE_FAILED);
			return "redirect:CreateProjectLink";
		}
	}

	/**
	 * 
	 * @param orgName
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "checkNameExistence")
	public void checkNameExistence(@RequestParam("name") String name, @RequestParam("className") String className,
			HttpServletResponse resp) throws IOException {
		try {
			boolean result = appService.checkNameExistence(className, name);
			if (result)
				resp.getWriter().write("'" + name + "' already exists. Please try another.");
			else
				resp.getWriter().write("");
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "checkEmailExistence")
	public void checkEmailExistence(@RequestParam("emailId") String emailId,
			@RequestParam("className") String className, HttpServletResponse resp) throws IOException {
		try {
			boolean result = appService.checkEmailExistence(className, emailId);
			if (result)
				resp.getWriter().write("'" + emailId + "' already exists. Please try another.");
			else
				resp.getWriter().write("");
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "UpdateOrganizationLink", method = RequestMethod.GET)
	public String updateOrganizationLink(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "message", required = false) String message) {
		try {
			List<SuperEntity> orgList = appService.getEntityList(Organization.class);
			if (orgList != null && !orgList.isEmpty())
				model.addAttribute("orgList", orgList);
			if (message != null && !message.trim().isEmpty()) {
				model.addAttribute("message", message);
			}
			return "updateorganization";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		logger.error(ShowMessage.ORGANIZATION_LIST_NULL);
		return "updateorganization";
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/getOrgAjax", method = RequestMethod.POST)
	public String getOrgAjax(@RequestParam("orgName") String orgName, ModelMap model) {
		if (orgName == null || orgName.isEmpty()) {
			return "error";
		}
		try {
			Organization organization = (Organization) appService.getEntityByName(Organization.class, orgName);
			Map<String, List<String>> dropDownmap = util.getOrgDropDownValues();
			if (dropDownmap != null && !dropDownmap.isEmpty()) {
				for (String key : dropDownmap.keySet()) {
					if (key.equals("orgTypeList"))
						model.addAttribute("orgTypeList", dropDownmap.get(key));
					if (key.equals("billingRateList"))
						model.addAttribute("billingRateList", dropDownmap.get(key));
					if (key.equals("billingPeriodList"))
						model.addAttribute("billingPeriodList", dropDownmap.get(key));
					if (key.equals("billingTypeList"))
						model.addAttribute("billingTypeList", dropDownmap.get(key));
					if (key.equals("billingCurrencyList"))
						model.addAttribute("billingCurrencyList", dropDownmap.get(key));
					if (key.equals("licenceNumList"))
						model.addAttribute("licenceNumList", dropDownmap.get(key));
				}
			}
			model.addAttribute("organization", organization);
			return "updateOrgTable";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		logger.error(ShowMessage.DROPDOWN_VALUES_EMPTY);
		return "updateOrgTable";
	}

	/**
	 * 
	 * @param emailId
	 * @param className
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "checkEmailOnUpdate")
	public void checkEmailOnUpdate(@RequestParam("emailId") String emailId, @RequestParam("className") String className,
			@RequestParam("id") int id, HttpServletResponse resp) throws IOException {
		try {
			SuperEntity entity = appService.checkEmailOnUpdate(className, emailId);
			if (entity != null) {
				if (className.equals("UserDetail")) {
					UserDetail user = (UserDetail) appService.getEntityById(UserDetail.class, id);
					UserDetail userDetail = (UserDetail) entity;
					if (user.getEmailId().equalsIgnoreCase(userDetail.getEmailId())) {
						resp.getWriter().write("");
					} else {
						resp.getWriter().write("'" + emailId + "' already exists. Please try another.");
					}
				}
				if (className.equals("Organization")) {
					Organization org = (Organization) appService.getEntityById(Organization.class, id);
					Organization organization = (Organization) entity;
					if (org.getContactEmailId().equalsIgnoreCase(organization.getContactEmailId())) {
						resp.getWriter().write("");
					} else {
						resp.getWriter().write("'" + emailId + "' already exists. Please try another.");
					}
				}
			} else {
				resp.getWriter().write("");
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
	}

	/**
	 * 
	 * @param name
	 * @param className
	 * @param id
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "checkNameOnUpdate")
	public void checkNameOnUpdate(@RequestParam("name") String name, @RequestParam("className") String className,
			@RequestParam("id") int id, HttpServletResponse resp) throws IOException {
		try {
			SuperEntity entity = appService.checkNameOnUpdate(className, name);
			if (entity != null) {
				if (className.equals("Group")) {
					Group group = (Group) appService.getEntityById(Group.class, id);
					Group testGroup = (Group) entity;
					if (group.getName().equalsIgnoreCase(testGroup.getName())) {
						resp.getWriter().write("");
					} else {
						resp.getWriter().write("'" + name + "' already exists. Please try another.");
					}
				}
			} else {
				resp.getWriter().write("");
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateOrganization", method = RequestMethod.POST)
	public String updateOrganization(@ModelAttribute("organization") Organization organization,
			RedirectAttributes redir) {
		logger.info("Entered updateOrg method");
		try {
			boolean result = appService.updateEntity(organization);
			if (result) {
				redir.addAttribute("message", ShowMessage.ORG_UPDATE_SUCCESS);
				return "redirect:UpdateOrganizationLink";
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		redir.addAttribute("message", ShowMessage.ORG_UPDATE_FAILED);
		return "redirect:UpdateOrganizationLink";
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "UpdateAdminLink", method = RequestMethod.GET)
	public String UpdateAdminLink(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "message", required = false) String message) {
		try {
			List<SuperEntity> orgList = appService.getEntityList(Organization.class);
			if (orgList != null && !orgList.isEmpty()) {
				model.addAttribute("orgList", orgList);
			}
			if (message != null && !message.isEmpty()) {
				model.addAttribute("message", message);
			}
			return "updateadmin";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		logger.error(ShowMessage.DROPDOWN_VALUES_EMPTY);
		return "updateadmin";
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "adminList", headers = "Accept=*/*", method = RequestMethod.GET)
	public @ResponseBody List<UserDetail> getadminListByorgId(
			@RequestParam(value = "orgId", required = true) int orgId) {
		List<UserDetail> adminList = new ArrayList<UserDetail>();
		try {
			UserDetail userDetail = null;
			List<Integer> userIdList = appService.getAssociatedList(AssociationTypeEnum.UOA.toString(), 0, orgId);
			if (userIdList != null && !userIdList.isEmpty()) {
				for (int id : userIdList) {
					userDetail = (UserDetail) appService.getEntityById(UserDetail.class, id);
					if (userDetail.getUserType().getId() == 2) {
						adminList.add(userDetail);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return adminList;
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/getAdminAjax", method = RequestMethod.POST)
	public String getAdminAjax(@RequestParam(value = "id", required = true) int id, ModelMap model,
			HttpServletRequest request) {
		if (id == 0) {
			return "error";
		}
		try {
			UserDetail user = (UserDetail) appService.getEntityById(UserDetail.class, id);
			if (user != null) {
				model.addAttribute("admin", user);
			}
			// To get organization of the admin
			List<Integer> orgId = appService.getAssociatedList(AssociationTypeEnum.UOA.toString(), user.getId(), 0);
			if (orgId != null && !orgId.isEmpty()) {
				Organization org = (Organization) appService.getEntityById(Organization.class, orgId.get(0));
				model.addAttribute("organizationName", org.getName());
			}
			// To get groups which belongs to admin
			List<Integer> groupIdList = appService.getAssociatedList(AssociationTypeEnum.UGA.toString(), user.getId(),
					0);
			List<Group> groupList = new ArrayList<Group>();
			if (groupIdList != null && !groupIdList.isEmpty()) {
				for (int groupId : groupIdList) {
					Group group = (Group) appService.getEntityById(Group.class, groupId);
					groupList.add(group);
				}
				model.addAttribute("groupList", groupList);
			}
			List<SuperEntity> userTypeList = appService.getEntityList(UserType.class);
			if (userTypeList != null && !userTypeList.isEmpty()) {
				for(SuperEntity superType : userTypeList) {
					UserType userType = (UserType)superType;
					if(userType.getId() == 2) {
						model.addAttribute("userType", userType);
					}
				}
			} 
			List<SuperEntity> availablegroupList = appService.getEntityList(Group.class);
			if (availablegroupList != null && !availablegroupList.isEmpty()) {
				model.addAttribute("availablegroupList", availablegroupList);
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return "updateAdminTable";
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "processUpdateAdmin", params = "updateAdmin", method = RequestMethod.POST)
	public String updateAdmin(@ModelAttribute("admin") UserDetailDTO admin, BindingResult results,
			RedirectAttributes redir, HttpServletRequest request) {
		logger.info("Entered updateAdmin method");
		boolean isAdminUpdated = false;
		boolean isUpdateUGA = false;
		boolean isUpdateUOA = false;
		Organization existingOrg = null;
		ArrayList<Group> currentGroupList = null;
		ArrayList<Group> existingGroupList = null;
		UserDetail userDetail = null;
		try {
			// Deciding whether to update UOA
			Map<String, Object> resultUOAMap = util.checkForUpdateUOA(admin);
			if (resultUOAMap != null && !resultUOAMap.isEmpty()) {
				for (String key : resultUOAMap.keySet()) {
					if (key.equals("EXISTING_ORG"))
						existingOrg = (Organization) resultUOAMap.get(key);
					if (key.equals("IS_UPDATE_UOA"))
						isUpdateUOA = (boolean) resultUOAMap.get(key);
				}
			}
			// Deciding whether to update UGA
			Map<String, Object> resultUGAMap = util.checkForUpdateUGA(admin);
			if (resultUOAMap != null && !resultUOAMap.isEmpty()) {
				for (String key : resultUGAMap.keySet()) {
					if (key.equals("EXISTING_GROUP_LIST"))
						existingGroupList = (ArrayList<Group>) resultUGAMap.get(key);
					if (key.equals("CURRENT_GROUP_LIST"))
						currentGroupList = (ArrayList<Group>) resultUGAMap.get(key);
					if (key.equals("IS_UPDATE_UGA"))
						isUpdateUGA = (boolean) resultUGAMap.get(key);
				}
			}
			userDetail = util.mapDtoWithCommonFeildsOnUpdate(admin, request);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		if (userDetail != null) {
			List<Association> associationTobeDeletedList = null;
			List<Association> associationTobeAddedList = null;
			boolean proceedUpdateAdmin = true;
			try {
				// updating UOA
				if (isUpdateUOA) {
					Map<String, Object> updatedUOAMap = util.updateUOA(admin, existingOrg);
					if (updatedUOAMap != null && !updatedUOAMap.isEmpty()) {
						for (String key : updatedUOAMap.keySet()) {
							if (key.equals("ASSOCIATION_TO_BE_ADDED"))
								associationTobeAddedList = (List<Association>) updatedUOAMap.get(key);
							if (key.equals("ASSOCIATION_TO_BE_DELETED"))
								associationTobeDeletedList = (List<Association>) updatedUOAMap.get(key);
							if (key.equals("PROCEED_UPDATE_USER"))
								proceedUpdateAdmin = (boolean) updatedUOAMap.get(key);
						}
					}
				}
				// updating UGA
				if (isUpdateUGA) {
					Map<String, Object> updatedUGAMap = util.updateUGA(admin, currentGroupList, existingGroupList,
							associationTobeDeletedList, associationTobeAddedList);
					if (updatedUGAMap != null && !updatedUGAMap.isEmpty()) {
						for (String key : updatedUGAMap.keySet()) {
							if (key.equals("ASSOCIATION_TO_BE_ADDED"))
								associationTobeAddedList.addAll((List<Association>) updatedUGAMap.get(key));
							if (key.equals("ASSOCIATION_TO_BE_DELETED"))
								associationTobeDeletedList.addAll((List<Association>) updatedUGAMap.get(key));
							if (key.equals("PROCEED_UPDATE_USER"))
								proceedUpdateAdmin = (boolean) updatedUGAMap.get(key);
						}
					}
				}
				if (proceedUpdateAdmin) {
					isAdminUpdated = appService.updateEntity(userDetail);
					if (isAdminUpdated) {
						redir.addAttribute("message", ShowMessage.ADMIN_UPDATE_SUCCESS);
					} else {
						try {
							if (associationTobeDeletedList != null && !associationTobeDeletedList.isEmpty()) {
								for (Association removeEntity : associationTobeDeletedList) {
									appService.deleteEntity(removeEntity);
								}
							}
							if (associationTobeAddedList != null && !associationTobeAddedList.isEmpty()) {
								for (Association addEntity : associationTobeAddedList) {
									appService.addEntity(addEntity);
								}
							}
						} catch (Exception e) {
							logger.error("Exception : " + ShowMessage.getStackTrace(e));
						}
						redir.addAttribute("message", ShowMessage.ADMIN_UPDATE_FAILED);
					}
				} else {
					redir.addAttribute("message", ShowMessage.ADMIN_ASSOCIATION_UPDATE_FAILED);
				}
				return "redirect:UpdateAdminLink";
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
				redir.addAttribute("message", ShowMessage.ADMIN_UPDATE_FAILED);
			}
			return "redirect:UpdateAdminLink";
		} else {
			logger.error("Exception : " + ShowMessage.USERDETAIL_OBJECT_NULL);
			redir.addAttribute("message", ShowMessage.ADMIN_UPDATE_FAILED);
		}
		return "redirect:UpdateAdminLink";
	}

	/**
	 * 
	 * @param userDto
	 * @param results
	 * @param request
	 * @param redir
	 * @return
	 */
	@RequestMapping(value = "processUpdateAdmin", params = "resetPassword", method = RequestMethod.POST)
	public String resetPassword(@ModelAttribute("admin") UserDetailDTO userDto, BindingResult results,
			HttpServletRequest request, RedirectAttributes redir) {
		logger.info("Entered resetPassword method");
		UserDetail user = null;
		String value = "";
		try {
			user = util.toUserDetailEntity(userDto, request);
			value = util.deleteAttemptCount(user);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		if (value.equals("success")) {
			redir.addAttribute("message", ShowMessage.RESET_PASSWORD_SUCCESS);
		} else if (value.equals("failure")) {
			redir.addAttribute("message", ShowMessage.RESET_PASSWORD_FAILED);
		} else if (value.trim().equals("")) {
			redir.addAttribute("message", ShowMessage.NO_RECORDS_FOUND_TO_RESET);
		}
		return "redirect:UpdateAdminLink";
	}

	/**
	 * 
	 * @param userDto
	 * @param results
	 * @param model
	 * @param redir
	 * @param message
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "processUpdateAdmin", params = "deleteAdmin", method = RequestMethod.POST)
	public String deleteAdmin(@ModelAttribute("userDto") UserDetailDTO userDto, BindingResult results, ModelMap model,
			RedirectAttributes redir, @RequestParam(value = "message", required = false) String message) {
		logger.info("Entered deleteUser method");
		List<Association> associationListToAdd = new ArrayList<Association>();
		boolean isAdminDeleted = false;
		Association association = null;
		boolean isUGADeleted = true;
		boolean isUOADeleted = true;
		if (userDto == null) {
			redir.addAttribute("message", ShowMessage.USERDETAIL_OBJECT_NULL);
			return "redirect:UpdateAdminLink";
		} else {
			try {
				UserDetail user = (UserDetail) appService.getEntityById(UserDetail.class, userDto.getId());
				isAdminDeleted = appService.deleteEntityById(UserDetail.class, userDto.getId()); // Delete user
				if (isAdminDeleted) {
					// Delete UGA association
					List<Association> UGAList = appService.getAssociationById(AssociationTypeEnum.UGA.toString(),
							userDto.getId(), 0);
					if (UGAList != null && !UGAList.isEmpty()) {
						for (Association entity : UGAList) {
							isUGADeleted = appService.deleteEntity(entity);
							if (isUGADeleted) {
								associationListToAdd.add(entity);
							} else {
								isUGADeleted = false;
							}
						}
					}
					// Delete UOA association
					List<Integer> orgList = appService.getAssociatedList(AssociationTypeEnum.UOA.toString(),
							userDto.getId(), 0);
					Organization org = (Organization) appService.getEntityById(Organization.class, orgList.get(0));

					List<Association> UOAList = appService.getAssociationById(AssociationTypeEnum.UOA.toString(),
							userDto.getId(), 0);
					if (UOAList != null && !UOAList.isEmpty()) {
						for (Association entity : UOAList) {
							isUOADeleted = appService.deleteEntity(entity);
							if (isUOADeleted) {
								associationListToAdd.add(entity);
							} else {
								isUOADeleted = false;
							}
						}
					}
					if (isUOADeleted && isUGADeleted) {
						redir.addAttribute("message", ShowMessage.ADMIN_DELETE_SUCCESS);
					} else {
						appService.addEntity(user);
						UserDetail user1 = (UserDetail) appService.getEntityById(UserDetail.class, user.getId());
						if (associationListToAdd != null && !associationListToAdd.isEmpty()) {
							for (Association entity : associationListToAdd) {
								entity.getCompositeId().setMainID(user1.getId());
								appService.addEntity(entity);
							}
						}
						redir.addAttribute("message", ShowMessage.ADMIN_ASSOCIATION_DELETE_FAILED);
					}
				} else {
					redir.addAttribute("message", ShowMessage.ADMIN_DELETE_FAILED);
				}
				return "redirect:UpdateAdminLink";
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		return "redirect:UpdateAdminLink";
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "UpdateProjectLink", method = RequestMethod.GET)
	public String updateProjectLink(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "message", required = false) String message) {
		try {
			Organization org = null;
			List<Project> projectList = new ArrayList<Project>();
			UserDetail user = (UserDetail) appService.getEntityByEmail(UserDetail.class,
					(String) request.getSession().getAttribute("userMailId"));
			if (user != null) {
				List<Integer> orgList = appService.getAssociatedList(AssociationTypeEnum.UOA.toString(), user.getId(),
						0);
				if (orgList != null && !orgList.isEmpty()) {
					org = (Organization) appService.getEntityById(Organization.class, orgList.get(0));
					model.addAttribute("organization", org.getName());
				}
			}

			if (org != null) {
				List<Integer> projectIdList = appService.getAssociatedList(AssociationTypeEnum.OPA.toString(), org.getId(),
						0);
				if (projectIdList != null && !projectIdList.isEmpty()) {
					for (int id : projectIdList) {
						Project Project = (Project) appService.getEntityById(Project.class, id);
							projectList.add(Project);
					}
				}
				model.addAttribute("projectList", projectList);
			}
			
			
			if (message != null && !message.isEmpty()) {
				model.addAttribute("message", message);
			}
			return "updateproject";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("message", ShowMessage.ORGANIZATION_LIST_NULL);
		return "updateproject";
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "getProjectList", headers = "Accept=*/*", method = RequestMethod.GET)        //NOT USED
	public @ResponseBody List<Project> getProjectListByorgId( 
			@RequestParam(value = "orgId", required = true) int orgId) {
		List<Project> projectList = new ArrayList<Project>();
		Project project = null;
		try {
			List<Integer> projectIdList = appService.getAssociatedList(AssociationTypeEnum.OPA.toString(), orgId, 0);
			if (projectIdList != null && !projectIdList.isEmpty()) {
				for (int id : projectIdList) {
					project = (Project) appService.getEntityById(Project.class, id);
					projectList.add(project);
				}
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return projectList;
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/getProjectAjax", method = RequestMethod.POST)
	public String getProject(@RequestParam(value = "id", required = true) int id, ModelMap model) {
		if (id == 0) {
			return "error";
		}
		List<String> projectTypeList = null;
		try {
			Project project = (Project) appService.getEntityById(Project.class, id);
			model.addAttribute("project", project);
			Properties prop = util.getProperties("utility.properties");
			if (prop.entrySet() != null && !prop.entrySet().isEmpty()) {
				for (Map.Entry<Object, Object> entry : prop.entrySet()) {
					if (entry.getKey().equals("projectType")) {
						String str = (String) entry.getValue();
						projectTypeList = Arrays.asList(str.split("\\s*,\\s*"));
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("projectTypeList", projectTypeList);
		return "updateProjectTable";
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "processUpdateProject", params = "updateProject", method = RequestMethod.POST)
	public String updateProject(@ModelAttribute("project") Project project, BindingResult results,
			RedirectAttributes redir, HttpServletRequest request) {
		logger.info("Entered updateProject method");
		try {
			project.setModifiedBy((String) request.getSession().getAttribute("userName"));
			project.setModifiedDate(new Date());
			boolean result = appService.updateEntity(project);
			if (result) {
				redir.addAttribute("message", ShowMessage.PROJECT_UPDATE_SUCCESS);
				return "redirect:UpdateProjectLink";
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		redir.addAttribute("message", ShowMessage.PROJECT_UPDATE_FAILED);
		return "redirect:UpdateProjectLink";
	}

	/**
	 * 
	 * @param project
	 * @param results
	 * @param model
	 * @param redir
	 * @param message
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "processUpdateProject", params = "deleteProject", method = RequestMethod.POST)
	public String deleteProject(@ModelAttribute("project") Project project, BindingResult results, ModelMap model,
			RedirectAttributes redir, @RequestParam(value = "message", required = false) String message) {
		logger.info("Entered deleteUser method");
		List<Association> associationListToAdd = new ArrayList<Association>();
		boolean isProjectDeleted = false;
		Association association = null;
		boolean isOPADeleted = true;
		boolean isFolderDeleted = true;
		if (project == null) {
			redir.addAttribute("message", ShowMessage.PROJECT_OBJECT_NULL);
			return "redirect:UpdateProjectLink";
		} else {
			try {
				isProjectDeleted = appService.deleteEntity(project); // Delete project
				if (isProjectDeleted) {
					List<Integer> orgId = appService.getAssociatedList(AssociationTypeEnum.OPA.toString(), 0,
							project.getId());
					// Delete project folder
					Organization organization = (Organization) appService.getEntityById(Organization.class,
							orgId.get(0));
					// Delete OPA association
					List<Association> OPAList = appService.getAssociationById(AssociationTypeEnum.OPA.toString(), 0,
							project.getId());
					if (OPAList != null && !OPAList.isEmpty()) {
						for (Association entity : OPAList) {
							isOPADeleted = appService.deleteEntity(entity);
							if (isOPADeleted) {
								associationListToAdd.add(entity);
							} else {
								isOPADeleted = false;
							}
						}
					}
					String folderName = util.getFolderNameForProjects(organization.getName())
							.concat("\\" + project.getName());
					File srcFile = new File(folderName);
					File destFile = new File(automationBaseLocation.concat("//src//" + project.getName()));
					util.copyDirectory(srcFile, destFile);
					isFolderDeleted = util.deleteCopiedFolder(folderName);
					isFolderDeleted = false;
					if (isOPADeleted && isFolderDeleted) {
						redir.addAttribute("message", ShowMessage.PROJECT_DELETE_SUCCESS);
						util.deleteCopiedFolder(automationBaseLocation.concat("//src//" + project.getName()));
					} else {
						appService.addEntity(project);
						util.copyDirectory(new File(automationBaseLocation.concat("//src//" + project.getName())),
								new File(util.getFolderNameForProjects(organization.getName())
										.concat("\\" + project.getName())));
						util.deleteCopiedFolder(automationBaseLocation.concat("//src//" + project.getName()));
						if (associationListToAdd != null && !associationListToAdd.isEmpty()) {
							for (Association entity : associationListToAdd) {
								entity.getCompositeId().setAssociationID(project.getId());
								appService.addEntity(entity);
							}
						}
						redir.addAttribute("message", ShowMessage.PROJECT_ASSOCIATION_DELETE_FAILED);
					}
				} else {
					redir.addAttribute("message", ShowMessage.PROJECT_DELETE_FAILED);
				}
				return "redirect:UpdateProjectLink";
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		return "redirect:UpdateProjectLink";
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "UpdateGroupLink", method = RequestMethod.GET)
	public String updateGroupLink(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "message", required = false) String message) {
		try {
			logger.info("Entered updateGroup method");
			List<SuperEntity> groupList = appService.getEntityList(Group.class);
			if (groupList != null && !groupList.isEmpty()) {
				model.addAttribute("groupList", groupList);
				model.addAttribute("message", message);
			}
			return "updategroup";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("message", ShowMessage.GROUP_lIST_EMPTY);
		return "updategroup";
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getGroupAjax", method = RequestMethod.POST)
	public String getGroupAjax(@RequestParam("id") int id, ModelMap model) {
		if (id == 0) {
			return "error";
		}
		try {
			Group group = (Group) appService.getEntityById(Group.class, id);
			if (group != null) {
				model.addAttribute("group", group);
			}
			List<SuperEntity> roleList = appService.getEntityList(Role.class);
			if (roleList != null && !roleList.isEmpty()) {
				model.addAttribute("roleList", roleList);
			}
			List<Integer> roleIdList = appService.getAssociatedList(AssociationTypeEnum.GRA.toString(), group.getId(),
					0);
			List<Role> assignedRoleList = new ArrayList<Role>();
			if (roleIdList != null && !roleIdList.isEmpty()) {
				for (int roleId : roleIdList) {
					Role role = (Role) appService.getEntityById(Role.class, roleId);
					assignedRoleList.add(role);
				}
			}
			if (assignedRoleList != null && !assignedRoleList.isEmpty()) {
				model.addAttribute("assignedRoleList", assignedRoleList);
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return "updateGroupTable";
	}

	/**
	 * 
	 * @param group
	 * @param results
	 * @param model
	 * @param redir
	 * @return
	 */
	@RequestMapping(value = "processUpdateGroup", params = "updateGroup", method = RequestMethod.POST)
	public String updateGroup(@ModelAttribute("group") Group group, BindingResult results, ModelMap model,
			RedirectAttributes redir, @RequestParam(value = "message", required = false) String message) {
		logger.info("Entered updateGroup method");
		if (message == null || message.isEmpty()) {
			if (group.getAssignedRoles() == null || group.getAssignedRoles().isEmpty()) {
				redir.addAttribute("message", ShowMessage.ASSIGNED_ROLE_EMPTY);
				return "redirect:UpdateGroupLink";
			}
		}
		boolean proceedUpdateGroup = true;
		boolean updateGRA = false;
		boolean isGroupUpdated = false;
		if (group != null) {
			try {
				// Decide to update GRA
				List<Integer> existingRoleIdList = appService.getAssociatedList(AssociationTypeEnum.GRA.toString(),
						group.getId(), 0);
				List<Role> existingRoleList = new ArrayList<Role>();
				if (existingRoleIdList != null && !existingRoleIdList.isEmpty()) {
					for (int roleId : existingRoleIdList) {
						Role role = (Role) appService.getEntityById(Role.class, roleId);
						existingRoleList.add(role);
					}
				}
				List<Role> assignedRoleList = new ArrayList<Role>();
				if (group.getAssignedRoles() != null && !group.getAssignedRoles().isEmpty()) {
					for (String name : group.getAssignedRoles()) {
						Role role = (Role) appService.getEntityByName(Role.class, name);
						assignedRoleList.add(role);
					}
				}
				if (assignedRoleList != null && !assignedRoleList.isEmpty()) {
					if (existingRoleList == null || existingRoleList.isEmpty()) {
						updateGRA = true;
					}
					if (!assignedRoleList.equals(existingRoleList)) {
						updateGRA = true;
					}
				}
				List<Association> associationTobeDeletedList = new ArrayList<Association>();
				List<Association> associationTobeAddedList = new ArrayList<Association>();
				// update GRA
				if (updateGRA) {
					boolean isGRADeleted = false;
					boolean isGRAUpdated = false;
					if (existingRoleList != null) {
						for (Role role : existingRoleList) {
							Association association = appService.getAssociation(AssociationTypeEnum.GRA.toString(),
									group.getId(), role.getId());
							isGRADeleted = appService.deleteEntity(association);
							if (isGRADeleted) {
								associationTobeAddedList.add(association);
							} else {
								proceedUpdateGroup = false;
							}
						}
					}
					if (assignedRoleList != null) {
						for (Role role : assignedRoleList) {
							Association association = new Association(AssociationTypeEnum.GRA.toString(), role.getId(),
									group.getId());
							isGRAUpdated = appService.addEntity(association);
							if (isGRAUpdated) {
								associationTobeDeletedList.add(association);
							} else {
								proceedUpdateGroup = false;
							}
						}
					}
				}
				if (proceedUpdateGroup) {
					isGroupUpdated = appService.updateEntity(group);
					if (isGroupUpdated) {
						redir.addAttribute("message", ShowMessage.GROUP_UPDATE_SUCCESS);
					} else {
						try {
							if (associationTobeDeletedList != null && !associationTobeDeletedList.isEmpty()) {
								for (Association removeEntity : associationTobeDeletedList) {
									appService.deleteEntity(removeEntity);
								}
							}
							if (associationTobeAddedList != null && !associationTobeAddedList.isEmpty()) {
								for (Association addEntity : associationTobeAddedList) {
									appService.addEntity(addEntity);
								}
							}
						} catch (Exception e) {
							logger.error("Exception : " + ShowMessage.getStackTrace(e));
						}
						redir.addAttribute("message", ShowMessage.GROUP_UPDATE_FAILED);
					}
				} else {
					redir.addAttribute("message", ShowMessage.GROUP_ASSOCIATION_CREATION_FAILED);
				}
				return "redirect:UpdateGroupLink";
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		logger.error("Exception : " + ShowMessage.GROUP_OBJECT_EMPTY);
		redir.addAttribute("message", ShowMessage.GROUP_UPDATE_FAILED);
		return "redirect:UpdateGroupLink";
	}

	/**
	 * 
	 * @param group
	 * @param results
	 * @param model
	 * @param redir
	 * @param message
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "processUpdateGroup", params = "deleteGroup", method = RequestMethod.POST)
	public String deleteGroup(@ModelAttribute("group") Group group, BindingResult results, ModelMap model,
			RedirectAttributes redir, @RequestParam(value = "message", required = false) String message) {
		logger.info("Entered deleteGroup method");
		if (group == null) {
			redir.addAttribute("message", ShowMessage.GROUP_OBJECT_EMPTY);
			return "redirect:UpdateGroupLink";
		} else {
			try {
				boolean isGroupDeleted = appService.deleteEntity(group);
				List<Association> associationListToAdd = new ArrayList<Association>();
				Association association = null;
				boolean isUGADeleted = true;
				boolean isGRADeleted = true;
				if (isGroupDeleted) {
					// Delete UGA association
					List<Association> UGAList = appService.getAssociationById(AssociationTypeEnum.UGA.toString(), 0,
							group.getId());
					if (UGAList != null && !UGAList.isEmpty()) {
						for (Association entity : UGAList) {
							isUGADeleted = appService.deleteEntity(entity);
							if (isUGADeleted) {
								associationListToAdd.add(entity);
							} else {
								isUGADeleted = false;
							}
						}
					}
					// Delete GRA association
					List<Association> GRAList = appService.getAssociationById(AssociationTypeEnum.GRA.toString(),
							group.getId(), 0);
					if (GRAList != null && !GRAList.isEmpty()) {
						for (Association entity : GRAList) {
							isGRADeleted = appService.deleteEntity(entity);
							if (isGRADeleted) {
								associationListToAdd.add(entity);
							} else {
								isUGADeleted = false;
							}
						}
					}
					if (isGRADeleted && isUGADeleted) {
						redir.addAttribute("message", ShowMessage.GROUP_DELETE_SUCCESS);
					} else {
						appService.addEntity(group);
						if (associationListToAdd != null && !associationListToAdd.isEmpty()) {
							for (Association entity : associationListToAdd) {
								appService.addEntity(entity);
							}
						}
						redir.addAttribute("message", ShowMessage.GROUP_ASSOCIATION_DELETE_FAILED);
					}
				} else {
					redir.addAttribute("message", ShowMessage.GROUP_DELETE_FAILED);
				}
				return "redirect:UpdateGroupLink";
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		return "redirect:UpdateGroupLink";
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "UpdateUserLink", method = RequestMethod.GET)
	public String updateUserLink(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "message", required = false) String message) {
		try {
			Organization org = null;
			List<UserDetail> userList = new ArrayList<UserDetail>();
			UserDetail user = (UserDetail) appService.getEntityByEmail(UserDetail.class,
					(String) request.getSession().getAttribute("userMailId"));
			if (user != null) {
				List<Integer> orgList = appService.getAssociatedList(AssociationTypeEnum.UOA.toString(), user.getId(),
						0);
				if (orgList != null && !orgList.isEmpty()) {
					org = (Organization) appService.getEntityById(Organization.class, orgList.get(0));
					model.addAttribute("organization", org.getName());
				}
			}

			if (org != null) {
				List<Integer> userIdList = appService.getAssociatedList(AssociationTypeEnum.UOA.toString(), 0,
						org.getId());
				if (userIdList != null && !userIdList.isEmpty()) {
					for (int id : userIdList) {
						UserDetail userDetail = (UserDetail) appService.getEntityById(UserDetail.class, id);
						if (userDetail.getUserType().getId() == 3) {
							userList.add(userDetail);
						}
					}
				}
				model.addAttribute("userList", userList);
			}
			model.addAttribute("message", message);
			return "updateuser";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("message", ShowMessage.DROPDOWN_VALUES_EMPTY);
		return "updateuser";
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "userList", headers = "Accept=*/*", method = RequestMethod.GET) // NOT USED
	public @ResponseBody List<UserDetail> getuserListByorgId(
			@RequestParam(value = "orgId", required = true) int orgId) {
		List<UserDetail> userList = new ArrayList<UserDetail>();
		UserDetail userDetail = null;
		try {
			List<Integer> userIdList = appService.getAssociatedList(AssociationTypeEnum.UOA.toString(), 0, orgId);
			if (userIdList != null && !userIdList.isEmpty()) {
				for (int id : userIdList) {
					userDetail = (UserDetail) appService.getEntityById(UserDetail.class, id);
					if (userDetail.getUserType().getId() == 3) {
						userList.add(userDetail);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return userList;
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getUserAjax", method = RequestMethod.POST)
	public String getUserAjax(@RequestParam(value = "id", required = true) int id, ModelMap model,
			HttpServletRequest request, RedirectAttributes redir) {
		if (id == 0) {
			return "error";
		}
		try {
			UserDetail user = (UserDetail) appService.getEntityById(UserDetail.class, id);
			if (user != null) {
				model.addAttribute("user", user);
			}
			Organization org = null;
			// To get organization of the User
			List<Integer> orgId = appService.getAssociatedList(AssociationTypeEnum.UOA.toString(), user.getId(), 0);
			if (orgId != null && !orgId.isEmpty()) {
				org = (Organization) appService.getEntityById(Organization.class, orgId.get(0));
				model.addAttribute("organizationName", org.getName());
			}
			// To get groups which belongs to User
			List<Integer> groupIdList = appService.getAssociatedList(AssociationTypeEnum.UGA.toString(), user.getId(),
					0);
			List<Group> groupList = new ArrayList<Group>();
			if (groupIdList != null && !groupIdList.isEmpty()) {
				for (int groupId : groupIdList) {
					Group group = (Group) appService.getEntityById(Group.class, groupId);
					groupList.add(group);
				}
				model.addAttribute("groupList", groupList);
			}
			// To get projects which belongs to User
			List<Integer> projectIdList = appService.getAssociatedList(AssociationTypeEnum.UPA.toString(), user.getId(),
					0);
			List<Project> projectList = new ArrayList<Project>();
			if (projectIdList != null && !projectIdList.isEmpty()) {
				for (int projectId : projectIdList) {
					Project project = (Project) appService.getEntityById(Project.class, projectId);
					projectList.add(project);
				}
				model.addAttribute("userProjectList", projectList);
			}
			List<Integer> allProjectIdList = appService.getAssociatedList(AssociationTypeEnum.OPA.toString(),
					org.getId(), 0);
			List<Project> allprojectList = new ArrayList<Project>();
			if (allProjectIdList != null && !allProjectIdList.isEmpty()) {
				for (int projectId : allProjectIdList) {
					Project project = (Project) appService.getEntityById(Project.class, projectId);
					allprojectList.add(project);
				}
				model.addAttribute("allProjectList", allprojectList);
			}
//			List<SuperEntity> userTypeList = appService.getEntityList(UserType.class);
//			if (userTypeList != null && !userTypeList.isEmpty()) {
//				model.addAttribute("typeList", userTypeList);
//			}
			
			List<SuperEntity> userTypeList = appService.getEntityList(UserType.class);
			if (userTypeList != null && !userTypeList.isEmpty()) {
				for(SuperEntity superType : userTypeList) {
					UserType userType = (UserType)superType;
					if(userType.getId() == 3) {
						model.addAttribute("userType", userType);
					}
				}
			} 
			
			List<SuperEntity> orgList = appService.getEntityList(Organization.class);
			if (orgList != null && !orgList.isEmpty()) {
				model.addAttribute("orgList", orgList);
			}
			List<SuperEntity> availablegroupList = appService.getEntityList(Group.class);
			if (availablegroupList != null && !availablegroupList.isEmpty()) {
				model.addAttribute("availablegroupList", availablegroupList);
			}
			return "updateUserTable";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		redir.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "redirect:/UpdateUserLink";
	}

	/**
	 * 
	 * @param user
	 * @param results
	 * @param redir
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "processUpdateUser", params = "updateUser", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("user") UserDetailDTO user, BindingResult results,
			RedirectAttributes redir, HttpServletRequest request) {
		logger.info("Entered updateUser method");
		boolean isUserUpdated = false;
		boolean isUpdateUOA = false;
		boolean isUpdateUGA = false;
		boolean isUpdateUPA = false;
		Organization existingOrg = null;
		UserDetail userDetail = null;
		List<Group> currentGroupList = null;
		List<Group> existingGroupList = null;
		List<Project> currentProjectList = null;
		List<Project> existingProjectList = null;
		try {
			// Deciding whether to update UOA
			Map<String, Object> resultUOAMap = util.checkForUpdateUOA(user);
			if (resultUOAMap != null && !resultUOAMap.isEmpty()) {
				for (String key : resultUOAMap.keySet()) {
					if (key.equals("EXISTING_ORG"))
						existingOrg = (Organization) resultUOAMap.get(key);
					if (key.equals("IS_UPDATE_UOA"))
						isUpdateUOA = (boolean) resultUOAMap.get(key);
				}
			}
			// Deciding whether to update UGA
			Map<String, Object> resultUGAMap = util.checkForUpdateUGA(user);
			if (resultUGAMap != null && !resultUGAMap.isEmpty()) {
				for (String key : resultUGAMap.keySet()) {
					if (key.equals("EXISTING_GROUP_LIST"))
						existingGroupList = (List<Group>) resultUGAMap.get(key);
					if (key.equals("CURRENT_GROUP_LIST"))
						currentGroupList = (List<Group>) resultUGAMap.get(key);
					if (key.equals("IS_UPDATE_UGA"))
						isUpdateUGA = (boolean) resultUGAMap.get(key);
				}
			}
			// Deciding whether to update UPA
			Map<String, Object> resultUPAMap = util.checkForUpdateUPA(user);
			if (resultUPAMap != null && !resultUPAMap.isEmpty()) {
				for (String key : resultUPAMap.keySet()) {
					if (key.equals("EXISTING_PROJECT_LIST"))
						existingProjectList = (List<Project>) resultUPAMap.get(key);
					if (key.equals("CURRENT_PROJECT_LIST"))
						currentProjectList = (List<Project>) resultUPAMap.get(key);
					if (key.equals("IS_UPDATE_UPA"))
						isUpdateUPA = (boolean) resultUPAMap.get(key);
				}
			}
			userDetail = util.mapDtoWithCommonFeildsOnUpdate(user, request);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		if (userDetail != null) {
			List<Association> associationTobeDeletedList = new ArrayList<Association>();
			List<Association> associationTobeAddedList = new ArrayList<Association>();
			boolean proceedUpdateUser = true;
			try {
				if (isUpdateUOA) {
					Map<String, Object> updatedUOAMap = util.updateUOA(user, existingOrg);
					if (updatedUOAMap != null && !updatedUOAMap.isEmpty()) {
						for (String key : updatedUOAMap.keySet()) {
							if (key.equals("ASSOCIATION_TO_BE_ADDED"))
								associationTobeAddedList = (List<Association>) updatedUOAMap.get(key);
							if (key.equals("ASSOCIATION_TO_BE_DELETED"))
								associationTobeDeletedList = (List<Association>) updatedUOAMap.get(key);
							if (key.equals("PROCEED_UPDATE_USER"))
								proceedUpdateUser = (boolean) updatedUOAMap.get(key);
						}
					}
				}
				if (isUpdateUGA) {
					Map<String, Object> updatedUGAMap = util.updateUGA(user, currentGroupList, existingGroupList,
							associationTobeDeletedList, associationTobeAddedList);
					if (updatedUGAMap != null && !updatedUGAMap.isEmpty()) {
						for (String key : updatedUGAMap.keySet()) {
							if (key.equals("ASSOCIATION_TO_BE_ADDED"))
								associationTobeAddedList.addAll((List<Association>) updatedUGAMap.get(key));
							if (key.equals("ASSOCIATION_TO_BE_DELETED"))
								associationTobeDeletedList.addAll((List<Association>) updatedUGAMap.get(key));
							if (key.equals("PROCEED_UPDATE_USER"))
								proceedUpdateUser = (boolean) updatedUGAMap.get(key);
						}
					}

				}
				if (isUpdateUPA) {
					Map<String, Object> updatedUPAMap = util.updateUPA(user, currentProjectList, existingProjectList,
							associationTobeDeletedList, associationTobeAddedList);
					if (updatedUPAMap != null && !updatedUPAMap.isEmpty()) {
						for (String key : updatedUPAMap.keySet()) {
							if (key.equals("ASSOCIATION_TO_BE_ADDED"))
								associationTobeAddedList.addAll((List<Association>) updatedUPAMap.get(key));
							if (key.equals("ASSOCIATION_TO_BE_DELETED"))
								associationTobeDeletedList.addAll((List<Association>) updatedUPAMap.get(key));
							if (key.equals("PROCEED_UPDATE_USER"))
								proceedUpdateUser = (boolean) updatedUPAMap.get(key);
						}
					}
				}
				if (proceedUpdateUser) {
					isUserUpdated = appService.updateEntity(userDetail);
					if (isUserUpdated) {
						redir.addAttribute("message", ShowMessage.USER_UPDATE_SUCCESS);
					} else {
						try {
							if (associationTobeDeletedList != null && !associationTobeDeletedList.isEmpty()) {
								for (Association removeEntity : associationTobeDeletedList) {
									appService.deleteEntity(removeEntity);
								}
							}
							if (associationTobeAddedList != null && !associationTobeAddedList.isEmpty()) {
								for (Association addEntity : associationTobeAddedList) {
									appService.addEntity(addEntity);
								}
							}
						} catch (Exception e) {
							logger.error("Exception : " + ShowMessage.getStackTrace(e));
						}
						redir.addAttribute("message", ShowMessage.USER_UPDATE_FAILED);
					}
				} else {
					redir.addAttribute("message", ShowMessage.USER_ASSOCIATION_UPDATE_FAILED);
				}
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
			return "redirect:UpdateUserLink";
		}
		logger.error("Exception : " + ShowMessage.USERDETAIL_OBJECT_NULL);
		redir.addAttribute("message", ShowMessage.USER_UPDATE_FAILED);
		return "redirect:UpdateUserLink";
	}

	/**
	 * 
	 * @param userDto
	 * @param results
	 * @param request
	 * @param redir
	 * @return
	 */
	@RequestMapping(value = "processUpdateUser", params = "resetPassword", method = RequestMethod.POST)
	public String resetPasswordForUser(@ModelAttribute("user") UserDetailDTO userDto, BindingResult results,
			HttpServletRequest request, RedirectAttributes redir) {
		logger.info("Entered resetPasswordForUser method");
		UserDetail user = null;
		String value = "";
		try {
			user = util.toUserDetailEntity(userDto, request);
			value = util.deleteAttemptCount(user);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		if (value.equals("success")) {
			redir.addAttribute("message", ShowMessage.RESET_PASSWORD_SUCCESS);
		} else if (value.equals("failure")) {
			redir.addAttribute("message", ShowMessage.RESET_PASSWORD_FAILED);
		} else if (value.trim().equals("")) {
			redir.addAttribute("message", ShowMessage.NO_RECORDS_FOUND_TO_RESET);
		}
		return "redirect:UpdateUserLink";
	}

	/**
	 * 
	 * @param userDto
	 * @param results
	 * @param model
	 * @param redir
	 * @param message
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "processUpdateUser", params = "deleteUser", method = RequestMethod.POST)
	public String deleteUser(@ModelAttribute("userDto") UserDetailDTO userDto, BindingResult results, ModelMap model,
			RedirectAttributes redir, @RequestParam(value = "message", required = false) String message, HttpServletRequest request) {
		logger.info("Entered deleteUser method");
		List<Association> associationListToAdd = new ArrayList<Association>();
		boolean isPortDeleted = false;
		boolean isAttemptHistoryDeleted = false;
		boolean isUserDeleted = false;
		Association association = null;
		boolean isUGADeleted = true;
		boolean isUOADeleted = true;
		boolean isUPADeleted = true;
		boolean isFolderDeleted = true;
		if (userDto == null) {
			redir.addAttribute("message", ShowMessage.USERDETAIL_OBJECT_NULL);
			return "redirect:UpdateUserLink";
		} else {
			try {
				UserDetail user = (UserDetail) appService.getEntityById(UserDetail.class, userDto.getId());
				// Port should be deleted before user deletion
				Port port = (Port) appService.getEntityById(Port.class, userDto.getId());
				if (port != null) {
					isPortDeleted = appService.deleteEntity(port);
				}
				// Delete user
				if (isPortDeleted) {
					AttemptHistory attemptHistory = (AttemptHistory) appService.getEntityById(AttemptHistory.class, user.getId());
					isAttemptHistoryDeleted = appService.deleteEntity(attemptHistory);
					if(isAttemptHistoryDeleted){
						isUserDeleted = appService.deleteEntityById(UserDetail.class, userDto.getId());
					}
				}
				if (isUserDeleted) {
					// Delete UGA association
					List<Association> UGAList = appService.getAssociationById(AssociationTypeEnum.UGA.toString(),
							userDto.getId(), 0);
					if (UGAList != null && !UGAList.isEmpty()) {
						for (Association entity : UGAList) {
							isUGADeleted = appService.deleteEntity(entity);
							if (isUGADeleted) {
								associationListToAdd.add(entity);
							} else {
								isUGADeleted = false;
							}
						}
					}
					// Delete UOA association
					List<Integer> orgList = appService.getAssociatedList(AssociationTypeEnum.UOA.toString(),
							userDto.getId(), 0);
					Organization org = (Organization) appService.getEntityById(Organization.class, orgList.get(0));

					List<Association> UOAList = appService.getAssociationById(AssociationTypeEnum.UOA.toString(),
							userDto.getId(), 0);
					if (UOAList != null && !UOAList.isEmpty()) {
						for (Association entity : UOAList) {
							isUOADeleted = appService.deleteEntity(entity);
							if (isUOADeleted) {
								associationListToAdd.add(entity);
							} else {
								isUOADeleted = false;
							}
						}
					}
					// Delete UPA association
					List<Association> UPAList = appService.getAssociationById(AssociationTypeEnum.UPA.toString(),
							userDto.getId(), 0);
					if (UPAList != null && !UPAList.isEmpty()) {
						for (Association entity : UPAList) {
							isUPADeleted = appService.deleteEntity(entity);
							if (isUPADeleted) {
								associationListToAdd.add(entity);
							} else {
								isUPADeleted = false;
							}
						}
					}
					// Delete user folder
					String userFolderName = userDto.getFirstName() + " " + userDto.getLastName();
					String folderName = util.getFolderNameForUsers(org.getName()).concat("\\" + userFolderName);
					isFolderDeleted = util.deleteCopiedFolder(folderName);
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>. isUOADeleted : "+isUOADeleted);
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>. isUGADeleted : "+isUGADeleted);
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>. isUPADeleted : "+isUPADeleted);
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>. isFolderDeleted : "+isFolderDeleted);
					if (isUOADeleted && isUGADeleted && isUPADeleted && isFolderDeleted) {
						redir.addAttribute("message", ShowMessage.USER_DELETE_SUCCESS);
					} else {
						appService.addEntity(user);
						UserDetail user1 = (UserDetail) appService.getEntityById(UserDetail.class, user.getId());
						Port port1 = new Port();
						port1.setUser(user1);
						appService.addEntity(port1);
						File file = new File(folderName);
						if (!file.exists()) {
							file.mkdirs();
						}
						util.copyUserFiles(request, folderName, port1.getPort());
						if (associationListToAdd != null && !associationListToAdd.isEmpty()) {
							for (Association entity : associationListToAdd) {
								entity.getCompositeId().setMainID(user1.getId());
								appService.addEntity(entity);
							}
						}
						redir.addAttribute("message", ShowMessage.USER_ASSOCIATION_DELETE_FAILED);
					}
				} else {
					redir.addAttribute("message", ShowMessage.USER_DELETE_FAILED);
				}
				return "redirect:UpdateUserLink";
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		return "redirect:UpdateUserLink";
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "CreateGroupLink", method = RequestMethod.GET)
	public String createGroup(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "message", required = false) String message) {
		try {
			List<SuperEntity> roleList = appService.getEntityList(Role.class);
			if (roleList != null && !roleList.isEmpty()) {
				model.addAttribute("roleList", roleList);
				model.addAttribute("message", message);
				return "addgroup";
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("message", ShowMessage.ROLE_lIST_EMPTY);
		return "addgroup";
	}

	/**
	 * 
	 * @param emailId
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "saveGroup", method = RequestMethod.POST)
	public String saveGroup(@ModelAttribute("group") Group group, BindingResult results, RedirectAttributes redir,
			HttpServletRequest request) {
		logger.info("Entered saveGroup method");
		List<String> assignedRoleNameList = group.getAssignedRoles();
		if (assignedRoleNameList == null || assignedRoleNameList.isEmpty()) {
			redir.addAttribute("message", ShowMessage.ASSIGNED_ROLE_EMPTY);
			return "redirect:CreateGroupLink";
		}
		boolean result = false;
		boolean isCompleteCreateGroup = true;
		boolean isGRAcreated = false;
		Association association = null;
		try {
			result = appService.addEntity(group);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		if (result) {
			try {
				group = (Group) appService.getEntityById(Group.class, group.getId());
				List<Association> associationListToremove = new ArrayList<Association>();
				// creating GRA association
				if (assignedRoleNameList != null && !assignedRoleNameList.isEmpty()) {
					for (String name : assignedRoleNameList) {
						Role role = (Role) appService.getEntityByName(Role.class, name);
						association = new Association(AssociationTypeEnum.GRA.toString(), role.getId(), group.getId());
						isGRAcreated = appService.addEntity(association);
						if (isGRAcreated) {
							associationListToremove.add(association);
						} else {
							isCompleteCreateGroup = false;
						}
						logger.info("Association GRA added");
					}
				}
				if (isCompleteCreateGroup) {
					redir.addAttribute("message", ShowMessage.GROUP_CREATE_SUCCESS);
					return "redirect:CreateGroupLink";
				} else {
					try {
						appService.deleteEntity(group);
						if (associationListToremove != null && !associationListToremove.isEmpty()) {
							for (Association remove : associationListToremove) {
								appService.deleteEntity(remove);
							}
						}
					} catch (Exception e) {
						logger.error("Exception : " + ShowMessage.getStackTrace(e));
					}
					redir.addAttribute("message", ShowMessage.GROUP_ASSOCIATION_CREATION_FAILED);
				}
				return "redirect:CreateGroupLink";
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		redir.addAttribute("message", ShowMessage.GROUP_CREATE_FAILED);
		return "redirect:CreateGroupLink";
	}
}
