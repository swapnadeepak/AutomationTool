package com.estuate.esaip2_0.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estuate.esaip2_0.entity.Project;
import com.estuate.esaip2_0.exception.ShowMessage;
import com.estuate.esaip2_0.model.AllObjects;
import com.estuate.esaip2_0.model.Defects;
import com.estuate.esaip2_0.model.MasterPlan;
import com.estuate.esaip2_0.model.ProjectFolderEnum;
import com.estuate.esaip2_0.model.ResultDetails;
import com.estuate.esaip2_0.model.RunPlan;
import com.estuate.esaip2_0.model.TestSteps;
import com.estuate.esaip2_0.service.ApplicationService;
import com.estuate.esaip2_0.util.CreateMasterPlan;
import com.estuate.esaip2_0.util.ExcelUtil;
import com.estuate.esaip2_0.util.Execute;
import com.estuate.esaip2_0.util.FetchDataUtil;
import com.estuate.esaip2_0.util.UpdateData;
import com.estuate.esaip2_0.util.Util;
import com.estuate.esaip2_0.util.ValidateData;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.estuate.esaip2_0.util.Constants.*;



/**
 * <ul>
 * <li>Title: UserController</li>
 * <li>Description: Handles requests for the web application related to
 * <tt>projectUser group </tt>.</li>
 * <li>Created by: Radhika Gopalraj</li>
 * </ul>
 */
@Controller
public class UserController {
	/**
	 * The logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	/**
	 * The appService
	 */
	@Autowired
	private ApplicationService appService;
	/**
	 * The fetchDataUtil
	 */
	@Autowired
	private FetchDataUtil fetchDataUtil;
	/**
	 * The updateData
	 */
	@Autowired
	private UpdateData updateData;
	/**
	 * The execute
	 */
	@Autowired
	private Execute execute;
	/**
	 * The util
	 */
	@Autowired
	private Util util;
	/**
	 * The validateData
	 */
	@Autowired
	private ValidateData validateData;
	/**
	 * The excelUtil
	 */
	@Autowired
	private ExcelUtil excelUtil;
	/**
	 * The createMasterPlan
	 */
	@Autowired
	private CreateMasterPlan createMasterPlan;

	/**
	 * This is used to fetch the master plan
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "MasterPlan")
	public String masterPlan(ModelMap model, HttpServletRequest request) {
		logger.info("Entered method masterPlan");
		try {
			List<Project> projectList = (List<Project>) request.getSession().getAttribute("userProjectsList");
			logger.info("Number of projects associated: "+projectList.size());
			if (projectList != null && !projectList.isEmpty()) {
				if (projectList.size() == 1) {
					return "userdashboard";
				} else if (projectList.size() > 1) {
					model.addAttribute("proList", projectList);
					return "usermultiprojectdashboard";
				}
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("message", ShowMessage.NO_PROJECTS_FOUND_FOR_USER);
		return "userdashboard";
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @param projectName
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/getMasterPlan", method = RequestMethod.GET)
	public String getMasterPlan(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "projectName", required = false) String projectName) {
		logger.info("Entered getMasterPlan method");
		List<MasterPlan> masterPlan = null;
		String projName = "";
		try {
			List<Project> userProjectList = (List<Project>) request.getSession().getAttribute("userProjectsList");
			if (projectName == null || projectName.isEmpty()) {
				if (userProjectList != null && !userProjectList.isEmpty() && userProjectList.size() == 1) {
					projName = userProjectList.get(0).getName();
					request.getSession().setAttribute("userProject", userProjectList.get(0));
					masterPlan = fetchDataUtil.fetchMasterplan(request);
					if (masterPlan != null && !masterPlan.isEmpty()) {
						request.getSession().setAttribute("masterPlan", masterPlan);
						model.addAttribute("masterPlan", masterPlan);
						return "userdashboardTable";
					} else {
						model.addAttribute("message",
								ShowMessage.MASTER_PLAN_EMPTY.concat(" '" + userProjectList.get(0).getName() + "'"));
						return "userdashboardTable";
					}
				} else {
					model.addAttribute("proList", userProjectList);
					return "usermultiprojectdashboard";
				}
			}
			if (projectName != null && !projectName.isEmpty()) {
				projName = projectName;
				Project project = (Project) appService.getEntityByName(Project.class, projectName);
				request.getSession().setAttribute("userProject", project);
				masterPlan = fetchDataUtil.fetchMasterplan(request);
				if (masterPlan != null && !masterPlan.isEmpty()) {
					model.addAttribute("masterPlan", masterPlan);
				} else {
					model.addAttribute("message", ShowMessage.MASTER_PLAN_EMPTY.concat(" '" + projectName + "'"));
				}
				return "userdashboardTable";
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
			model.addAttribute("message", "Error occured in the master plan. Please upload new/modified master plan");
			return "userdashboardTable";
		} finally {
			try {
				// updating in the generic.properties
				updateProperty(projName, request);
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		model.addAttribute("message", ShowMessage.NO_PROJECTS_FOUND_FOR_USER);
		return "userdashboard";
	}

	/**
	 * This is used to fetch the list of files in run plan folder
	 * 
	 * @param model
	 * @param request
	 * @param message
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "Execute")
	public String fileDetails(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "message", required = false) String message) {
		logger.info("Entered method fileDetails");
		try {
			Project userProject = (Project) request.getSession().getAttribute("userProject");
			if (userProject == null) {
				List<Project> projectList = (List<Project>) request.getSession().getAttribute("userProjectsList");
				model.addAttribute("projectList", projectList);
				return "projectselection";
			} else {
				// updating in the generic.properties
				updateProperty(userProject.getName(), request);
				List<MasterPlan> masterPlan = fetchDataUtil.fetchMasterplan(request);
				if (masterPlan == null || masterPlan.isEmpty()) {
					model.addAttribute("message",
							ShowMessage.MASTER_PLAN_EMPTY.concat(" '" + userProject.getName() + "'"));
					return "errormessage";
				}
			}
			Map<String, String> runplansList = fetchDataUtil.getFilesList(ProjectFolderEnum.RUNPLANS.getFolderName(),
					request);
			if (runplansList != null && !runplansList.isEmpty()) {
				model.addAttribute("filesList", runplansList);
				model.addAttribute("message", message);
			} else {
				model.addAttribute("dropDownMessage", ShowMessage.RUN_PLANS_EMPTY);
				model.addAttribute("disable", "true");
			}
			return "filesdropdown";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "filesdropdown";
	}

	/**
	 * This is used to fetch the list of files in results folder
	 * 
	 * @param message
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "Results")
	public String results(@RequestParam(value = "message", required = false) String message, ModelMap model,
			HttpServletRequest request) {
		logger.info("Entered method fetchResult");
		try {
			Project userProject = (Project) request.getSession().getAttribute("userProject");
			if (userProject == null) {
				List<Project> projectList = (List<Project>) request.getSession().getAttribute("userProjectsList");
				model.addAttribute("projectList", projectList);
				return "projectselection";
			} else {
				// updating in the generic.properties
				updateProperty(userProject.getName(), request);
				List<MasterPlan> masterPlan = fetchDataUtil.fetchMasterplan(request);
				if (masterPlan == null || masterPlan.isEmpty()) {
					model.addAttribute("message",
							ShowMessage.MASTER_PLAN_EMPTY.concat(" '" + userProject.getName() + "'"));
					return "errormessage";
				}
			}
			Map<String, String> resultMap = fetchDataUtil
					.getFilesList(ProjectFolderEnum.RESULTS.getFolderName(), request);
			if (resultMap != null && !resultMap.isEmpty()) {
				model.addAttribute("resultMap", resultMap);
				model.addAttribute("message", message);
			} else {
				model.addAttribute("message", ShowMessage.RESULT_FILES_EMPTY);
				model.addAttribute("disable", "true");
			}
			return "result";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "result";
	}
	
	
	
	@RequestMapping(value = "fileList", headers = "Accept=*/*", method = RequestMethod.GET)
	public @ResponseBody String getFileByFolder(
			@RequestParam(value = "folderPath", required = true) String folderPath,HttpServletRequest request) {
		Map<String, String> fileMap = null;
		String json = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			 fileMap = fetchDataUtil.getFilesListForResults(folderPath, request);
			 json = mapper.writeValueAsString(fileMap);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return json;
	}


	/**
	 * This is used to fetch the run plan
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "RunPlan")
	public String runPlan(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "message", required = false) String message) {
		logger.info("Entered method runPlan");
		try {
			Project userProject = (Project) request.getSession().getAttribute("userProject");
			if (userProject == null) {
				List<Project> projectList = (List<Project>) request.getSession().getAttribute("userProjectsList");
				model.addAttribute("projectList", projectList);
				return "projectselection";
			}
			// updating in the generic.properties
			updateProperty(userProject.getName(), request);
			List<MasterPlan> masterPlan = fetchDataUtil.fetchMasterplan(request);
			if (masterPlan != null && !masterPlan.isEmpty()) {
				model.addAttribute("message", message);
				model.addAttribute("masterPlanList", masterPlan);
				return "runplan";
			} else {
				model.addAttribute("message", ShowMessage.MASTER_PLAN_EMPTY.concat(" '" + userProject.getName() + "'"));
				return "errormessage";
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "runplan";
	}

	/**
	 * This is used to fetch the file location of results to view in defects tab
	 * 
	 * @param model
	 * @param request
	 * @param message
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "Defects")
	public String location(ModelMap model, HttpServletRequest request,
			@RequestParam(value = "message", required = false) String message) {
		logger.info("Entered method location");
		try {
			Project userProject = (Project) request.getSession().getAttribute("userProject");
			if (userProject == null) {
				List<Project> projectList = (List<Project>) request.getSession().getAttribute("userProjectsList");
				model.addAttribute("projectList", projectList);
				return "projectselection";
			} else {
				// updating in the generic.properties
				updateProperty(userProject.getName(), request);
				List<MasterPlan> masterPlan = fetchDataUtil.fetchMasterplan(request);
				if (masterPlan == null || masterPlan.isEmpty()) {
					model.addAttribute("message",
							ShowMessage.MASTER_PLAN_EMPTY.concat(" '" + userProject.getName() + "'"));
					return "errormessage";
				}
			}
			Map<String, String> resultMap = fetchDataUtil
					.getFilesList(ProjectFolderEnum.RESULTS.getFolderName(), request);
			if (resultMap != null && !resultMap.isEmpty()) {
				model.addAttribute("defectFiles", resultMap);
				model.addAttribute("message", message);
			} else {
				model.addAttribute("message", ShowMessage.DEFECT_FILES_EMPTY);
				model.addAttribute("disable", "true");
			}
			return "defects";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "defects";
	}

	/**
	 * This is used to create the new Run plan
	 * 
	 * @param params
	 * @param model
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "createRunPlan", method = RequestMethod.POST)
	public String createRunPlan(@RequestParam Map<String, String> params, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		logger.info("Entered method createRunPlan");
		try {
			if (params != null && !params.isEmpty()) {
				String ip = request.getRemoteAddr();
				ArrayList<MasterPlan> runPlans = new ArrayList<MasterPlan>();
				int length = Integer.parseInt(params.get("length"));
				for (int i = 1; i <= length; i++) {
					MasterPlan runPlan = new MasterPlan();
					runPlan.setId(request.getParameter("id" + i));
					runPlan.setBusinessRequirement(params.get("businessRequirement" + i));
					runPlan.setDescription(params.get("description" + i));
					runPlan.setImpactedBRs(params.get("impactedBRs" + i));
					runPlan.setIncludeImpactedBRs(params.get("includeImpactedBRs" + i));
					runPlan.setModules(params.get("modules" + i));
					runPlan.setModulesInclude(params.get("modulesInclude" + i));
					runPlan.setTestCases(params.get("testCases" + i));
					runPlan.setTestCasesInclude(params.get("testCasesInclude" + i));
					runPlan.setTestCaseId(params.get("testCaseId" + i));
					runPlan.setCriticality(params.get("criticality" + i));
					runPlan.setRepeatablity(params.get("repeatablity" + i));
					runPlans.add(runPlan);
				}
				String runPlanLocation = updateData.createRunPlan(runPlans, ip, request);
				redirectAttributes.addAttribute("fileLocation", runPlanLocation);
				return "redirect:/generate";
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		redirectAttributes.addAttribute("message", ShowMessage.CREATE_RUNPLAN_FAILED);
		return "redirect:/RunPlan";
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @param projectName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "selectProject", method = RequestMethod.POST)
	public String selectProject(ModelMap model, HttpServletRequest request,
			@RequestParam("projectName") String projectName) {
		logger.info("Entered selectProject method");
		List<MasterPlan> masterPlan = null;
		String projName = "";
		try {
			if (projectName != null && !projectName.isEmpty()) {
				projName = projectName;
				Project project = (Project) appService.getEntityByName(Project.class, projectName);
				request.getSession().setAttribute("userProject", project);
				masterPlan = fetchDataUtil.fetchMasterplan(request);
				if (masterPlan == null || masterPlan.isEmpty()) {
					model.addAttribute("message",
							ShowMessage.PROJECT_WITH_NO_MASTERPLAN.concat(" '" + projectName + "'"));
					model.addAttribute("errorMessage", ShowMessage.MASTER_PLAN_EMPTY.concat(" '" + projectName + "'"));
				} else {
					model.addAttribute("message", ShowMessage.processProjectMsg(projectName));
				}
				List<Project> projectList = (List<Project>) request.getSession().getAttribute("userProjectsList");
				model.addAttribute("projectList", projectList);
				return "projectselection";
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			try {
				// updating in the generic.properties
				updateProperty(projName, request);
			} catch (Exception e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
		}
		model.addAttribute("message", ShowMessage.NO_PROJECTS_FOUND_FOR_USER);
		return "projectselection";

	}

	/**
	 * This is used to fetch the execution plan
	 * 
	 * @param fileLocation
	 * @param model
	 * @param request
	 * @param redir
	 * @return
	 */
	@RequestMapping(value = "generate", method = { RequestMethod.POST, RequestMethod.GET })
	public String fetchExecutionPlan(@RequestParam("fileLocation") String fileLocation, ModelMap model,
			HttpServletRequest request, RedirectAttributes redir) {
		logger.info("Entered method fetchExecutionPlan");
		try {
			if (fileLocation != null && !fileLocation.trim().isEmpty()) {
				List<RunPlan> runPlanList = fetchDataUtil.fetchRunPlan(fileLocation);
				Map<String, String> runplansList = fetchDataUtil
						.getFilesList(ProjectFolderEnum.RUNPLANS.getFolderName(), request);
				String newNode = execute.getProperty("DownloadNewNodeserver", request);
				String version = execute.getProperty("LatestNoderserverVersion", request);
				if (runPlanList != null && !runPlanList.isEmpty()) {
					model.addAttribute("executiondata", runPlanList);
				} else {
					logger.warn("runPlanList is Null or Empty");
				}
				if (runplansList != null && !runplansList.isEmpty()) {
					model.addAttribute("runplans", runplansList);
				} else {
					logger.warn("runplansList is Null or Empty");
				}
				model.addAttribute("newnode", newNode);
				model.addAttribute("version", version);
				return "execute";
			} else {
				redir.addAttribute("message", ShowMessage.SELECT_RUN_PLAN_MSG);
				return "redirect:/Execute";
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "redirect:/Execute";
	}

	/**
	 * This is used to call the execution plans
	 * 
	 * @param fileLocation
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "executeplan", method = RequestMethod.POST)
	public String executePlan(@RequestParam("fileLocation") String fileLocation, RedirectAttributes redir,
			HttpServletRequest request) {
		String orgFolder = (String) request.getSession().getAttribute("userOrganization");
		String userFolder = (String) request.getSession().getAttribute("userName");
		logger.info("Entered method executePlan" + fileLocation);
		try {
			if (fileLocation != null && !fileLocation.trim().isEmpty()) {
				String fileName = fileLocation.substring(fileLocation.lastIndexOf("\\") + 1);
				execute.updateProgressProperty(orgFolder, userFolder, "RunplanFilename", fileName, request);
				execute.updateProgressProperty(orgFolder, userFolder, "NodeServerRunning", "Yes", request);
				execute.updateProgressProperty(orgFolder, userFolder, "ProgressStatus", "0", request);
				execute.updateProgressProperty(orgFolder, userFolder, "StopExecution", "No", request);
				execute.updateProgressProperty(orgFolder, userFolder, "ResultFilename", "", request);
				execute.updateProgressProperty(orgFolder, userFolder, "NodeServerRunningMessage", "", request);
				execute.updateProgressProperty(orgFolder, userFolder, "StopExecutionMessage", "", request);
				execute.executeTestCase(fileLocation, request);
				return "redirect:/process";
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		redir.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "redirect:/process";
	}

	/**
	 * This is used to update the progress of the test case execution
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "process", method = RequestMethod.GET)
	public String updateProcess(ModelMap model, HttpServletRequest request) {
		logger.info("Entered method updateProcess");
		try {
			model.addAttribute("resultstatus", execute.getProperty("ProgressStatus", request).trim());
			model.addAttribute("nodeStatus", execute.getProperty("NodeServerRunning", request));
			model.addAttribute("stopStatus", execute.getProperty("StopExecution", request));
			model.addAttribute("stopMessage", execute.getProperty("StopExecutionMessage", request));
			String nodeMessage = execute.getProperty("NodeServerRunningMessage", request);
			model.addAttribute("nodeMessage", execute.getMessage(nodeMessage, request));
			model.addAttribute("runplan", fetchDataUtil.runPLanLocation(request));
			model.addAttribute("resultfile", fetchDataUtil.resultFileLocation(request));
			model.addAttribute("runPlanFileName", excelUtil.getProgressProperty("RunplanFilename", request));
			return "executionprogress";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "executionprogress";
	}

	/**
	 * This is used to fetch the results from the file
	 * 
	 * @param fileLocation
	 * @param model
	 * @param request
	 * @param redir
	 * @return
	 */
	@RequestMapping(value = "display", method = RequestMethod.POST)
	public String display(@RequestParam("fileLocation") String fileLocation, ModelMap model, HttpServletRequest request,
			RedirectAttributes redir) {
		logger.info("Entered method display");
		try {
			if (fileLocation != null && !fileLocation.trim().isEmpty()) {
				Map<String, String> resultMap = fetchDataUtil
						.getFilesList(ProjectFolderEnum.RESULTS.getFolderName(), request);
				List<RunPlan> runPlan = fetchDataUtil.fetchExcecutionPlan(fileLocation);
				List<ResultDetails> resultDetails = fetchDataUtil.fetchResultData(fileLocation);
				int defectsListSize = fetchDataUtil.fetchDefectData(fileLocation).size();
				if (resultMap != null && !resultMap.isEmpty()) {
					model.addAttribute("resultFileList", resultMap);
				} else {
					logger.warn("resultMap is Null or Empty");
				}
				if (resultDetails != null && !resultDetails.isEmpty()) {
					model.addAttribute("result", resultDetails);
				} else {
					logger.warn("resultDetails List is Null or Empty");
				}
				if (runPlan != null && !runPlan.isEmpty()) {
					model.addAttribute("UsedRunPlan", runPlan);
				} else {
					logger.warn("runPlan List is Null or Empty");
				}
				model.addAttribute("defectsize", defectsListSize);
				return "resultdisplay";
			}
			redir.addAttribute("message", ShowMessage.SELECT_RESULT_FILE_MSG);
			return "redirect:/Results";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		model.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "executionprogress";
	}

	/**
	 * This is used to download the selected result file
	 * 
	 * @param fileLocation
	 * @param model
	 * @param response
	 * @param request
	 * @param redir
	 * @return
	 */
	@RequestMapping(value = "save", method = { RequestMethod.GET, RequestMethod.POST })
	public String saveResultFile(@RequestParam("fileLocation") String fileLocation, HttpServletResponse response,
			HttpServletRequest request, RedirectAttributes redir) {
		logger.info("Entered method saveResultFile");
		response.setContentType("text/html");
		FileInputStream fileInputStream = null;
		PrintWriter out = null;
		try {
			if (fileLocation != null && !fileLocation.trim().isEmpty()) {
				try {
					out = response.getWriter();
					String filename = fileLocation.substring(fileLocation.lastIndexOf("\\"));
					response.setContentType("APPLICATION/OCTET-STREAM");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
					fileInputStream = new FileInputStream(fileLocation);
					int i;
					while ((i = fileInputStream.read()) != -1) {
						out.write(i);
					}
				} catch (Exception e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				} finally {
					try {
						if (fileInputStream != null)
							fileInputStream.close();
					} catch (IOException e) {
						logger.error("Exception : " + ShowMessage.getStackTrace(e));
					}
					if (out != null)
						out.close();
				}
				return "result";
			} else {
				redir.addAttribute("message", ShowMessage.SELECT_RESULT_FILE_TO_DOWNLOAD);
				return "redirect:/Results";
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		redir.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "redirect:/Results";
	}

	/**
	 * This is used to download log files
	 * 
	 * @param fileLocation
	 * @param model
	 * @param response
	 * @param request
	 * @param redir
	 * @return
	 */
	@RequestMapping(value = "logs", method = { RequestMethod.GET, RequestMethod.POST })
	public String downloadLog(@RequestParam("fileLocation") String fileLocation, HttpServletResponse response,
			HttpServletRequest request, RedirectAttributes redir) {
		logger.info("Entered method downloadLog");
		response.setContentType("text/plain");
		PrintWriter out = null;
		FileInputStream fileInputStream = null;
		try {
			if (fileLocation != null && !fileLocation.trim().isEmpty()) {
				try {
					out = response.getWriter();
					String logFile = fetchDataUtil.getLogFile(fileLocation, request);
					String filename = logFile.substring(logFile.lastIndexOf("\\") + 1);
					response.setContentType("APPLICATION/OCTET-STREAM");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
					fileInputStream = new FileInputStream(logFile);
					int i;
					while ((i = fileInputStream.read()) != -1) {
						out.write(i);
					}
					out.flush();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				} finally {
					try {
						if (fileInputStream != null)
							fileInputStream.close();
					} catch (IOException e) {
						logger.error("Exception : " + ShowMessage.getStackTrace(e));
					}
					if (out != null) {
						out.close();
					}
				}
				return "result";
			} else {
				redir.addAttribute("message", ShowMessage.SELECT_RESULT_FILE_TO_DOWNLOAD_LOG);
				return "redirect:/Results";
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		redir.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "redirect:/Results";
	}

	/**
	 * This is used fetch the defects details All the column numbers are n-1 as of
	 * sheet in excel column 10 is log defect column 11 is reproducibility column 12
	 * is severity column 13 is priority column 14 is assigned to
	 * 
	 * @param fileLocation
	 * @param model
	 * @param response
	 * @param request
	 * @param redir
	 * @return
	 */
	@RequestMapping(value = "defects", method = { RequestMethod.POST, RequestMethod.GET })
	public String defects(@RequestParam("fileLocation") String fileLocation, ModelMap model, HttpServletRequest request,
			RedirectAttributes redir) {
		String orgFolder = (String) request.getSession().getAttribute("userOrganization");
		String userFolder = (String) request.getSession().getAttribute("userName");
		try {
			if (fileLocation != null && !fileLocation.trim().isEmpty()) {
				execute.updateProgressProperty(orgFolder, userFolder, "StopExecution", "No", request);

				Map<String, String> defectfiles = fetchDataUtil
						.getFilesList(ProjectFolderEnum.RESULTS.getFolderName(), request);
				List<Defects> defectList = fetchDataUtil.fetchDefectData(fileLocation);
				Map<String, String> logDefect = fetchDataUtil.fetchDefectDropdown(fileLocation, 9);
				Map<String, String> reproducibilityTypes = fetchDataUtil.fetchDefectDropdown(fileLocation, 10);
				Map<String, String> severityTypes = fetchDataUtil.fetchDefectDropdown(fileLocation, 11);
				Map<String, String> priorityTypes = fetchDataUtil.fetchDefectDropdown(fileLocation, 12);
				Map<String, String> assignToTypes = fetchDataUtil.fetchDefectDropdown(fileLocation, 13);
				if (defectfiles != null && !defectfiles.isEmpty()) {
					model.addAttribute("defectfiles", defectfiles);
				} else {
					logger.warn("defectfiles is empty");
				}
				if (defectList != null && !defectList.isEmpty()) {
					model.addAttribute("defects", defectList);
				} else {
					logger.warn("defects is empty");
				}
				if (logDefect != null && !logDefect.isEmpty()) {
					model.addAttribute("logDefect", logDefect);
				} else {
					logger.warn("logDefect is empty");
				}
				if (reproducibilityTypes != null && !reproducibilityTypes.isEmpty()) {
					model.addAttribute("reproducibilityTypes", reproducibilityTypes);
				} else {
					logger.warn("reproducibilityTypes is empty");
				}
				if (severityTypes != null && !severityTypes.isEmpty()) {
					model.addAttribute("severityTypes", severityTypes);
				} else {
					logger.warn("severityTypes is empty");
				}
				if (priorityTypes != null && !priorityTypes.isEmpty()) {
					model.addAttribute("priorityTypes", priorityTypes);
				} else {
					logger.warn("priorityTypes is empty");
				}
				if (assignToTypes != null && !assignToTypes.isEmpty()) {
					model.addAttribute("assignToTypes", assignToTypes);
				} else {
					logger.warn("assignToTypes is empty");
				}
				return "defectdetails";
			} else {
				redir.addAttribute("message", ShowMessage.SELECT_DEFECT_FILE_MSG);
				return "redirect:/Defects";
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		redir.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "redirect:/Defects";
	}

	/**
	 * This method is used to update the changes made in defect sheet
	 * 
	 * @param params
	 * @param redir
	 * @param model
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@RequestParam Map<String, String> params, RedirectAttributes redir, ModelMap model,
			HttpServletRequest request) {
		try {
			if (params != null && !params.isEmpty()) {
				String fileLocation = params.get("fileLocation");
				if (fileLocation != null && !fileLocation.trim().isEmpty()) {
					redir.addAttribute("fileLocation", fileLocation);
				} else {
					redir.addAttribute("message", "Please select a defect file");
					return "redirect:/Defects";
				}
				ArrayList<Defects> defectsList = new ArrayList<Defects>();
				int length = Integer.parseInt(request.getParameter("length"));
				for (int i = 1; i <= length; i++) {
					Defects defect = new Defects();
					defect.setId(params.get("id" + i));
					defect.setTestCaseid(params.get("testCaseid" + i));
					defect.setTestCaseName(params.get("testCaseName" + i));
					defect.setLogDefect(params.get("logDefect" + i));
					defect.setSummary(params.get("summary" + i));
					defect.setDescription(params.get("description" + i));
					defect.setReproducibility(params.get("reproducibility" + i));
					defect.setSeverity(params.get("severity" + i));
					defect.setPriority(params.get("priority" + i));
					defect.setAssignTo(params.get("assignTo" + i));
					defect.setStepsToReproduce(params.get("stepsToReproduce" + i));
					defect.setAdditionalInformation(params.get("additionalInformation" + i));
					defect.setUploadFilePath(params.get("uploadFilePath" + i));
					defect.setDefectUrl(params.get("defecturl" + i));
					defect.setDefectID(params.get("defectID" + i));
					defect.setLoggedDate(params.get("loggeddate" + i));
					defectsList.add(defect);
				}
				String updatedLocation = updateData.updateDefects(defectsList, fileLocation);
				if (updatedLocation != null && !updatedLocation.isEmpty()) {
					redir.addAttribute("message", ShowMessage.DEFECT_FILE_CHANGE_SUCCESS);
				}

				return "redirect:/defects";
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		redir.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "redirect:/defects";
	}

	/**
	 * This is used to update the defect to Defect logging tool
	 * 
	 * @param params
	 * @param model
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "data", method = RequestMethod.POST)
	public String data(@RequestParam Map<String, String> params, HttpServletRequest request, RedirectAttributes redir) {
		String orgFolder = (String) request.getSession().getAttribute("userOrganization");
		String userFolder = (String) request.getSession().getAttribute("userName");
		try {
			if (params != null && !params.isEmpty()) {
				String fileLocation = params.get("fileLocation");
				String fName = fileLocation.substring(fileLocation.lastIndexOf("\\")+1);
				String fLocation = fileLocation.substring(0,fileLocation.lastIndexOf("\\"));
				String fileName = fLocation.substring(fLocation.lastIndexOf("\\")+1).concat("\\" + fName);
				ArrayList<Defects> defectsList = new ArrayList<Defects>();
				int length = Integer.parseInt(request.getParameter("length"));
				for (int i = 1; i <= length; i++) {
					Defects defect = new Defects();
					defect.setId(request.getParameter("id" + i));
					defect.setTestCaseid(params.get("testCaseid" + i));
					defect.setTestCaseName(params.get("testCaseName" + i));
					defect.setLogDefect(params.get("logDefect" + i));
					defect.setSummary(params.get("summary" + i));
					defect.setDescription(params.get("description" + i));
					defect.setReproducibility(params.get("reproducibility" + i));
					defect.setSeverity(params.get("severity" + i));
					defect.setPriority(params.get("priority" + i));
					defect.setAssignTo(params.get("assignTo" + i));
					defect.setStepsToReproduce(params.get("stepsToReproduce" + i));
					defect.setAdditionalInformation(params.get("additionalInformation" + i));
					defect.setUploadFilePath(params.get("uploadFilePath" + i));
					defect.setDefectUrl(params.get("defecturl" + i));
					defect.setDefectID(params.get("defectID" + i));
					defect.setLoggedDate(params.get("loggeddate" + i));
					defectsList.add(defect);
				}
				updateData.updateDefects(defectsList, fileLocation);
				execute.updateProgressProperty(orgFolder, userFolder, "DefectFilename", fileName, request);
				execute.updateProgressProperty(orgFolder, userFolder, "DefectProgressStatus", "0", request);
				execute.updateProgressProperty(orgFolder, userFolder, "StopExecution", "No", request);
				execute.updateProgressProperty(orgFolder, userFolder, "DefectProgressMessage", "", request);
				execute.logDefect(fileLocation, request);

				return "redirect:/progress";
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		redir.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "redirect:/progress";
	}

	/**
	 * This is used to update the progress of the defect logging
	 * 
	 * @param params
	 * @param redirectAttributes
	 * @param model
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "progress", method = RequestMethod.GET)
	public String progress(RedirectAttributes redir, ModelMap model, HttpServletRequest request) {
		try {
			logger.info("Entered method progress");
			model.addAttribute("defectstatus", execute.getProperty("DefectProgressStatus", request));
			model.addAttribute("stopExecution", execute.getProperty("StopExecution", request));
			String defectProgressMessage = execute.getProperty("DefectProgressMessage", request);
			model.addAttribute("defectmessage", execute.getMessage(defectProgressMessage, request));
			model.addAttribute("defectfile", fetchDataUtil.defectFileLocation(request));
			return "defectprogress";

		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		redir.addAttribute("message", ShowMessage.TRY_AGAIN_INFO);
		return "redirect:/defects";
	}

	/**
	 * This is used to provide the files required for selenium grid execution
	 * 
	 * @param model
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "download")
	public void filesForSelenium(HttpServletResponse response, HttpServletRequest request) {
		logger.info("Entered filesForSelenium method");
		response.setContentType("text/html");
		PrintWriter out = null;
		FileInputStream fileInputStream = null;
		try {
			out = response.getWriter();
			String fileName = execute.getProperty("LatestNoderserverVersion", request) + ".zip";
			String orgFolder = (String) request.getSession().getAttribute("userOrganization");
			String userFolder = (String) request.getSession().getAttribute("userName");

			String filepath = esaipBaseLocation + "\\" + orgFolder + "\\"
					+ util.getProjectDataProperty("userFolderKey") + "\\" + userFolder;
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			fileInputStream = new FileInputStream(filepath + "\\" + fileName);
			int i;
			while ((i = fileInputStream.read()) != -1) {
				out.write(i);
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			try {
				if (fileInputStream != null)
					fileInputStream.close();
			} catch (IOException e) {
				logger.error("Exception : " + ShowMessage.getStackTrace(e));
			}
			if (out != null)
				out.close();
		}
	}

	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "UploadMasterPlan", method = RequestMethod.GET)
	public String getUploadPage(HttpServletRequest request, ModelMap model,
			@RequestParam(value = "message", required = false) String message) {
		List<Project> projectList = null;
		try {
			Project userProject = (Project) request.getSession().getAttribute("userProject");
			if (userProject == null) {
				projectList = (List<Project>) request.getSession().getAttribute("userProjectsList");
				model.addAttribute("projectList", projectList);
				return "projectselection";
			} else {
				// updating in the generic.properties
				updateProperty(userProject.getName(), request);
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		if (message != null && !message.trim().isEmpty()) {
			model.addAttribute("message", message);
		}
		return "upload";
	}

	/**
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request, ModelMap model,
			RedirectAttributes redir) {
		String result = "";
		InputStream fileContent = null;
		OutputStream out = null;
		try {
			if (file == null) {
				redir.addAttribute("message", ShowMessage.FILE_NOT_SELECTED);
				return "redirect:/UploadMasterPlan";
			}
			String fileName = file.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			fileContent = file.getInputStream();
			if (fileType.equals(".xlsx")) {
				String tempFileLocation = updateData.createTempfile(fileContent);
				String value = validateData.validateUploadedData(tempFileLocation);
				updateData.removeUnusedRows(request);
				if (value.equals("success")) {
					updateData.backupMasterPlan(request);
					updateData.updateMasterFile(tempFileLocation, request);
					result = ShowMessage.MASTERPLAN_UPLOAD_SUCCESS;
				} else {
					result = value;
				}
			} else {
				result = ShowMessage.MASTERPLAN_UPLOAD_FAILED;
			}
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
			if (fileContent != null) {
				try {
					fileContent.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
		}
		request.getSession().setAttribute("uploadResult", result);
		return "redirect:/UploadMasterPlan";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "masterPage", method = RequestMethod.GET)
	public void masterPage(HttpServletResponse response) {
		InputStream fileInputStream = null;
		PrintWriter out = null;
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			String filepath = automationBaseLocation + "/src/Repository/resources/MasterPlanTemplate.xlsx";
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename=\"MasterPlan.xlsx\"");
			fileInputStream = new FileInputStream(filepath);
			int i;
			while ((i = fileInputStream.read()) != -1) {
				out.write(i);
			}
		} catch (IOException e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					logger.error("Exception : " + ShowMessage.getStackTrace(e));
				}
			}
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "CreateMasterPlan",method = RequestMethod.GET)
	public String createMasterPlan(ModelMap model, HttpServletRequest request) {
		return "createmasterplan";
	}

	/**
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "teststeps", method = RequestMethod.POST)
	public String testSteps(HttpServletResponse response, HttpServletRequest request) {
		try {
			ArrayList<TestSteps> testSteps = new ArrayList<>();
			int size = Integer.parseInt(request.getParameter("length").trim());
			for (int i = 1; i < size; i++) {
				TestSteps testStep = new TestSteps();
				testStep.setTcId(request.getParameter("tcid" + i));
				testStep.setBusinessRequirement(request.getParameter("businessrequirement" + i));
				testStep.setModule(request.getParameter("module" + i));
				testStep.setTestcase(request.getParameter("testcase" + i));
				testStep.setSlNo(request.getParameter("slno" + i));
				testStep.setKeyword(request.getParameter("keyword" + i));
				testStep.setObject(request.getParameter("object" + i));
				testStep.setTestObjectData(request.getParameter("testobjectdata" + i));
				testStep.setClassfile(request.getParameter("classfile" + i));
				testStep.setDescription(request.getParameter("description" + i));
				testSteps.add(testStep);
			}
			createMasterPlan.createTestSteps(testSteps, "D:\\MasterPlan.xlsx");
			request.getSession().setAttribute("locators", ExcelUtil.getDropDownValues("locators"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "createallobjects";
	}

	/**
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "allobjects", method = RequestMethod.GET)
	public String allObjects(HttpServletRequest request) {
		ArrayList<AllObjects> allObjects = new ArrayList<>();
		try {
			int size = Integer.parseInt(request.getParameter("length"));
			for (int i = 1; i < size; i++) {
				AllObjects allObject = new AllObjects();
				allObject.setSlNo(request.getParameter("slno" + i));
				allObject.setScreenName(request.getParameter("screenname" + i));
				allObject.setObjectName(request.getParameter("objectname" + i));
				allObject.setType(request.getParameter("type" + i));
				allObject.setValues(request.getParameter("value" + i));
				allObject.setComments(request.getParameter("comments" + i));
				allObjects.add(allObject);
			}
			createMasterPlan.createAllObjects(allObjects, "D:\\MasterPlan.xlsx");
			request.getSession().setAttribute("status",
					"Masterplan created successfully. Click <a href='save?fileLocation=" + "D:\\MasterPlan.xlsx"
							+ "'>here</a> to download.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "createallobjects";
	}

	/**
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "masterplan", method = RequestMethod.POST)
	public String masterplan(HttpServletRequest request) {
		try {
			updateData.backupMasterPlan(request);
			ArrayList<MasterPlan> masterPlan = new ArrayList<>();
			int size = Integer.parseInt(request.getParameter("length"));
			for (int i = 1; i < size; i++) {
				MasterPlan plan = new MasterPlan();
				plan.setId(request.getParameter("slno" + i));
				plan.setBusinessRequirement(request.getParameter("businessrequirement" + i));
				plan.setDescription(request.getParameter("description" + i));
				plan.setImpactedBRs(request.getParameter("impactedbr" + i));
				plan.setIncludeImpactedBRs(request.getParameter("includeimpactedbrs" + i));
				plan.setModules(request.getParameter("modules" + i));
				plan.setModulesInclude(request.getParameter("includeModule" + i));
				plan.setTestCases(request.getParameter("testcases" + i));
				plan.setTestCasesInclude(request.getParameter("includetestcase" + i));
				plan.setTestCaseId(request.getParameter("testcaseid" + i));
				plan.setCriticality(request.getParameter("criticallity" + i));
				plan.setRepeatablity(request.getParameter("iterations" + i));
				masterPlan.add(plan);
			}
			createMasterPlan.createMasterSheet(masterPlan, "D:\\MasterPlan.xlsx");
			request.getSession().setAttribute("keywords", ExcelUtil.getDropDownValues("keywords"));
			request.getSession().setAttribute("classfile", ExcelUtil.getDropDownValues("classfile"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "createteststeps";
	}

	/**
	 * This is used to stop the execution
	 * 
	 * @param request
	 */
	@RequestMapping(value = "stop", method = RequestMethod.GET)
	public void stop(HttpServletRequest request) {
		String orgFolder = (String) request.getSession().getAttribute("userOrganization");
		String userFolder = (String) request.getSession().getAttribute("userName");
		try {
			execute.updateProgressProperty(orgFolder, userFolder, "StopExecution", "Yes", request);
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
	}

	/**
	 * logout closes the current logged in session
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request) {
		try {
			request.getSession().invalidate();
			return "redirect:/";
		} catch (Exception e) {
			logger.error("Exception : " + ShowMessage.getStackTrace(e));
		}
		return "redirect:/";
	}

	/**
	 * 
	 * @param projectName
	 * @param request
	 * @throws Exception
	 */
	private void updateProperty(String projectName, HttpServletRequest request) throws Exception {
		String orgFolder = (String) request.getSession().getAttribute("userOrganization");
		String userFolder = (String) request.getSession().getAttribute("userName");
		String value = "${directory}/" + orgFolder + "/" + util.getProjectDataProperty("projectFolderKey") + "/"
				+ projectName + "/" + ProjectFolderEnum.LOGS.getFolderName() + "/${resultfilename}.log";
		execute.updateProgressProperty(orgFolder, userFolder, "log4j.appender.fileout.File", value, request);
		execute.updateProgressProperty(orgFolder, userFolder, "projectName", projectName, request);
	}
}
