package com.rb.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.rb.model.CusStmtModel;
import com.rb.model.CusStmtModels;

public final class UploadUtil {
	private static final Logger log = LogManager.getLogger(UploadUtil.class);
	public static String FILE_TYPE_CSV = "csv";
	public static String FILE_TYPE_XML = "xml";

	/**
	 * This method will convert the given multipart file into file
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static File convertMultiPartToFile(MultipartFile file)
			throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	/**
	 * This method will populate the model from the uploaded csv file
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public static List<CusStmtModel> populateFromCSV(File file)
			throws FileNotFoundException, IOException {
		List<CusStmtModel> statements = null;
		try (Reader reader = new FileReader(file);) {
			CsvToBean<CusStmtModel> csvToBean = new CsvToBeanBuilder<CusStmtModel>(
					reader).withType(CusStmtModel.class)
					.withIgnoreLeadingWhiteSpace(true).build();
			statements = csvToBean.parse();

		}
		return statements;
	}

	/**
	 * This method will populate the model from the uploaded xml file
	 * 
	 * @param file
	 * @return
	 * @throws JAXBException
	 * @throws Exception
	 */
	public static List<CusStmtModel> populateFromXML(File file)
			throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(CusStmtModels.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		CusStmtModels cusStmtModels = (CusStmtModels) unmarshaller
				.unmarshal(file);
		return cusStmtModels.getRecords();
	}

	/**
	 * This method will validate the each record of the statements and return
	 * the failed/ invalid transactions list
	 * 
	 * @param statements
	 * @return
	 */
	public static List<CusStmtModel> processStatements(
			List<CusStmtModel> statements) {
		List<CusStmtModel> failedStatements = null;
		Set<Integer> uniqueRefSet = null;
		Double endBalance = null;
		if (statements == null) {
			return null;
		}

		statements.stream().forEachOrdered(log::debug);
		failedStatements = new ArrayList<CusStmtModel>();
		uniqueRefSet = new HashSet<Integer>();

		for (CusStmtModel statement : statements) {
			endBalance = round(statement.getStartBalance()
					+ statement.getMutation());
			if (!uniqueRefSet.add(statement.getReference())) {
				statement.setRemarks("Duplicate");
				failedStatements.add(statement);
			} else if (!endBalance.equals(statement.getEndBalance())) {
				statement.setRemarks("End Balance is not correct");
				failedStatements.add(statement);
			}
		}
		log.debug("Failed Transactions----------------------");
		failedStatements.stream().forEachOrdered(log::debug);
		log.debug("Failed Transactions----------------------");
		return failedStatements;
	}

	/**
	 * 
	 * @param doubleValue
	 * @return
	 */
	public static Double round(Double doubleValue) {
		doubleValue = (double) Math.round(doubleValue * 100);
		return doubleValue / 100;
	}
}
