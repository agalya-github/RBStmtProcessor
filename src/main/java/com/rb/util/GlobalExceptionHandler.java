package com.rb.util;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	private static final Logger log = LogManager
			.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(MultipartException.class)
	public String handleMultipartException(MultipartException e,
			RedirectAttributes redirectAttributes) {

		log.error("MultipartException: ", e);
		redirectAttributes.addFlashAttribute("message", e.getCause()
				.getMessage());
		return "redirect:/uploadStatus";

	}

	@ExceptionHandler(FileNotFoundException.class)
	public String handleFileNotFoundException(FileNotFoundException e,
			RedirectAttributes redirectAttributes) {
		log.error("FileNotFoundException: ", e);
		redirectAttributes.addFlashAttribute("message", e.getCause()
				.getMessage());
		return "redirect:/uploadStatus";

	}

	@ExceptionHandler(IOException.class)
	public String handleIOException(IOException e,
			RedirectAttributes redirectAttributes) {
		log.error("IOException: ", e);
		redirectAttributes.addFlashAttribute("message", e.getCause()
				.getMessage());
		return "redirect:/uploadStatus";

	}

	@ExceptionHandler(JAXBException.class)
	public String handleJAXBException(JAXBException e,
			RedirectAttributes redirectAttributes) {
		log.error("JAXBException: ", e);
		redirectAttributes.addFlashAttribute("message", e.getCause()
				.getMessage());
		return "redirect:/uploadStatus";
	}

	@ExceptionHandler(FileUploadException.class)
	public String handleFileUploadException(FileUploadException e,
			RedirectAttributes redirectAttributes) {
		log.error("FileUploadException: ", e);
		redirectAttributes.addFlashAttribute("message", e.getCause()
				.getMessage());
		return "redirect:/uploadStatus";

	}
}
