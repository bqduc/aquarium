/**
 * 
 */
package net.sunrise.cdix.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import net.sunrise.cdix.entity.PersistenceResource;
import net.sunrise.cdix.form.PersistenceResourceUploadForm;
import net.sunrise.cdix.service.PersistenceResourceService;
import net.sunrise.controller.base.BaseController;

/**
 * @author bqduc
 *
 */
@Controller
@RequestMapping("/cdix/pr")
public class PersistenceResourceController extends BaseController {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7161179463290360704L;

	private static final String UPLOAD_FORM = "myUploadForm";
	private static final String PAGE_PREFIX = "pages/cdix/";

	@Inject 
	private PersistenceResourceService persistenceResourceService;

	@RequestMapping(path = { "/", "" }, method = RequestMethod.GET)
	public String viewDefaultPage(Model model) {
		System.out.println("Enter view persistence resources");

		PersistenceResourceUploadForm myUploadForm = new PersistenceResourceUploadForm();
		model.addAttribute(UPLOAD_FORM, myUploadForm);

		return PAGE_PREFIX + CdixPageFlows.PERSISTENCE_RESOURCE_BROWSE.getPageFlow();
	}

	// GET: Hiển thị trang form upload
	@RequestMapping(value = "/uploadOneResource", method = RequestMethod.GET)
	public String uploadOneFileHandler(Model model) {

		PersistenceResourceUploadForm myUploadForm = new PersistenceResourceUploadForm();
		model.addAttribute(UPLOAD_FORM, myUploadForm);

		return PAGE_PREFIX + CdixPageFlows.UPLOAD_ONE_RESOURCE.getPageFlow();
	}

	// POST: Sử lý Upload
	@RequestMapping(value = "/uploadOneResource", method = RequestMethod.POST)
	public String uploadOneFileHandlerPOST(HttpServletRequest request, //
			Model model, //
			@ModelAttribute(UPLOAD_FORM ) PersistenceResourceUploadForm myUploadForm) {

		return this.doUpload(request, model, myUploadForm, PAGE_PREFIX + CdixPageFlows.UPLOAD_ONE_RESOURCE.getPageFlow());

	}

	// GET: Hiển thị trang form upload
	@RequestMapping(value = "/uploadMultipleResources", method = RequestMethod.GET)
	public String uploadMultiFileHandler(Model model) {

		PersistenceResourceUploadForm myUploadForm = new PersistenceResourceUploadForm();
		model.addAttribute(UPLOAD_FORM, myUploadForm);

		return PAGE_PREFIX + CdixPageFlows.UPLOAD_MULTIPLE_RESOURCES.getPageFlow();
	}

	// POST: Sử lý Upload
	@RequestMapping(value = "/uploadMultipleResources", method = RequestMethod.POST)
	public String uploadMultiFileHandlerPOST(HttpServletRequest request, //
			Model model, //
			@ModelAttribute(UPLOAD_FORM) PersistenceResourceUploadForm myUploadForm) {

		return this.doUpload(request, model, myUploadForm, PAGE_PREFIX + CdixPageFlows.UPLOAD_MULTIPLE_RESOURCES.getPageFlow());

	}

	private String doUpload(HttpServletRequest request, Model model, //
			PersistenceResourceUploadForm myUploadForm, String targetPage) {

		String description = myUploadForm.getDescription();
		System.out.println("Description: " + description);

		// Thư mục gốc upload file.
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		System.out.println("uploadRootPath=" + uploadRootPath);

		File uploadRootDir = new File(uploadRootPath);
		// Tạo thư mục gốc upload nếu nó không tồn tại.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		MultipartFile[] fileDatas = myUploadForm.getFileDatas();
		//
		List<File> uploadedFiles = new ArrayList<File>();
		List<String> failedFiles = new ArrayList<String>();

		for (MultipartFile fileData : fileDatas) {

			// Tên file gốc tại Client.
			String name = fileData.getOriginalFilename();
			System.out.println("Client File Name = " + name);

			if (name != null && name.length() > 0) {
				try {
					// Tạo file tại Server.
					File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
					/*
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					*/
					//
					uploadedFiles.add(serverFile);
					
					this.persistenceResourceService.saveOrUpdate(PersistenceResource.builder()
							.data(fileData.getBytes())
							.name(name)
							.type(fileData.getContentType())
							.description(description)
							.build());
					System.out.println("Write file: " + serverFile);
				} catch (Exception e) {
					System.out.println("Error Write file: " + name);
					failedFiles.add(name);
				}
			}
		}
		model.addAttribute("description", description);
		model.addAttribute("uploadedFiles", uploadedFiles);
		model.addAttribute("failedFiles", failedFiles);
		return targetPage;//"uploadResult";
	}
}
