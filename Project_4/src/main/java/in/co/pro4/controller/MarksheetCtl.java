package in.co.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.pro4.bean.BaseBean;
import in.co.pro4.bean.MarksheetBean;
import in.co.pro4.exception.ApplicationException;
import in.co.pro4.exception.DuplicateRecordException;
import in.co.pro4.model.MarksheetModel;
import in.co.pro4.model.StudentModel;
import in.co.pro4.utility.DataUtility;
import in.co.pro4.utility.DataValidator;
import in.co.pro4.utility.PropertyReader;
import in.co.pro4.utility.ServletUtility;

/**
 * @author Uttam Singh
 *
 */
@WebServlet(name = "MarksheetCtl", urlPatterns = { "/ctl/MarksheetCtl" })
public class MarksheetCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(MarksheetCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * in.co.pro4.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		StudentModel model = new StudentModel();
		try {
			List l = model.list();
			request.setAttribute("studentList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * in.co.pro4.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("MarksheetCtl Method validate Started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number "));
			pass = false;
		} else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", "Roll No. Should be in Proper Formate");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("studentld"))) {
			request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("physics"))
				&& !DataValidator.isInteger(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.require", "Physics Marks "));
			pass = false;

		} else if (DataUtility.getInt(request.getParameter("physics")) < 0) {
			request.setAttribute("physics", "Marks can Not less then 0 ");
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("physics")) > 100) {
			request.setAttribute("physics", "Marks can Not More then 100");
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("physics"))
				&& !DataValidator.isInteger(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.integer", "Physics Marks "));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.require", "Chemistry Mark "));
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("chemistry")) > 100) {
			request.setAttribute("chemistry", "Marks can Not More then 100");
			pass = false;

		} else if (DataUtility.getInt(request.getParameter("chemistry")) < 0) {
			request.setAttribute("chemistry", "Marks can Not less then 0 ");
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("chemistry"))
				&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "Chemistry Marks "));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.require", "Maths Marks "));
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("maths")) > 100) {
			request.setAttribute("maths", "Marks can Not More then 100");
			pass = false;

		} else if (DataUtility.getInt(request.getParameter("maths")) < 0) {
			request.setAttribute("maths", "Marks can Not less then 0 ");
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("maths"))
				&& !DataValidator.isInteger(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.integer", "Maths Marks "));
			pass = false;
		}

		log.debug("MarksheetCtl Method validate Ended");
		return pass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.pro4.controller.BaseCtl#populatebean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("MarksheetCtl Method populatebean Started");

		MarksheetBean bean = new MarksheetBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		bean.setMaths(DataUtility.getInt(request.getParameter("maths")));
		bean.setStudentId(DataUtility.getLong(request.getParameter("studentld")));

		// System.out.println(">>>>>>>>>>>>>" +request.getParameter("studentId"));

		populateDTO(bean, request);
		System.out.println("Population done");
		log.debug("MarksheetCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Contains display logic.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("MarksheetCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		MarksheetModel model = new MarksheetModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			MarksheetBean bean;
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("MarksheetCtl Method doGet Ended");
	}

	/**
	 * Contains submit logic.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("MarksheetCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		MarksheetBean bean = (MarksheetBean) populateBean(request);

		// get model
		MarksheetModel model = new MarksheetModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Marksheet is Successfully Updated ", request);

				} else {
					long pk = model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Marksheet is Successfully Added ", request);

				}

			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Roll no already exists", request);
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
		}
		ServletUtility.setBean(bean, request);
		ServletUtility.forward(getView(), request, response);

		log.debug("MarksheetCtl Method doPost Ended");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.pro4.controller.BaseCtl#getview()
	 */
	@Override
	protected String getView() {
		return ORSView.MARKSHEET_VIEW;
	}
}
