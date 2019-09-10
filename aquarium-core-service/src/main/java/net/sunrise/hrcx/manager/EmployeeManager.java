package net.sunrise.hrcx.manager;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sunrise.cdx.domain.entity.Configuration;
import net.sunrise.cdx.manager.ConfigurationManager;
import net.sunrise.common.CommonConstants;
import net.sunrise.common.CommonUtility;
import net.sunrise.domain.entity.hc.Employee;
import net.sunrise.enums.DefaultConfigurations;
import net.sunrise.exceptions.EcosysException;
import net.sunrise.framework.manager.BaseManager;
import net.sunrise.framework.repository.BaseRepository;
import net.sunrise.helper.GlobalDataServiceHelper;
import net.sunrise.hrcx.persistence.EmployeeRepository;
import net.sunrise.model.Bucket;

@Service("employeeManager")
public class EmployeeManager extends BaseManager<Employee, Long> {
	private static final long serialVersionUID = -5456144769781208206L;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ConfigurationManager configurationManager;

	@Inject
	private GlobalDataServiceHelper dataRepositoryHelper;

	@Override
	protected BaseRepository<Employee, Long> getRepository() {
		return this.employeeRepository;
	}

	public long getCountByCode(String code){
		return this.employeeRepository.countByCode(code);
	}

	@SuppressWarnings("unchecked")
	public String importEmployees(InputStream inputStream, String sheetName, int startedIndex) throws EcosysException {
		List<String> dataParts = null;
		Bucket dataBucket = null;
		Employee emp = null;
		try {
			dataBucket = dataRepositoryHelper.readSpreadsheetData(inputStream, new String[]{sheetName});
			List<List<String>> dataStrings = (List<List<String>>)dataBucket.getBucketData().get(sheetName);
			for (int i = startedIndex; i < dataStrings.size(); ++i){
				dataParts = dataStrings.get(i);
				emp = this.parseEmployee(dataParts);
				this.employeeRepository.save(emp);
			}
		} catch (Exception e) {
			throw new EcosysException(e);
		}
		return CommonConstants.STRING_BLANK;//Should return the import messages log
	}

	protected Employee parseEmployee(List<String> dataParts) throws EcosysException {
		Employee emp = new Employee();
		try {
			emp
			.setCode(dataParts.get(1))
			.setFirstName(dataParts.get(3))
			.setLastName(dataParts.get(2))
			.setEmail(dataParts.get(6))
			.setAddress(dataParts.get(7))
			;
		} catch (Exception e) {
			throw new EcosysException(e);
		}
		System.out.println(dataParts.get(1)+"|\t"+dataParts.get(2)+" "+dataParts.get(3)+"|\t");
		return emp;
	}

	public void initDefaultData() throws EcosysException{
		Optional<Configuration> configuration = configurationManager.getByName(DefaultConfigurations.setupEmployees.getConfigurationName());
		if (!configuration.isPresent() && true==CommonUtility.getBooleanValue(configuration.get().getValue())){
			return;
		}

		try {
			String fileInputName = "salesman.xlsx";
			String sheetName = "sheet1";
			String startedRowIndex = "1"; //The first row often is column headers
			if (CommonUtility.isNotEmpty(configuration.get().getValueExtended())){
				String[] extendedValue = configuration.get().getValueExtended().split(CommonUtility.SEPARATORS[1]);
				fileInputName = extendedValue[0];
				sheetName = extendedValue[1];
				startedRowIndex = extendedValue[2];
			}
			this.importEmployees(new FileInputStream(fileInputName), sheetName, Integer.valueOf(startedRowIndex));
		} catch (Exception e) {
			throw new EcosysException(e);
		} finally {
			Configuration config = Configuration.builder().name(DefaultConfigurations.setupEmployees.getConfigurationName()).value("true").build();
			configurationManager.save(config);
		}
	}
}
