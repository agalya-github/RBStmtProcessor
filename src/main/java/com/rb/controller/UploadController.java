package com.rb.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rb.model.CusStmtModel;
import com.rb.util.UploadUtil;

@Controller
public class UploadController {

	private static final Logger log = LogManager
			.getLogger(UploadController.class);

	@GetMapping("/")
	public String index() {
		return "upload";
	}

	@PostMapping("/upload")
	public String singleFileUpload(
			@RequestParam("file") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes) {

		String fileName = null;
		String fileExtn = null;
		File file = null;
		List<CusStmtModel> statements = null;
		List<CusStmtModel> failedStatements = null;

		try {
			// Redirect if file is not selected or the content is empty
			if (multipartFile.isEmpty()) {
				redirectAttributes.addFlashAttribute("message",
						"Please select a file to upload");
				return "redirect:uploadStatus";
			}

			// Convert multipart file as a file
			file = UploadUtil.convertMultiPartToFile(multipartFile);
			fileName = multipartFile.getOriginalFilename();
			fileExtn = FilenameUtils.getExtension(fileName);

			// Redirect with appropriate message if the user try to upload file
			// format other than csv and xml
			if (fileExtn.equalsIgnoreCase(UploadUtil.FILE_TYPE_CSV)) {
				statements = UploadUtil.populateFromCSV(file);
			} else if (fileExtn.equalsIgnoreCase(UploadUtil.FILE_TYPE_XML)) {
				statements = UploadUtil.populateFromXML(file);
			} else {
				redirectAttributes.addFlashAttribute("message",
						"File type should be csv or xml");
				return "redirect:uploadStatus";
			}

			// Process the records
			failedStatements = UploadUtil.processStatements(statements);
			redirectAttributes.addFlashAttribute("message", "Your file "
					+ fileName + " has been preocessed successfully");

			if (failedStatements != null && !failedStatements.isEmpty()) {
				// Add if any failed transaction there in order to display the
				// record
				redirectAttributes.addFlashAttribute("failedStatements",
						failedStatements);
			}

		} catch (FileNotFoundException fileNotFoundException) {
			redirectAttributes.addFlashAttribute(
					"message",
					"You successfully uploaded "
							+ fileNotFoundException.getMessage());
			log.error("Exception while upload file", fileNotFoundException);
		} catch (IOException ioException) {
			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded " + ioException.getMessage());
			log.error("Exception while upload file", ioException);
		} catch (JAXBException jaxbException) {
			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded " + jaxbException.getMessage());
			log.error("Exception while upload file", jaxbException);
		}

		return "redirect:/uploadStatus";
	}

	@GetMapping("/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}
}