package com.estuate.esaip2_0.exception;

/**
 * <ul>
 * <li>Title: ErrorMessage</li>
 * <li>Description: The error messages to be shown during exeption will be defined here.</li>
 * <li>Created By: Radhika GopalRaj</li>
 * </ul>
 */
public class ShowMessage {
	public static String INVALID_EMAIL_ID="Entered e-mail ID does not exist. Please enter a valid e-mail ID.";
	public static String LOGIN_ATTEMPT_COMPLETED="Maximun login attempts exceeded. Please contact administrator to reset the Password.";
	public static String INVALID_LOGIN_CREDENTIALS="Invalid e-mail ID or Password. Please try again.";
	public static String RESET_PASSWORD_SUCCESS="Password reset successful";
	public static String RESET_PASSWORD_FAILED="Password reset failed. Please try again.";
	public static String NO_RECORDS_FOUND_TO_RESET="Password reset is up to date.";
	public static String GROUP_NOT_ASSOCIATED_TO_ROLE="User is not associated with any group. Please contact administrator.";
	public static String NO_PROJECTS_FOUND_FOR_USER="None of the projects are associated for the current user. Please contact administrator.";
	
	public static String USERDETAIL_OBJECT_NULL="UserDetail is empty";
	public static String PROJECT_OBJECT_NULL="Project detail is empty";
	public static String ORGANIZATION_LIST_NULL="Organization List is found to be empty";
	public static String ROLE_lIST_EMPTY="Role List is found to be empty";
	public static String GROUP_lIST_EMPTY="Role List is found to be empty";
	public static String PROJECT_lIST_EMPTY="Role List is found to be empty";
	public static String GROUP_OBJECT_EMPTY="Group Object is empty";
	public static String ATTEMPTCOUNT_OBJECT_EMPTY="AttemptCount Object is empty";
	public static String ATTEMPTHISTORY_OBJECT_EMPTY="AttemptHistory Object is empty";
	public static String ASSIGNED_GROUP_EMPTY="Atleast one group should be assigned to the user.";
	public static String ASSIGNED_ROLE_EMPTY="Atleast one role should be assigned to a Group. Please try again.";
	public static String PROJECT_SELECTION_EMPTY="Altleast one project should be assigned to the user.";
	
	public static String ORG_CREATE_SUCCESS="Organization created successfully";
	public static String ADMIN_CREATE_SUCCESS="Admin created successfully";
	public static String USER_CREATE_SUCCESS="User created successfully";
	public static String PROJECT_CREATE_SUCCESS="Project created successfully";
	public static String GROUP_CREATE_SUCCESS="Group created successfully";
	
	public static String ORG_CREATE_FAILED="Failed to create Organization";
	public static String ADMIN_CREATE_FAILED="Failed to create Admin";
	public static String USER_CREATE_FAILED="Failed to create User";
	public static String PROJECT_CREATE_FAILED="Failed to create Project";
	public static String GROUP_CREATE_FAILED="Failed to create Group";
	
	public static String ORG_UPDATE_SUCCESS="Organization details updated successfully";
	public static String ADMIN_UPDATE_SUCCESS="Admin details updated successfully";
	public static String USER_UPDATE_SUCCESS="User details updated successfully";
	public static String PROJECT_UPDATE_SUCCESS="Project details updated successfully";
	public static String GROUP_UPDATE_SUCCESS="Group updated successfully";
	
	public static String ORG_UPDATE_FAILED="Failed to update Organization details";
	public static String ADMIN_UPDATE_FAILED="Failed to update Admin details";
	public static String USER_UPDATE_FAILED="Failed to update User details";
	public static String PROJECT_UPDATE_FAILED="Failed to update Project details";
	public static String GROUP_UPDATE_FAILED="Failed to update the Group";
	
	public static String ORG_DELETE_SUCCESS="Organization deleted successfully";
	public static String ADMIN_DELETE_SUCCESS="Admin deleted successfully";
	public static String USER_DELETE_SUCCESS="User deleted successfully";
	public static String PROJECT_DELETE_SUCCESS="Project deleted successfully";	
	public static String GROUP_DELETE_SUCCESS="Group deleted successfully";
	
	public static String ORG_DELETE_FAILED="Failed to delete Organization";
	public static String ADMIN_DELETE_FAILED="Failed to delete Admin";
	public static String USER_DELETE_FAILED="Failed to delete User";
	public static String PROJECT_DELETE_FAILED="Failed to delete Project";
	public static String GROUP_DELETE_FAILED="Failed to delete Group";
	
	public static String ORG_FOLDER_CREATE_FAILED="Failed creating organization folder. Organization not created.";
	public static String USER_FOLDER_CREATE_FAILED="Failed creating user folder or neccessary files. User not created.";
	public static String PROJECT_FOLDER_CREATE_FAILED="Failed creating project folder or neccesary files. Project not created.";
	public static String ASSOCIATION_CREATE_FAILED_FOR_ADMIN="Failed creating association to admin. Admin not created.";
	
	public static String GROUP_ASSOCIATION_CREATION_FAILED="Failed creating association with a role. Group not created.";
	public static String ADMIN_ASSOCIATION_UPDATE_FAILED="Failed updating related associations to Admin.";
	public static String USER_ASSOCIATION_UPDATE_FAILED="Failed updating related associations to User.";
	
	public static String DROPDOWN_VALUES_EMPTY="Drop down values found to be empty. Please check the 'utility' property file.";
	
	public static String DEFECT_FILES_EMPTY="Defect file not found";
	public static String RESULT_FILES_EMPTY="Result file not found";
	public static String MASTER_PLAN_EMPTY="Master Plan not found for the project";
	public static String RUN_PLANS_EMPTY="Run Plan not found";
	public static String MASTERPLAN_UPLOAD_SUCCESS="Master Plan uploaded successfully.";
	public static String MASTERPLAN_UPLOAD_FAILED="Upload Failed. Only files of .xlsx format are allowed.";
	public static String SELECT_RESULT_FILE_MSG="Please select a result file to view";
	public static String SELECT_RESULT_FILE_TO_DOWNLOAD="	Please select a result file to download";
	public static String SELECT_RESULT_FILE_TO_DOWNLOAD_LOG="	Please select a result file to download logs";
	public static String SELECT_RUN_PLAN_MSG="Please select a run plan to view";
	public static String SELECT_DEFECT_FILE_MSG="Please select a defect file to view";
	public static String DEFECT_FILE_CHANGE_SUCCESS="Changes saved successfully";
	public static String GROUP_ASSOCIATION_DELETE_FAILED="Failed deleting group associations. Group not deleted.";
	public static String USER_ASSOCIATION_DELETE_FAILED="Failed deleting user associations. User not deleted.";
	public static String ADMIN_ASSOCIATION_DELETE_FAILED="Failed deleting admin associations. Admin not deleted.";
	public static String PROJECT_ASSOCIATION_DELETE_FAILED="Failed deleting project associations. Project not deleted.";
	public static String FILE_NOT_SELECTED="Please select a '.xlsx' file to upload.";
	
	public static String ATTEMPTCOUNT_RECORD_NOT_DELETED="Records in attemptcount table is not deleted after successful login";

	public static String TRY_AGAIN_INFO="Please try again. If problem persists please contact Administrator.";
	
	public static String CREATE_RUNPLAN_FAILED="Problem while creating Run Plan. Please try again.";
	public static String PROJECT_WITH_NO_MASTERPLAN="Selected Project : ";
	
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	public static String getStackTrace(Exception e) {
		e.getMessage();
		e.printStackTrace();
		return e.getLocalizedMessage();
	}
	/**
	 * 
	 * @param msg
	 * @param count
	 * @return
	 */
	public static String processErrorMessage(String msg, int count) {
		return msg.concat("\n Remaining Attempts is "+count);
	}
	
	public static String processProjectMsg(String projectName) {
		return "Selected project : '"+projectName+"'.Please proceed to continue.";
	}
	
}
