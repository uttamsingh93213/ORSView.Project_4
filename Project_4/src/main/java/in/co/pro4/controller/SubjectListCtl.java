package in.co.pro4.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.pro4.bean.BaseBean;
import in.co.pro4.bean.CourseBean;
import in.co.pro4.bean.SubjectBean;
import in.co.pro4.exception.ApplicationException;
import in.co.pro4.exception.DatabaseException;
import in.co.pro4.model.CourseModel;
import in.co.pro4.model.SubjectModel;
import in.co.pro4.utility.DataUtility;
import in.co.pro4.utility.PropertyReader;
import in.co.pro4.utility.ServletUtility;

/**
 * @author Uttam Singh
 *
 */
@WebServlet(name = "SubjectListCtl", urlPatterns = { "/ctl/SubjectListCtl" })
public class SubjectListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(SubjectListCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.pro4.controller.BaseCtl#preload(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		SubjectModel subjectModel = new SubjectModel();
		CourseModel courseModel = new CourseModel();

		try {
			List<SubjectBean> subjectList = subjectModel.list(0, 0);
			request.setAttribute("subjectList", subjectList);

			List<CourseBean> courseList = courseModel.list(0, 0);
			request.setAttribute("courseList", courseList);

		} catch (ApplicationException e) {
			log.error(e);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.pro4.controller.BaseCtl#populatedbean(javax.servlet.http.
	 * HttpServletRequest)
	 */

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		SubjectBean bean = new SubjectBean();
		bean.setId(DataUtility.getLong(request.getParameter("subjectId")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		populateDTO(bean, request);
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
		log.debug("SubjectListCtl doGet Start");

		System.out.println("insdie get method subject ctl");
		List list = null;
		List next = null;

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		SubjectBean bean = (SubjectBean) populateBean(request);

		@SuppressWarnings("unused")
		String op = DataUtility.getString(request.getParameter("operation"));

		SubjectModel model = new SubjectModel();
		try {

			list = model.search(bean, pageNo, pageSize);
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		try {
			next = model.search(bean, pageNo + 1, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list == null || list.size() == 0) {
			ServletUtility.setErrorMessage("No record found ", request);
		}
		request.setAttribute("nextListSize", next.size());
		ServletUtility.setList(list, request);
		Collections.sort(list);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("SubjectListCtl doGet End");
	}

	/**
	 * Contains submit logic.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("SubjectListCtl doPost Start");
		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		SubjectBean bean = (SubjectBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		SubjectModel model = new SubjectModel();

		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					SubjectBean deletebean = new SubjectBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.Delete(deletebean);
						ServletUtility.setSuccessMessage("Data is deleted successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);
			Collections.sort(list);
			ServletUtility.setBean(bean, request);
			next = model.search(bean, pageNo + 1, pageSize);
			ServletUtility.setList(list, request);
			if (!OP_DELETE.equalsIgnoreCase(op)) {
				if (list == null || list.size() == 0) {
					ServletUtility.setErrorMessage("No record found ", request);
				}
			}
			// ServletUtility.setList(list, request);
			// ServletUtility.setBean(bean, request);
			request.setAttribute("nextListSize", next.size());
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("SubjectListCtl doGet End");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.pro4.controller.BaseCtl#getview()
	 */
	@Override
	protected String getView() {
		return ORSView.SUBJECT_LIST_VIEW;
	}
}
