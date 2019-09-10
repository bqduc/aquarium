/**
 * 
 */
package net.sunrise.cdix.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import net.sunrise.cdix.entity.ReserveResource;
import net.sunrise.cdix.form.ReserveResourceUploadForm;
import net.sunrise.cdix.service.ReserveResourceService;
import net.sunrise.cdx.domain.entity.Configuration;
import net.sunrise.cdx.service.ConfigurationService;
import net.sunrise.common.utility.CompressionUtility;
import net.sunrise.controller.base.BaseController;

/**
 * @author bqduc
 *
 */
@Controller
@RequestMapping("/cdix/pr")
public class ReserveResourceController extends BaseController {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7161179463290360704L;

	private static final String UPLOAD_FORM = "myUploadForm";
	private static final String PAGE_PREFIX = "pages/cdix/";

	@Inject
	private ReserveResourceService persistenceResourceService;

	@Inject
	private ConfigurationService configurationService;

	@RequestMapping(path = { "/", "" }, method = RequestMethod.GET)
	public String viewDefaultPage(Model model) {
		log.debug("Enter view persistence resources");

		ReserveResourceUploadForm myUploadForm = new ReserveResourceUploadForm();
		model.addAttribute(UPLOAD_FORM, myUploadForm);

		return PAGE_PREFIX + CdixPageFlows.PERSISTENCE_RESOURCE_BROWSE.getPageFlow();
	}

	// GET: Hiển thị trang form upload
	@RequestMapping(value = "/uploadOneResource", method = RequestMethod.GET)
	public String uploadOneFileHandler(Model model) {

		ReserveResourceUploadForm myUploadForm = new ReserveResourceUploadForm();
		model.addAttribute(UPLOAD_FORM, myUploadForm);

		return PAGE_PREFIX + CdixPageFlows.UPLOAD_ONE_RESOURCE.getPageFlow();
	}

	// POST: Sử lý Upload
	@RequestMapping(value = "/uploadOneResource", method = RequestMethod.POST)
	public String uploadOneFileHandlerPOST(HttpServletRequest request, //
			Model model, //
			@ModelAttribute(UPLOAD_FORM) ReserveResourceUploadForm myUploadForm) {

		return this.doUpload(request, model, myUploadForm,
				PAGE_PREFIX + CdixPageFlows.PERSISTENCE_RESOURCE_BROWSE.getPageFlow());

	}

	// GET: Hiển thị trang form upload
	@RequestMapping(value = "/uploadMultipleResources", method = RequestMethod.GET)
	public String uploadMultiFileHandler(Model model) {

		ReserveResourceUploadForm myUploadForm = new ReserveResourceUploadForm();
		model.addAttribute(UPLOAD_FORM, myUploadForm);

		return PAGE_PREFIX + CdixPageFlows.PERSISTENCE_RESOURCE_BROWSE.getPageFlow();
	}

	// POST: Sử lý Upload
	@RequestMapping(value = "/uploadMultipleResources", method = RequestMethod.POST)
	public String uploadMultiFileHandlerPOST(HttpServletRequest request, //
			Model model, //
			@ModelAttribute(UPLOAD_FORM) ReserveResourceUploadForm myUploadForm) {

		return this.doUpload(request, model, myUploadForm,
				PAGE_PREFIX + CdixPageFlows.PERSISTENCE_RESOURCE_BROWSE.getPageFlow());

	}

	@GetMapping("/downloadResource/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
		// Load file from database
		ReserveResource fetchedBizObject = persistenceResourceService.getObject(Long.valueOf(fileId));
		byte[] decompressedBytes = null;
		try {
			decompressedBytes = CompressionUtility.builder().build().decompress(fetchedBizObject.getData());
		} catch (IOException | DataFormatException e) {
			log.error(e);
			decompressedBytes = fetchedBizObject.getData();
		}
		return ResponseEntity
				.ok()
				.contentType(MediaType.parseMediaType(fetchedBizObject.getType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fetchedBizObject.getName() + "\"")
				.body(new ByteArrayResource(decompressedBytes));
	}

	private String doUpload(HttpServletRequest request, Model model, //
			ReserveResourceUploadForm uploadFilesForm, String targetPage) {

		String description = uploadFilesForm.getDescription();
		log.debug("Description: " + description);

		// Thư mục gốc upload file.
		/*
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		log.debug("Upload root path: " + uploadRootPath);

		File uploadRootDir = new File(uploadRootPath);
		// Tạo thư mục gốc upload nếu nó không tồn tại.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		*/
		Optional<Configuration> drbxConfig = configurationService.getOne("DRBX_token");
		System.out.println(drbxConfig);
		MultipartFile[] fileDatas = uploadFilesForm.getFileDatas();
		//
		List<File> uploadedFiles = new ArrayList<File>();
		List<String> failedFiles = new ArrayList<String>();
		CompressionUtility compressionUtility = CompressionUtility.builder().build();
		byte[] compressedBytes = null;
		for (MultipartFile fileData : fileDatas) {

			// Tên file gốc tại Client.
			String name = fileData.getOriginalFilename();
			// System.out.println("Client File Name = " + name);

			if (name != null && name.length() > 0) {
				try {
					// Tạo file tại Server.
					//File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
					/*
					 * BufferedOutputStream stream = new BufferedOutputStream(new
					 * FileOutputStream(serverFile)); stream.write(fileData.getBytes());
					 * stream.close();
					 */
					//
					//uploadedFiles.add(serverFile);
					compressedBytes = compressionUtility.compress(fileData.getBytes());
					this.persistenceResourceService.saveOrUpdate(
							ReserveResource.builder()
							.data(compressedBytes)
							.name(name).type(fileData.getContentType()).description(description).build());
				} catch (Exception e) {
					log.error("Error while processing file: " + name + ". Exception: " + e);
					failedFiles.add(name);
				}
			}
		}
		model.addAttribute("description", description);
		model.addAttribute("uploadedFiles", uploadedFiles);
		model.addAttribute("failedFiles", failedFiles);
		return targetPage;// "uploadResult";
	}
}
