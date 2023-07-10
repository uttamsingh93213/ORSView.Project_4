package in.co.pro4.utility;

/**
 * Contains Email Message.
 * 
 * @author Uttam Singh
 *
 */
public class EmailMassage {

	private String to = null;
	private String from = null;
	private String cc = null;
	private String bcc = null;
	private String subject = null;
	private String message = null;

	private int massageType = TEXT_MSG;
	public static final int HTML_MSG = 1;
	public static final int TEXT_MSG = 2;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMassageType() {
		return massageType;
	}

	public void setMassageType(int massageType) {
		this.massageType = massageType;
	}

}
