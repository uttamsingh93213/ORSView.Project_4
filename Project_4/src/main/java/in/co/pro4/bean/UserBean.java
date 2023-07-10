package in.co.pro4.bean;

import java.sql.Timestamp;
import java.util.Date;
/**
 * @author Uttam Singh
 *
 */
public class UserBean extends BaseBean {

	public static final String ACTIVE = "Active";
	public static final String INACTIVE = "Inactive";

	private String firstName;
	private String lastName;
	private String logIn;
	private String password;
	private String ConfirmPassword;
	private Date dob;
	private String mobileNo;
	private long roleId;
	private int unSuccessfullLogin;
	private String gender;
	private Timestamp lastLogin;
	private String lock = INACTIVE;
	private String registeredIP;
	private String lastloginIP;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogIn() {
		return logIn;
	}

	public void setLogIn(String logIn) {
		this.logIn = logIn;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return ConfirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		ConfirmPassword = confirmPassword;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public int getUnSuccessfullLogin() {
		return unSuccessfullLogin;
	}

	public void setUnSuccessfullLogin(int unSuccessfullLogin) {
		this.unSuccessfullLogin = unSuccessfullLogin;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLock() {
		return lock;
	}

	public void setLock(String lock) {
		this.lock = lock;
	}

	public String getRegisteredIP() {
		return registeredIP;
	}

	public void setRegisteredIP(String registeredIP) {
		this.registeredIP = registeredIP;
	}

	public String getLastloginIP() {
		return lastloginIP;
	}

	public void setLastloginIP(String lastloginIP) {
		this.lastloginIP = lastloginIP;
	}

	public static String getActive() {
		return ACTIVE;
	}

	public static String getInactive() {
		return INACTIVE;
	}

	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return firstName + " " + lastName + " "+dob;
	}

}
